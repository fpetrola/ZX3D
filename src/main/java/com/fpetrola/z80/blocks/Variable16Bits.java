package com.fpetrola.z80.blocks;

import org.cojen.maker.*;

public class Variable16Bits implements Variable {
  public Object variableLow;
  public Object variableHigh;

  public Variable16Bits(Object variableLow, Object variableHigh) {
    this.variableLow = variableLow;
    this.variableHigh = variableHigh;
  }

  public Variable get1() {
    if (variableLow instanceof Variable low)
      if (variableHigh instanceof Variable high) {
        return high.shl(8).or(low);
      }

    return this;
  }

  @Override
  public Variable set(Object o) {
//    if (o instanceof Integer integer) {
//      if (variableLow instanceof Variable variable)
//        variableLow = variable.set(integer & 0xFF);
//      if (variableHigh instanceof Variable variable)
//        variableHigh = variable.set(integer >> 8);
//    } else if (o instanceof Variable16Bits variable16Bits) {
//      if (variableLow instanceof Variable variable)
//        variableLow = variable.set(variable16Bits.variableLow);
//      if (variableHigh instanceof Variable variable)
//        variableHigh = variable.set(variable16Bits.variableHigh);
//    } else if (o instanceof Variable variable1) {
//      if (variableLow instanceof Variable variable)
//        variableLow = variable.set(variable1.and(0xFF));
//      if (variableHigh instanceof Variable variable)
//        variableHigh = variable.set(variable1.shr(8));
//    }

    Variable16Bits v = (Variable16Bits) o;
    variableLow = ((Variable) variableLow).set(v.variableLow);
    variableHigh = ((Variable) variableHigh).set(v.variableHigh);
    return this;
  }

  @Override
  public Variable xor(Object o) {
    return this;
  }

  @Override
  public Class<?> classType() {
    return variableLow instanceof Variable ? ((Variable) variableLow).classType() : int.class;
  }

  @Override
  public ClassMaker makerType() {
    return null;
  }

  @Override
  public String name() {
    return null;
  }

  @Override
  public Variable name(String s) {
    return null;
  }

  @Override
  public Variable signature(Object... objects) {
    return null;
  }

  @Override
  public AnnotationMaker addAnnotation(Object o, boolean b) {
    return null;
  }

  @Override
  public Variable clear() {
    return null;
  }

  @Override
  public Variable setExact(Object o) {
    return null;
  }

  @Override
  public Variable get() {
    return null;
  }

  @Override
  public void ifTrue(Label label) {

  }

  @Override
  public void ifFalse(Label label) {

  }

  @Override
  public void ifEq(Object o, Label label) {

  }

  @Override
  public void ifNe(Object o, Label label) {

  }

  @Override
  public void ifLt(Object o, Label label) {

  }

  @Override
  public void ifGe(Object o, Label label) {

  }

  @Override
  public void ifGt(Object o, Label label) {

  }

  @Override
  public void ifLe(Object o, Label label) {

  }

  @Override
  public void switch_(Label label, int[] ints, Label... labels) {

  }

  @Override
  public void switch_(Label label, String[] strings, Label... labels) {

  }

  @Override
  public void switch_(Label label, Enum<?>[] enums, Label... labels) {

  }

  @Override
  public void switch_(Label label, Object[] objects, Label... labels) {

  }

  @Override
  public void inc(Object o) {
    ((Variable) variableLow).inc((Integer) o & 0xff);
    ((Variable) variableHigh).inc((Integer) o >> 8);
  }

  @Override
  public Variable add(Object o) {
    Variable16Bits v = (Variable16Bits) o;
    Variable variableLow1 = ((Variable) variableLow).add(v.variableLow);
    Variable variableHigh1 = ((Variable) variableHigh).add(v.variableHigh);
    return new Variable16Bits(variableLow1, variableHigh1);
  }

  @Override
  public Variable sub(Object o) {
    return null;
  }

  @Override
  public Variable mul(Object o) {
    return null;
  }

  @Override
  public Variable div(Object o) {
    return null;
  }

  @Override
  public Variable rem(Object o) {
    return null;
  }

  @Override
  public Variable eq(Object o) {
    return null;
  }

  @Override
  public Variable ne(Object o) {
    return null;
  }

  @Override
  public Variable lt(Object o) {
    return null;
  }

  @Override
  public Variable ge(Object o) {
    return null;
  }

  @Override
  public Variable gt(Object o) {
    return null;
  }

  @Override
  public Variable le(Object o) {
    return null;
  }

  @Override
  public Variable instanceOf(Object o) {
    return null;
  }

  @Override
  public Variable cast(Object o) {
    return null;
  }

  @Override
  public Variable not() {
    return null;
  }

  @Override
  public Variable and(Object o) {
    return null;
  }

  @Override
  public Variable or(Object o) {
    return null;
  }

  @Override
  public Variable shl(Object o) {
    return null;
  }

  @Override
  public Variable shr(Object o) {
    return null;
  }

  @Override
  public Variable ushr(Object o) {
    return null;
  }

  @Override
  public Variable neg() {
    return null;
  }

  @Override
  public Variable com() {
    return null;
  }

  @Override
  public Variable box() {
    return null;
  }

  @Override
  public Variable unbox() {
    return null;
  }

  @Override
  public Variable alength() {
    return null;
  }

  @Override
  public Variable aget(Object o) {
    return null;
  }

  @Override
  public void aset(Object o, Object o1) {

  }

  @Override
  public Field field(String s) {
    return null;
  }

  @Override
  public Variable invoke(String s, Object... objects) {
    return null;
  }

  @Override
  public Variable invoke(Object o, String s, Object[] objects, Object... objects1) {
    return null;
  }

  @Override
  public Variable methodHandle(Object o, String s, Object... objects) {
    return null;
  }

  @Override
  public Bootstrap indy(String s, Object... objects) {
    return null;
  }

  @Override
  public Bootstrap condy(String s, Object... objects) {
    return null;
  }

  @Override
  public void throw_() {

  }

  @Override
  public void monitorEnter() {

  }

  @Override
  public void monitorExit() {

  }

  @Override
  public void synchronized_(Runnable runnable) {

  }

  @Override
  public MethodMaker methodMaker() {
    return null;
  }
}
