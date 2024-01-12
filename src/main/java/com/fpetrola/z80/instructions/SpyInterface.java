package com.fpetrola.z80.instructions;

import com.fpetrola.z80.mmu.Memory;
import com.fpetrola.z80.registers.Register;
import com.fpetrola.z80.registers.RegisterName;

public interface SpyInterface {

  boolean isCapturing();

  Memory wrapMemory(Memory aMemory);

  OpcodeReference wrapOpcodeReference(OpcodeReference opcodeReference);

  Register wrapOpcodeRegister(Register register, RegisterName name);

  void start(OpCode opcode, int opcodeInt, int pcValue);

  void end();

  void enable(boolean enabled);

  void setSpritesArray(boolean[] bitsWritten);

  void undo();

  public void flipOpcode(OpCode opCode, int opcodeInt);

}