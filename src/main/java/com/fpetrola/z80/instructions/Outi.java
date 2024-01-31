package com.fpetrola.z80.instructions;

import com.fpetrola.z80.instructions.base.AbstractInstruction;
import com.fpetrola.z80.mmu.State;
import com.fpetrola.z80.registers.Register;

public class Outi extends AbstractInstruction {
  public Outi(State state) {
    super(state);
  }

  public int execute() {

    spy.pause();

    int hlValue = hl.read();
    int valueFromHL = memory.read(hlValue);

    int cValue = bc.getLow().read();
    spy.doContinue();

    state.getIo().out(cValue, valueFromHL);

    spy.pause();

    hl.increment(1);
    b.decrement(1);

    flag.OUTI(b.read());

    spy.doContinue();

    return 1;
  }
}
