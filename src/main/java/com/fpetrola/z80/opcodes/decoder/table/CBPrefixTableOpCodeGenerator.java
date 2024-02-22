package com.fpetrola.z80.opcodes.decoder.table;

import com.fpetrola.z80.instructions.InstructionFactory;
import com.fpetrola.z80.instructions.base.Instruction;
import com.fpetrola.z80.mmu.State;
import com.fpetrola.z80.opcodes.references.OpcodeConditions;
import com.fpetrola.z80.opcodes.references.OpcodeReference;

import static com.fpetrola.z80.registers.RegisterName.*;

public class CBPrefixTableOpCodeGenerator<T> extends TableOpCodeGenerator<T> {

  public CBPrefixTableOpCodeGenerator(State state, OpcodeReference a, OpcodeConditions opc1, InstructionFactory instructionFactory) {
    super(state, HL, H, L, a, opc1, instructionFactory);
  }

  protected Instruction<T> getOpcode() {
    return select(rot.get(y).create(r[z], 0), i.BIT(r[z], y, 0), i.RES(r[z], y, 0), i.SET(r[z], y, 0)).get(x);
  }
}
