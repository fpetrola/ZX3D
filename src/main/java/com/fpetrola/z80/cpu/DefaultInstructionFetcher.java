package com.fpetrola.z80.cpu;

import com.fpetrola.z80.instructions.base.Instruction;
import com.fpetrola.z80.instructions.base.JumpInstruction;
import com.fpetrola.z80.mmu.State;
import com.fpetrola.z80.opcodes.decoder.DefaultFetchNextOpcodeInstruction;
import com.fpetrola.z80.opcodes.decoder.table.FetchNextOpcodeInstructionFactory;
import com.fpetrola.z80.opcodes.decoder.table.TableBasedOpCodeDecoder;
import com.fpetrola.z80.opcodes.references.OpcodeConditions;
import com.fpetrola.z80.opcodes.references.WordNumber;

public class DefaultInstructionFetcher<T extends WordNumber> implements InstructionFetcher {
  protected State<T> state;
  protected Instruction<T> instruction;
  protected Instruction<T>[] opcodesTables;

  protected int opcodeInt;
  protected T pcValue;
  protected final InstructionExecutor<T> instructionExecutor;

  public DefaultInstructionFetcher(State aState, FetchNextOpcodeInstructionFactory fetchInstructionFactory, InstructionExecutor<T> instructionExecutor) {
    this(aState, new OpcodeConditions(aState.getFlag()), fetchInstructionFactory, instructionExecutor);
  }

  public DefaultInstructionFetcher(State aState, OpcodeConditions opcodeConditions, FetchNextOpcodeInstructionFactory fetchInstructionFactory, InstructionExecutor<T> instructionExecutor) {
    this.state = aState;
    this.instructionExecutor = instructionExecutor;
    opcodesTables = new TableBasedOpCodeDecoder<T>(this.state, opcodeConditions, fetchInstructionFactory).getOpcodeLookupTable();
  }

  @Override
  public void fetchNextInstruction() {
    state.getRegisterR().increment();
    pcValue = state.getPc().read();
    opcodeInt = state.getMemory().read(pcValue).intValue();
    Instruction<T> instruction = opcodesTables[this.state.isHalted() ? 0x76 : opcodeInt];
    this.instruction = instruction;
    this.instructionExecutor.execute(this.instruction);
    this.instruction = getBaseInstruction(instruction);

    T nextPC = null;
    if (this.instruction instanceof JumpInstruction jumpInstruction)
      nextPC = (T) jumpInstruction.getNextPC();

    if (nextPC == null)
      nextPC = pcValue.plus(this.instruction.getLength());

    state.getPc().write(nextPC);
  }

  private Instruction<T> getBaseInstruction(Instruction<T> instruction) {
    while (instruction instanceof DefaultFetchNextOpcodeInstruction fetchNextOpcodeInstruction) {
      instruction = fetchNextOpcodeInstruction.findNextOpcode();
    }
    return instruction;
  }

  @Override
  public void reset() {
  }
}
