package com.fpetrola.z80.instructions;

import com.fpetrola.z80.instructions.base.ParameterizedUnaryAluInstruction;
import com.fpetrola.z80.opcodes.references.OpcodeReference;
import com.fpetrola.z80.opcodes.references.WordNumber;
import com.fpetrola.z80.registers.Register;

import static com.fpetrola.z80.registers.flag.AluOperationsInitializer.*;

public class RLCA<T extends WordNumber> extends ParameterizedUnaryAluInstruction<T> {
  public RLCA(OpcodeReference target, Register<T> flag) {
    super(target, flag, (tFlagRegister, regA) -> rlcaTableAluOperation.executeWithCarry(regA, tFlagRegister));
  }
}
