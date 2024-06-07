package com.fpetrola.z80.blocks.spy;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonGrammar;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpetrola.z80.blocks.*;
import com.fpetrola.z80.blocks.references.ReferencesHandler;
import com.fpetrola.z80.bytecode.impl.ByteCodeGenerator;
import com.fpetrola.z80.cpu.RandomAccessInstructionFetcher;
import com.fpetrola.z80.graph.CustomGraph;
import com.fpetrola.z80.graph.GraphFrame;
import com.fpetrola.z80.instructions.base.ConditionalInstruction;
import com.fpetrola.z80.instructions.base.Instruction;
import com.fpetrola.z80.jspeccy.ReadOnlyMemoryImplementation;
import com.fpetrola.z80.metadata.DataStructure;
import com.fpetrola.z80.metadata.GameMetadata;
import com.fpetrola.z80.mmu.Memory;
import com.fpetrola.z80.mmu.State;
import com.fpetrola.z80.opcodes.references.ExecutionPoint;
import com.fpetrola.z80.opcodes.references.IntegerWordNumber;
import com.fpetrola.z80.opcodes.references.WordNumber;
import com.fpetrola.z80.registers.Register;
import com.fpetrola.z80.spy.AbstractInstructionSpy;
import com.fpetrola.z80.spy.ComplexInstructionSpy;
import com.fpetrola.z80.spy.ExecutionStep;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.cojen.maker.ClassMaker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

public class RoutineGrouperSpy<T extends WordNumber> extends AbstractInstructionSpy<T> implements ComplexInstructionSpy<T> {
  private static final String FILE_TRACE_JSON = "game-metadata.json";
  private GameMetadata gameMetadata;

  private RoutineCustomGraph customGraph;
  private final GraphFrame graphFrame;

  @Override
  public boolean isStructureCapture() {
    return structureCapture;
  }

  private boolean structureCapture;

  public BlocksManager getBlocksManager() {
    return blocksManager;
  }

  private BlocksManager blocksManager;
  private List<String> visitedPCs = new ArrayList<>();

  private Queue<ExecutionStep> stepsQueue = new CircularFifoQueue<>(1000);
  private String gameName;

  public RoutineGrouperSpy(GraphFrame graphFrame) {
    this.graphFrame = graphFrame;
    initGraph();
    blocksManager = new BlocksManager(new RoutineCustomGraph.GraphBlockChangesListener());
  }

  public void setGameMetadata(GameMetadata gameMetadata) {
    this.gameMetadata = gameMetadata;
    blocksManager.setGameMetadata(gameMetadata);
  }

  @Override
  protected void addExecutionPoint(ExecutionPoint executionPoint) {
    super.addExecutionPoint(executionPoint);
    executionPoint.setCycle(blocksManager.getCycle());
  }

  private void importMetadata() {
    if (gameName != null)
      try {
        ObjectMapper objectMapper = new ObjectMapper();

        Jankson jankson = Jankson.builder().build();
        setGameMetadata(jankson.fromJson(jankson.load(new File(getMetadataFileName())), GameMetadata.class));
//        gameMetadata = objectMapper.readValue(new File(getMetadataFileName()), GameMetadata.class);
      } catch (Exception e) {
        GameMetadata gameMetadata = new GameMetadata();
        gameMetadata.mainLoopAddress = -1;
        setGameMetadata(gameMetadata);
        exportMetadata(gameMetadata);
      }
  }

