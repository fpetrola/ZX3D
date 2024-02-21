package com.fpetrola.z80.instructions;

import com.fpetrola.z80.instructions.base.AbstractInstruction;
import com.fpetrola.z80.mmu.State;
import com.fpetrola.z80.opcodes.references.WordNumber;

public class Cpd<T extends WordNumber> extends AbstractInstruction<T> {

  Cpd(State state) {
    super(state);
  }

  public int execute() {
    T hlValue = hl.read();
    T valueFromHL = memory.read(hlValue);
    hl.decrement();
    bc.decrement();

    flag.CPD(valueFromHL, a.read(), bc.read());

    return 1;
  }
}
