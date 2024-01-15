package com.fpetrola.z80.instructions;

import com.fpetrola.z80.State;

public class Add extends TargetSourceOpcode {

  public Add(State state, OpcodeReference target, OpcodeReference source) {
    super(state, target, source);
  }

  public int execute() {
    final int value1 = source.read();
    final int value2 = target.read();
    int ALU8BitAdd = flag.ALU8BitAdd(value1, value2);
    target.write(ALU8BitAdd);
    return getCyclesCost();
  }

  public String toString() {
    return "ADD " + target + ", " + source;
  }

  public Object clone() throws CloneNotSupportedException {
    Add xor = new Add(state, (OpcodeReference) target.clone(), (OpcodeReference) source.clone());
    completeClone(xor);
    return xor;
  }
}