  private void exportMetadata(GameMetadata gameMetadata) {
    try {
      FileWriter writer = new FileWriter(getMetadataFileName());
      Jankson.builder().build().toJson(gameMetadata).toJson(writer, JsonGrammar.JSON5, 0);
      writer.close();

      //      ObjectMapper objectMapper = new ObjectMapper();
//      objectMapper.writeValue(new File(getMetadataFileName()), gameMetadata);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String getMetadataFileName() {
    return gameName + ".metadata.json";
  }

  public void reset(State state) {
    super.reset(state);
    initGraph();
  }

  private void initGraph() {
    customGraph = new RoutineCustomGraph(graphFrame.graph);
  }

  public void afterExecution(Instruction<T> instruction) {
    super.afterExecution(instruction);

    if (capturing) {
      executionSteps.clear();
      stepsQueue.add(executionStep);
      memoryChanges.clear();
      blocksManager.setExecutionNumber(executionNumber);
      blocksManager.checkExecution(executionStep);

//      executeMutantCode();
    }
  }

  private void executeMutantCode() {
    if (executionStep.pcValue > 16384 && enabledExecutionNumber > 50000) {
      System.out.println(blocksManager.getBlocks().size());
      Memory memory1 = memorySpy.getMemory();
      try {
        boolean isMutant = false;// executionStep.instruction.getState().getIo() instanceof ReadOnlyIOImplementation;
        if (!isMutant) {
          boolean isConditional = executionStep.instruction instanceof ConditionalInstruction;
          if (isConditional) {
            z80.getState().getPc().write(new IntegerWordNumber(executionStep.pcValue));
            memorySpy.setMemory(new ReadOnlyMemoryImplementation(memory1));
            for (int i = 0; i < 2; i++) {
              z80.execute();
            }
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        memorySpy.setMemory(memory1);
      }
    }
  }

  public void process() {
    blocksManager.optimizeBlocks();

    blocksManager.findBlockAt(37310).accept(new BlockBytecodeGenerator());

    CustomGraph a = customGraph.convertGraph();
    a.exportGraph();
  }

  @Override
  public void export() {
    blocksManager.findBlockAt(37310).accept(new BlockBytecodeGenerator());

    blocksManager.getBlocks().forEach(block -> {
      ReferencesHandler referencesHandler = block.getReferencesHandler();
      Map<Long, DataStructure> foundStructures = referencesHandler.getFoundStructures();
      foundStructures = removeRepeated(foundStructures);
      DataStructure combined = combine(foundStructures);
      if (!combined.instances.isEmpty())
        gameMetadata.addDataStructure(combined);
    });

    exportMetadata(gameMetadata);
  }

  private Map<Long, DataStructure> removeRepeated(Map<Long, DataStructure> foundStructures) {
    Map<Long, DataStructure> result = new HashMap<>();
    Map<Long, DataStructure> second = new HashMap<>(foundStructures);

    foundStructures.forEach((key, value) -> {
      if (result.isEmpty())
        result.put(key, value);

      second.forEach((key1, value1) -> {
        if (key != key1) {
          if (!value1.instances.isEmpty())
            if (!value.instances.equals(value1.instances)) {
              result.put(key1, value1);
            } else
              System.out.println("adadg");
        }
      });
    });

    return result;
  }


  private DataStructure combine(Map<Long, DataStructure> foundStructures) {

    DataStructure dataStructure = new DataStructure();

    foundStructures.forEach((key, value) -> {
      value.instances.forEach((k1, v1) -> {
        v1.addresses.forEach(a -> {
          dataStructure.getInstance(k1).addAddress(a);
        });
      });

    });

    return dataStructure;
  }

  @Override
  public void enableStructureCapture() {
    if (!structureCapture)
      blocksManager.getBlocks().forEach(block -> block.getReferencesHandler().addDataObserver(memory, this));
    else
      blocksManager.getBlocks().forEach(block -> block.getReferencesHandler().removeDataObserver(memory));

    structureCapture = !structureCapture;
  }

  @Override
  public void setGameName(String gameName) {
    this.gameName = gameName;
    importMetadata();
  }

  public String getGameName() {
    return gameName;
  }

  private class BlockBytecodeGenerator implements BlockVisitor {
    @Override
    public void visitingCodeBlock(CodeBlock codeBlock) {
      generateBytecode(codeBlock, RoutineGrouperSpy.this);
    }

    public void generateBytecode(CodeBlock codeBlock, ComplexInstructionSpy spy) {
      RandomAccessInstructionFetcher instructionFetcher = address1 -> spy.getFetchedAt(address1);
      Predicate<Integer> hasCodeChecker = address1 -> {
        boolean notCodeBlock = !(codeBlock.getBlocksManager().findBlockAt(address1) instanceof CodeBlock);
        boolean isNotFetched = instructionFetcher.getInstructionAt(address1) == null;
        return !(notCodeBlock || isNotFetched);
      };
      Register pc = null;//FIXME: get pc
      new ByteCodeGenerator(instructionFetcher, codeBlock.getRangeHandler().getStartAddress(), hasCodeChecker, 0xFFFF, pc).generate(() -> ClassMaker.beginExternal("JSW").public_(), "JSW.class");
    }
  }
}
