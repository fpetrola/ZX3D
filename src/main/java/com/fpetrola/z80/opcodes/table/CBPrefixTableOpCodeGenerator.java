package com.fpetrola.z80.opcodes.table;

import com.fpetrola.z80.State;
import com.fpetrola.z80.instructions.BIT;
import com.fpetrola.z80.instructions.OpCode;
import com.fpetrola.z80.instructions.OpcodeReference;
import com.fpetrola.z80.instructions.RES;
import com.fpetrola.z80.instructions.SET;
import com.fpetrola.z80.instructions.SpyInterface;

import static com.fpetrola.z80.registers.RegisterName.*;

public class CBPrefixTableOpCodeGenerator extends TableOpCodeGenerator {

  public CBPrefixTableOpCodeGenerator(State state, SpyInterface opcodesSpy, OpcodeReference a) {
    super(state, opcodesSpy, HL, H, L, a);
  }

  protected OpCode getOpcode(int i) {
    return select(rot.get(y).apply(r[z]), new BIT(s, r[z], y, 0), new RES(s, r[z], y, 0), new SET(s, r[z], y, 0)).get(x);
  }
}
