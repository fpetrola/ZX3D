package jmce;

import com.fpetrola.z80.helpers.Helper;
import com.fpetrola.z80.opcodes.references.WordNumber;
import jmce.sim.*;

public class RegisterImpl<T extends WordNumber> implements Register {

  private com.fpetrola.z80.registers.Register<T> register;

  public RegisterImpl(com.fpetrola.z80.registers.Register register) {
    this.register = register;
  }

  @Override
  public void setName(String name) {
    // TODO Auto-generated method stub

  }

  @Override
  public String getName() {
    return register.toString();
  }

  @Override
  public void init(Hardware parent) throws SIMException {
    // TODO Auto-generated method stub

  }

  @Override
  public void initSwing(Hardware parent) throws SIMException {
    // TODO Auto-generated method stub

  }

  @Override
  public void reset() throws SIMException {
    // TODO Auto-generated method stub

  }

  @Override
  public void destroy() throws SIMException {
    // TODO Auto-generated method stub

  }

  @Override
  public Hardware getParent() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Hardware addHardware(Hardware h) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int getHardwareCount() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void removeHardware(Hardware h) {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeHardware(int n) {
    // TODO Auto-generated method stub

  }

  @Override
  public Object[] getHardwareInstances(Class c) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Hardware getHardwareTree(Class... classes) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Hardware getHardware(Class clazz) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Hardware getHardware(Class clazz, int n) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setHardware(int n, Hardware h) {
    // TODO Auto-generated method stub

  }

  @Override
  public Hardware getHardware(int n) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Hardware[] getHardware() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setHardware(Hardware[] h) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setRegister(int value) throws SIMException {
    // TODO Auto-generated method stub

  }

  @Override
  public void setResetValue(int value) {
    // TODO Auto-generated method stub

  }

  @Override
  public int getRegister() throws SIMException {
    return register.read().intValue();
  }

  @Override
  public int getWidth() {
    // TODO Auto-generated method stub
    return 16;
  }

  @Override
  public int getFamily() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String hexValue(int value) {
    // TODO Auto-generated method stub
    return Helper.convertToHex(value);
  }

  @Override
  public String hexValue() throws SIMException {
    return Helper.convertToHex(register.read());
  }

  @Override
  public String descValue() throws SIMException {
    // TODO Auto-generated method stub
    return register.read()+"";
  }

  @Override
  public void addRegisterWriteListener(RegisterWriteListener l) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addRegisterReadListener(RegisterReadListener l) {
    // TODO Auto-generated method stub

  }

}
