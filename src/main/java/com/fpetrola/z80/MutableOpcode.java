package com.fpetrola.z80;

import com.fpetrola.z80.instructions.OpCode;
import com.fpetrola.z80.registers.Plain16BitRegister;

public class MutableOpcode implements OpCode {

  @Override
  public int execute() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getLength() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void incrementLength() {
    // TODO Auto-generated method stub

  }

  @Override
  public Plain16BitRegister getPC() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setPC(Plain16BitRegister pc) {
    // TODO Auto-generated method stub

  }

  @Override
  public int getBasePc() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void setBasePc(int basePc) {
    // TODO Auto-generated method stub

  }

  @Override
  public OpCode getInstruction() {
    // TODO Auto-generated method stub
    return null;
  }
  
  public Object clone() throws CloneNotSupportedException {
    // TODO Auto-generated method stub
    return super.clone();
  }

}
