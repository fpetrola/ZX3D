package com.fpetrola.z80.instructions;

import com.fpetrola.z80.State;

public class Sbc16 extends TargetSourceOpcode {

  public Sbc16(State state, OpcodeReference target, OpcodeReference source) {
    super(state, target, source);
  }

  public int execute() {
    pc.increment(1);

    final int value1 = source.read();
    final int value2 = target.read();
    int alu8BitAdc = flag.ALU16BitSBC(value2, value1);
    target.write(alu8BitAdc);

    return 4 + source.cyclesCost() + target.cyclesCost();
  }

  public String toString() {
    return "SBC16 " + target + ", " + source;
  }
}
