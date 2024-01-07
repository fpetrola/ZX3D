package com.fpetrola.z80.instructions;

import com.fpetrola.z80.State;
import com.fpetrola.z80.mmu.Memory;

public class Cpi extends AbstractOpCode {

  public Cpi(State state, OpcodeTargets opt) {
    super(state);
  }

  public int execute() {

    int hlValue = hl.read();
    int valueFromHL = memory.read(hlValue);

    flag.CPI(valueFromHL, a.read(), bc.read());
    
    hl.increment(1);
    bc.decrement(1);

    pc.increment(1);

    return 1;
  }
}
