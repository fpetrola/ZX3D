package com.fpetrola.z80.instructions;

import com.fpetrola.z80.instructions.base.InstructionVisitor;
import com.fpetrola.z80.opcodes.references.OpcodeReference;
import com.fpetrola.z80.opcodes.references.WordNumber;
import com.fpetrola.z80.registers.flag.FlagRegister;

public class Dec<T extends WordNumber> extends ParameterizedUnaryAluInstruction<T> {
  public Dec(OpcodeReference target, FlagRegister<T> flag) {
    super(target, flag, FlagRegister::ALU8BitDec);
  }

  public void accept(InstructionVisitor visitor) {
    visitor.visitingDec(this);
  }
}
