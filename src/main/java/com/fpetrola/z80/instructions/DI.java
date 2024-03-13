package com.fpetrola.z80.instructions;

import com.fpetrola.z80.instructions.base.AbstractInstruction;
import com.fpetrola.z80.mmu.State;
import com.fpetrola.z80.opcodes.references.WordNumber;

public class DI<T extends WordNumber> extends AbstractInstruction<T> {
  private final State<T> state;

  public DI(State state) {
    this.state = state;
  }

  public int execute() {
    state.resetInterrupt();
    return 4;
  }
}
