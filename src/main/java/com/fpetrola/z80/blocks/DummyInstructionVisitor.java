package com.fpetrola.z80.blocks;

import com.fpetrola.z80.instructions.*;
import com.fpetrola.z80.instructions.base.*;
import com.fpetrola.z80.instructions.transformations.VirtualAssignmentInstruction;
import com.fpetrola.z80.opcodes.references.*;

public class DummyInstructionVisitor<T extends WordNumber> implements InstructionVisitor<T> {
  @Override
  public void visitingTargetInstruction(TargetInstruction targetInstruction) {
  }

  @Override
  public void visitingInstruction(AbstractInstruction tAbstractInstruction) {

  }

  @Override
  public void visitingAdd(Add add) {

  }

  @Override
  public void visitingAdd16(Add16 tAdd16) {

  }

  @Override
  public void visitingAnd(And tAnd) {

  }

  @Override
  public void visitingDec(Dec dec) {

  }

  @Override
  public void visitingDec16(Dec16 tDec16) {

  }

  @Override
  public void visitingInc(Inc tInc) {

  }

  @Override
  public void visitingOr(Or tOr) {

  }

  @Override
  public void visitingSub(Sub tSub) {

  }

  @Override
  public void visitingXor(Xor tXor) {

  }

  @Override
  public void visitingCp(Cp tCp) {

  }

  @Override
  public void visitingRet(Ret ret) {

  }

  @Override
  public void visitingCall(Call tCall) {

  }

  @Override
  public void visitingConditionalInstruction(ConditionalInstruction conditionalInstruction) {
  }

  @Override
  public void visitingInc16(Inc16 tInc16) {

  }

  @Override
  public void visitingSet(SET set) {

  }

  @Override
  public void visitingRes(RES res) {

  }

  @Override
  public void visitingBit(BIT bit) {

  }

  @Override
  public void visitingDjnz(DJNZ djnz) {

  }

  @Override
  public void visitingLd(Ld ld) {

  }

  @Override
  public void visitingRla(RLA rla) {

  }

  @Override
  public void visitingRl(RL rl) {

  }

  @Override
  public void visitingRst(RST rst) {

  }

  @Override
  public void visitingIm(IM im) {

  }

  @Override
  public void visitingJR(JR jr) {

  }

  @Override
  public void visitingConditionAlwaysTrue(ConditionAlwaysTrue conditionAlwaysTrue) {

  }

  @Override
  public void visitingConditionFlag(ConditionFlag conditionFlag) {

  }

  @Override
  public void visitingParameterizedUnaryAluInstruction(ParameterizedUnaryAluInstruction parameterizedUnaryAluInstruction) {

  }

  @Override
  public void visitingParameterizedBinaryAluInstruction(ParameterizedBinaryAluInstruction tParameterizedBinaryAluInstruction) {

  }

  @Override
  public void visitingBitOperation(BitOperation tBitOperation) {

  }

  @Override
  public void visitingPop(Pop tPop) {

  }

  @Override
  public void visitingJP(JP tjp) {

  }

  @Override
  public void visitingVirtualAssignmentInstruction(VirtualAssignmentInstruction tVirtualAssignmentInstruction) {
  }

  @Override
  public void visitingTarget(OpcodeReference target, TargetInstruction targetInstruction) {

  }

  @Override
  public void visitingSource(ImmutableOpcodeReference source, TargetSourceInstruction targetSourceInstruction) {
  }

  @Override
  public void visitingTargetSourceInstruction(TargetSourceInstruction targetSourceInstruction) {
  }
}
