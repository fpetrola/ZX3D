package com.fpetrola.z80.instructions;

import com.fpetrola.z80.State;

public class Cp extends TargetSourceOpcode {

  public Cp(State state, OpcodeReference target, OpcodeReference source) {
    super(state, target, source);
  }

  public int execute() {
    final int value1 = target.read();
    final int value2 = source.read();

    flag.ALU8BitCp(value2, value1);

    return getCyclesCost();
  }

  public String toString() {
    return "CP " + source;
  }

  public Object clone() throws CloneNotSupportedException {
    Cp xor = new Cp(state, (OpcodeReference) target.clone(), (OpcodeReference) source.clone());
    completeClone(xor);
    return xor;
  }
}
