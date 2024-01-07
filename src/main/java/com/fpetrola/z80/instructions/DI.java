package com.fpetrola.z80.instructions;

import com.fpetrola.z80.State;

public class DI extends AbstractOpCode {

  public DI(State state) {
    super(state);
  }

  public int execute() {
    pc.increment(1);
    state.resetInterrupt();
    return 4;
  }
}
