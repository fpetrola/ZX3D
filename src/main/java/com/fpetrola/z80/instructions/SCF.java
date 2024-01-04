package com.fpetrola.z80.instructions;

import com.fpetrola.z80.State;

public class SCF extends AbstractOpCode {

  public SCF(State state) {
    super(state);
  }

  public int execute() {
    pc.increment(1);
    flag.SCF(this.a.read());
    return 4;
  }

}
