package com.fpetrola.z80.blocks;

import com.fpetrola.z80.instructions.JP;
import com.fpetrola.z80.instructions.JR;
import com.fpetrola.z80.instructions.Ret;
import com.fpetrola.z80.instructions.base.ConditionalInstruction;
import com.fpetrola.z80.instructions.base.Instruction;
import com.fpetrola.z80.opcodes.references.ConditionAlwaysTrue;
import com.fpetrola.z80.spy.ExecutionStepData;

public class Routine extends AbstractBlock {
  public Routine() {

  }

  public Routine(int startAddress, int endAddress, String callType, BlocksManager blocksManager) {
    super(startAddress, endAddress, callType, blocksManager);
  }

  @Override
  public Block checkExecution(ExecutionStepData executionStepData) {
    Instruction instruction = executionStepData.instruction;
    if (isInside(executionStepData.pcValue))
      updateEndAddress(Math.max(endAddress, executionStepData.pcValue + instruction.getLength() - 1));
    else if (canTake(executionStepData.pcValue)) {
      Block startSplit = joinBlocksBetween(this, executionStepData.pcValue + instruction.getLength() );
    }

    int nextPC = instruction.getNextPC();
    if (nextPC != -1) {
      jumpPerformed(executionStepData.pcValue, nextPC, instruction, executionStepData);
    }
    return null;
  }

  private boolean isInside(int address) {
    return address >= startAddress && address <= endAddress;
  }

  public void jumpPerformed(int pc, int nextPC, Instruction instruction, ExecutionStepData executionStepData) {
    if (!isInside(nextPC)) {
      boolean isConditional = instruction instanceof ConditionalInstruction;
//          isConditional |= baseInstruction instanceof DJNZ;
      isConditional &= !(instruction instanceof JR);
      isConditional |= instruction instanceof Ret;

      isConditional &= !(instruction instanceof JP) || Math.abs(nextPC - pc) < 100;
      if (isConditional) {
        String callType = instruction.toString().contains("Call") ? "CALL" : "JUMP";
        boolean isRet = instruction instanceof Ret;
        if (isRet) {
          Ret ret = (Ret) instruction;
          boolean isConditionalRet = !(ret.getCondition() instanceof ConditionAlwaysTrue);
          if (!isConditionalRet)
            getBlocksManager().endBlock(nextPC, pc, false, new Routine());
        } else {
          Block nextBlock = blocksManager.findBlockAt(nextPC).prepareForJump(executionStepData.instruction.getNextPC(), 1);
          if (!nextBlock.getReferencedByBlocks().contains(this)) {
            this.addBlockReference(this, nextBlock, pc, nextPC);
          }
//        getBlocksManager().addBlock(nextPC, pc, instruction.getClass().getSimpleName(), new Routine());
        }
      }
    }
  }

  public boolean canTake(int pcValue) {
    return pcValue == getEndAddress() + 1;
  }

  public Block prepareForJump(int pcValue, int length1) {
    return this;
  }
}
