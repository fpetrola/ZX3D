package com.fpetrola.z80.instructions;

import com.fpetrola.z80.State;

public class Inc extends AbstractOpCode {

  private final OpcodeReference target;

  public Inc(State state, OpcodeReference target) {
    super(state);
    this.target = target;
  }

  @Override
  public int execute() {
    pc.increment(1);
    target.write(flag.ALU8BitInc(target.read()));

    return 4 + target.cyclesCost() + target.cyclesCost();
  }

  @Override
  public String toString() {
    return "INC " + target;
  }
}
