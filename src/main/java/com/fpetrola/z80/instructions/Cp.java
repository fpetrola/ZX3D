package com.fpetrola.z80.instructions;

import com.fpetrola.z80.instructions.base.InstructionVisitor;
import com.fpetrola.z80.opcodes.references.ImmutableOpcodeReference;
import com.fpetrola.z80.opcodes.references.OpcodeReference;
import com.fpetrola.z80.opcodes.references.WordNumber;
import com.fpetrola.z80.registers.flag.FlagRegister;

public class Cp<T extends WordNumber> extends ParameterizedBinaryAluInstruction<T> {
  public Cp(OpcodeReference target, ImmutableOpcodeReference source, FlagRegister<T> flag) {
    super(target, source, flag, FlagRegister::ALU8BitCp);
  }

  public int execute() {
    final T value1 = target.read();
    final T value2 = source.read();
    binaryAluOperation.execute(flag, value2, value1);
    return cyclesCost;
  }

  @Override
  public void accept(InstructionVisitor visitor) {
    visitor.visitingCp(this);
  }
}
