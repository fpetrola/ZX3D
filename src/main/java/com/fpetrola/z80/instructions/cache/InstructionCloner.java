package com.fpetrola.z80.instructions.cache;

import java.lang.reflect.Constructor;

import com.fpetrola.z80.instructions.*;
import com.fpetrola.z80.instructions.base.*;
import com.fpetrola.z80.opcodes.references.WordNumber;
import com.fpetrola.z80.spy.NullInstructionSpy;

import static com.fpetrola.z80.instructions.InstructionFactory.createDJNZ;

public class InstructionCloner<T extends WordNumber> {
  public Instruction<T> clone(Instruction<T> instruction) {
    try {
      AbstractInstruction<T> newInstance;
      boolean isDJNZ = instruction instanceof DJNZ;
      boolean isConditional = instruction instanceof ConditionalInstruction;

      if (instruction instanceof IM) {
        newInstance = new IM(instruction.getState(), ((IM) instruction).getMode());
      } else if (instruction instanceof Ret) {
        newInstance = new Ret(instruction.getState(), ((Ret) instruction).getCondition());
      } else if (instruction instanceof RST) {
        newInstance = new RST(instruction.getState(), ((RST) instruction).getP());
      } else {
        if (isDJNZ) {
          newInstance = createDJNZ(((DJNZ) instruction).getPositionOpcodeReference());
        } else {
          Constructor<?> constructor = instruction.getClass().getConstructors()[0];
          Object[] objects = new Object[0];
          if (isConditional) {
            ConditionalInstruction conditionalInstruction = (ConditionalInstruction) instruction;
            objects = new Object[]{conditionalInstruction.getState(), conditionalInstruction.getPositionOpcodeReference().clone(), conditionalInstruction.getCondition()};
          } else if (instruction instanceof BitOperation) {
            BitOperation bitOperation = (BitOperation) instruction;
            objects = new Object[]{bitOperation.getState(), bitOperation.getTarget().clone(), bitOperation.getN(), bitOperation.getValueDelta()};
          } else if (instruction instanceof InvertedFetchInstruction<T>) {
            InvertedFetchInstruction invertedFetchInstruction = (InvertedFetchInstruction) instruction;
            objects = new Object[]{invertedFetchInstruction.getState(), invertedFetchInstruction.getTarget().clone(), invertedFetchInstruction.getValueDelta()};
          } else if (instruction instanceof TargetSourceInstruction) {
            TargetSourceInstruction<T> targetSourceInstruction = (TargetSourceInstruction<T>) instruction;
            objects = new Object[]{targetSourceInstruction.getState(), targetSourceInstruction.getTarget().clone(), targetSourceInstruction.getSource().clone()};
          } else if (instruction instanceof TargetInstruction) {
            TargetInstruction<T> targetInstruction = (TargetInstruction<T>) instruction;
            objects = new Object[]{targetInstruction.getState(), targetInstruction.getTarget().clone()};
          } else if (constructor.getParameterCount() == 1) {
            objects = new Object[]{instruction.getState()};
          } else
            System.out.println("dagadg");

          newInstance = (AbstractInstruction<T>) constructor.newInstance(objects);
        }
      }

      if (isConditional || isDJNZ)
        ((ConditionalInstruction<T>) newInstance).setJumpAddress(((ConditionalInstruction<T>) instruction).getJumpAddress());

      newInstance.setLength(instruction.getLength());
      newInstance.setSpy(new NullInstructionSpy());
      return newInstance;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
