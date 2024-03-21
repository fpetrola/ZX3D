package com.fpetrola.z80.instructions;

import com.fpetrola.z80.instructions.base.ParameterizedBinaryAluInstruction;
import com.fpetrola.z80.opcodes.references.ImmutableOpcodeReference;
import com.fpetrola.z80.opcodes.references.OpcodeReference;
import com.fpetrola.z80.opcodes.references.WordNumber;
import com.fpetrola.z80.registers.flag.FlagRegister;
import com.fpetrola.z80.registers.flag.TableFlagRegisterInitTables;

public class Sbc16<T extends WordNumber> extends ParameterizedBinaryAluInstruction<T> {
  public Sbc16(OpcodeReference<T> target, ImmutableOpcodeReference<T> source, FlagRegister<T> flag) {
    super(target, source, flag, (tFlagRegister, DE, HL) -> TableFlagRegisterInitTables.sbc16TableAluOperation.executeWithCarry(DE, HL, tFlagRegister));
  }
}
