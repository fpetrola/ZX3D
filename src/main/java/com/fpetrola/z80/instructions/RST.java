package com.fpetrola.z80.instructions;

import com.fpetrola.z80.State;

public class RST extends AbstractOpCode {

  private final int p;

  public RST(State state, int p) {
    super(state);
    this.p = p;
  }

  public int execute() {
    pc.increment(1);

    final int position = p & 0xFFFF;
    sp.decrement(2);
    final int address = sp.read();
    int value = pc.read();
    memory.write(address, value & 0xFF);
    memory.write(address + 1, (value >> 8));
    pc.write(position);

    return 5 + 3 + 3;
  }

  public String toString() {
    return "RST " + String.format("%02X", p);
  }
}
