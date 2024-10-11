package com.fpetrola.z80.instructions;

import com.fpetrola.z80.instructions.base.AbstractInstruction;
import com.fpetrola.z80.instructions.base.InstructionVisitor;
import com.fpetrola.z80.opcodes.references.WordNumber;

public class Nop<T extends WordNumber> extends AbstractInstruction<T> {
  public Nop() {
  }

  public int execute() {
    return 4;
  }

  @Override
  public void accept(InstructionVisitor visitor) {
    visitor.visitNop(this);
  }
}
