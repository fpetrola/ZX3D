package com.fpetrola.z80.instructions.base;

import org.cojen.maker.AnnotationMaker;
import org.cojen.maker.ClassMaker;
import org.cojen.maker.FieldMaker;

public class FieldMakerForTest implements FieldMaker {
  private final Object type;
  public String name;
  public FieldForTest field= new FieldForTest();

  public FieldMakerForTest(Object type, String name) {
    this.type = type;
    this.name = name;
  }

  @Override
  public String name() {
    return null;
  }

  @Override
  public FieldMaker public_() {
    return this;
  }

  @Override
  public FieldMaker private_() {
    return null;
  }

  @Override
  public FieldMaker protected_() {
    return null;
  }

  @Override
  public FieldMaker static_() {
    return null;
  }

  @Override
  public FieldMaker final_() {
    return null;
  }

  @Override
  public FieldMaker volatile_() {
    return null;
  }

  @Override
  public FieldMaker transient_() {
    return null;
  }

  @Override
  public FieldMaker synthetic() {
    return null;
  }

  @Override
  public FieldMaker enum_() {
    return null;
  }

  @Override
  public FieldMaker signature(Object... objects) {
    return null;
  }

  @Override
  public ClassMaker classMaker() {
    return null;
  }

  @Override
  public AnnotationMaker addAnnotation(Object o, boolean b) {
    return null;
  }

  @Override
  public void addAttribute(String s, Object o) {

  }

  @Override
  public FieldMaker init(Object o) {
    return null;
  }
}
