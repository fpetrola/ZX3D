package com.fpetrola.z80.opcodes.references;

import java.util.Set;

public class IntegerWordNumber implements WordNumber {
  private int value;

  public IntegerWordNumber(int aValue) {
    this.value = aValue;
  }

  @Override
  public <T extends WordNumber> T plus(int i) {
    return (T) new IntegerWordNumber(value + i);
  }

  @Override
  public <T extends WordNumber> T minus(int i) {
    return (T) new IntegerWordNumber(value - i);
  }

  @Override
  public <T extends WordNumber> T plus(byte i) {
    return (T) new IntegerWordNumber(value + i);
  }

  @Override
  public <T extends WordNumber> T left(int i) {
    return (T) new IntegerWordNumber(value << i);
  }

  @Override
  public <T extends WordNumber> T right(int i) {
    return (T) new IntegerWordNumber(value >>> i);
  }

  @Override
  public <T extends WordNumber> T or(int i) {
    return (T) new IntegerWordNumber(value | i);
  }

  @Override
  public <T extends WordNumber> T and(int i) {
    return (T) new IntegerWordNumber(value & i);
  }

  @Override
  public <T extends WordNumber> T xor(int i) {
    return (T) new IntegerWordNumber(value ^ i);
  }

  @Override
  public <T extends WordNumber> T or(T wordNumber) {
    return or(wordNumber.intValue());
  }

  @Override
  public <T extends WordNumber> T and(T wordNumber) {
    return and(wordNumber.intValue());

  }

  @Override
  public <T extends WordNumber> T xor(T wordNumber) {
    return xor(wordNumber.intValue());
  }

  @Override
  public <T extends WordNumber> T plus(T wordNumber) {
    return plus(wordNumber.intValue());

  }

  @Override
  public boolean notEquals(int i) {
    return value != i;
  }

  @Override
  public <T extends WordNumber> byte byteValue() {
    return (byte) value;
  }

  @Override
  public int intValue() {
    return value;
  }

  @Override
  public void set(int read) {
    this.value= read;
  }
}
