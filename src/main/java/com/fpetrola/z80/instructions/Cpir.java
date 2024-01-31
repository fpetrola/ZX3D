package com.fpetrola.z80.instructions;

import com.fpetrola.z80.mmu.State;

public class Cpir extends RepeatingInstruction {
  public Cpir(State state) {
    super(state, new Cpi(state));
  }

  protected boolean checkLoopCondition() {
    return !state.isZ() && bc.read() != 0;
  }
}
