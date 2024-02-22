package com.fpetrola.z80.instructions;

import com.fpetrola.z80.instructions.base.TargetInstruction;
import com.fpetrola.z80.opcodes.references.OpcodeReference;
import com.fpetrola.z80.opcodes.references.WordNumber;
import com.fpetrola.z80.registers.flag.IFlagRegister;

public class RLA<T extends WordNumber> extends TargetInstruction<T> {
  private final IFlagRegister<T> flag;

  RLA(OpcodeReference target, IFlagRegister<T> flag) {
    super(target);
    this.flag = flag;
  }

  public int execute() {
    final T value = target.read();
    target.write(flag.RLA(value));
    return cyclesCost;
  }
}
