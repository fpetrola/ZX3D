package com.fpetrola.z80.instructions;

import com.fpetrola.z80.instructions.base.ConditionalInstruction;
import com.fpetrola.z80.mmu.State;
import com.fpetrola.z80.opcodes.references.BaseImmutableOpcodeReference;
import com.fpetrola.z80.opcodes.references.Condition;
import com.fpetrola.z80.opcodes.references.OpcodeReference;
import com.fpetrola.z80.opcodes.references.WordNumber;

public class JR<T extends WordNumber> extends ConditionalInstruction<T> {

  public JR(State state, BaseImmutableOpcodeReference target, Condition condition) {
    super(state, (OpcodeReference<T>) target, condition);
  }

  public int execute() {
    jumpRelativeIfMatchCondition();
    return cyclesCost;
  }
}
