package com.fpetrola.z80.transformations;

import com.fpetrola.z80.blocks.DummyInstructionVisitor;
import com.fpetrola.z80.instructions.*;
import com.fpetrola.z80.instructions.base.*;
import com.fpetrola.z80.opcodes.references.*;
import com.fpetrola.z80.registers.Register;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("ALL")
public class InstructionTransformer<T extends WordNumber> extends InstructionTransformerBase<T> {
  public final VirtualRegisterFactory virtualRegisterFactory;

  public void setCurrentInstruction(Instruction currentInstruction) {
    this.currentInstruction = currentInstruction;
  }

  private Instruction currentInstruction;

  public InstructionTransformer(InstructionFactory instructionFactory, VirtualRegisterFactory virtualRegisterFactory) {
    super(instructionFactory);
    this.virtualRegisterFactory = virtualRegisterFactory;
  }

  public void visitingLd(Ld ld) {
    setCloned(instructionFactory.Ld(clone(ld.getTarget()), clone(ld.getSource())), ld);
    TargetSourceInstruction cloned1 = (TargetSourceInstruction) cloned;

    cloned1.setTarget(createRegisterReplacement(cloned1.getTarget(), cloned1, new VirtualFetcher()));
    cloned1.setSource(createRegisterReplacement(cloned1.getSource(), null, new VirtualFetcher()));
  }

  public void visitingInc16(Inc16 inc16) {
    setCloned(instructionFactory.Inc16(clone(inc16.getTarget())), inc16);
    Inc16 cloned1 = (Inc16) cloned;
    cloned1.setTarget(createRegisterReplacement(cloned1.getTarget(), cloned1, new VirtualFetcher()));
  }

  public void visitingDjnz(DJNZ djnz) {
    setCloned(instructionFactory.DJNZ(djnz.getPositionOpcodeReference()), djnz);
    DJNZ djnz1 = (DJNZ) cloned;
    djnz1.setB(createRegisterReplacement(djnz1.getB(), null, new VirtualFetcher()));
  }

  public void visitingJR(JR jr) {
    setCloned(instructionFactory.JR(clone(jr.getCondition()), clone(jr.getPositionOpcodeReference())), jr);
    JR clonedJr = (JR) cloned;
    clonedJr.accept(new DummyInstructionVisitor() {
      public void visitingConditionFlag(ConditionFlag conditionFlag) {
        conditionFlag.setRegister(createRegisterReplacement(conditionFlag.getRegister(), null, new VirtualFetcher()));
      }
    });
  }

  @Override
  public void visitingRet(Ret ret) {
    setCloned(instructionFactory.Ret(clone(ret.getCondition())), ret);
    Ret clonedRet = (Ret) cloned;
    clonedRet.accept(new DummyInstructionVisitor() {
      public void visitingConditionFlag(ConditionFlag conditionFlag) {
        conditionFlag.setRegister(createRegisterReplacement(conditionFlag.getRegister(), null, new VirtualFetcher()));
      }
    });
  }

  public void visitNop(Nop nop) {
    setCloned(instructionFactory.Nop(), nop);
  }

  @Override
  public void visitDI(DI tdi) {
    setCloned(instructionFactory.DI(), tdi);
  }

  @Override
  public void visitEI(EI ei) {
    setCloned(instructionFactory.EI(), ei);
  }

  @Override
  public void visitingCcf(CCF ccf) {
    setCloned(instructionFactory.CCF(), ccf);
  }

  @Override
  public void visitingScf(SCF scf) {
    setCloned(instructionFactory.SCF(), scf);
  }

  @Override
  public void visitingParameterizedUnaryAluInstruction(ParameterizedUnaryAluInstruction parameterizedUnaryAluInstruction) {
    super.visitingParameterizedUnaryAluInstruction(parameterizedUnaryAluInstruction);
    ParameterizedUnaryAluInstruction cloned1 = (ParameterizedUnaryAluInstruction) cloned;
    VirtualFetcher virtualFetcher = new VirtualFetcher();
    cloned1.setTarget(createRegisterReplacement(cloned1.getTarget(), cloned1, virtualFetcher));
    cloned1.setFlag(createRegisterReplacement(cloned1.getFlag(), cloned1, virtualFetcher));
  }

  @Override
  public void visitingJP(JP jp) {
    setCloned(instructionFactory.JP(clone(jp.getPositionOpcodeReference()), clone(jp.getCondition())), jp);
    JP clonedJp = (JP) cloned;
    clonedJp.accept(new DummyInstructionVisitor() {
      public void visitingConditionFlag(ConditionFlag conditionFlag) {
        conditionFlag.setRegister(createRegisterReplacement(conditionFlag.getRegister(), null, new VirtualFetcher()));
      }
    });
  }

  @Override
  public void visitEx(Ex ex) {
    setCloned(instructionFactory.Ex(clone(ex.getTarget()), clone(ex.getSource())), ex);
    Ex clonedEx = (Ex) cloned;
    clonedEx.setTarget(createRegisterReplacement(clonedEx.getTarget(), clonedEx, new VirtualFetcher()));
    clonedEx.setSource(createRegisterReplacement(clonedEx.getSource(), null, new VirtualFetcher()));
  }

  public void visitingCall(Call call) {
    setCloned(instructionFactory.Call(clone(call.getCondition()), clone(call.getPositionOpcodeReference())), call);
    Call clonedCall = (Call) cloned;
    clonedCall.accept(new DummyInstructionVisitor() {
      public void visitingConditionFlag(ConditionFlag conditionFlag) {
        conditionFlag.setRegister(createRegisterReplacement(conditionFlag.getRegister(), null, new VirtualFetcher()));
      }
    });
  }

  public void visitIn(In in) {
    setCloned(instructionFactory.In(clone(in.getTarget()), clone(in.getSource())), in);
    TargetSourceInstruction cloned1 = (TargetSourceInstruction) cloned;

    cloned1.setTarget(createRegisterReplacement(cloned1.getTarget(), cloned1, new VirtualFetcher()));
    cloned1.setSource(createRegisterReplacement(cloned1.getSource(), null, new VirtualFetcher()));
  }

  public void visitOut(Out out) {
    setCloned(instructionFactory.Out(clone(out.getTarget()), clone(out.getSource())), out);
    TargetSourceInstruction cloned1 = (TargetSourceInstruction) cloned;

   // cloned1.setTarget(createRegisterReplacement(cloned1.getTarget(), cloned1, new VirtualFetcher()));
    cloned1.setSource(createRegisterReplacement(cloned1.getSource(), null, new VirtualFetcher()));
  }

  public void visitExx(Exx exx) {
    setCloned(instructionFactory.Exx(), exx);
    Exx cloned1 = (Exx) cloned;

    cloned1.setBc(createRegisterReplacement(cloned1.getBc(), cloned1, new VirtualFetcher()));
    cloned1.set_bc(createRegisterReplacement(cloned1.get_bc(), cloned1, new VirtualFetcher()));
    cloned1.setDe(createRegisterReplacement(cloned1.getDe(), cloned1, new VirtualFetcher()));
    cloned1.set_de(createRegisterReplacement(cloned1.get_de(), cloned1, new VirtualFetcher()));
    cloned1.setHl(createRegisterReplacement(cloned1.getHl(), cloned1, new VirtualFetcher()));
    cloned1.set_hl(createRegisterReplacement(cloned1.get_hl(), cloned1, new VirtualFetcher()));
  }

  @Override
  public void visitingDec16(Dec16 dec16) {
    setCloned(instructionFactory.Dec16(clone(dec16.getTarget())), dec16);
    Dec16 cloned1 = (Dec16) cloned;

    cloned1.setTarget(createRegisterReplacement(cloned1.getTarget(), cloned1, new VirtualFetcher()));
  }

  public void visitingBitOperation(BitOperation bitOperation) {
    Constructor<?>[] constructors = bitOperation.getClass().getConstructors();
    try {
      cloned = (AbstractInstruction) constructors[0].newInstance(clone(bitOperation.getTarget()), bitOperation.getN(), bitOperation.getFlag());
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }

    BitOperation cloned1 = (BitOperation) cloned;

    VirtualFetcher virtualFetcher = new VirtualFetcher();

    cloned1.setTarget(createRegisterReplacement(cloned1.getTarget(), cloned1, virtualFetcher));
    cloned1.setFlag(createRegisterReplacement(cloned1.getFlag(), cloned1, virtualFetcher));
  }

  public void visitingRst(RST rst) {
    setCloned(instructionFactory.RST(rst.getP()), rst);
  }

  public void visitPush(Push push) {
    setCloned(instructionFactory.Push(clone(push.getTarget())), push);
    Push cloned1 = (Push) cloned;

    cloned1.setTarget(createRegisterReplacement(cloned1.getTarget(), cloned1, new VirtualFetcher()));
  }

  @Override
  public void visitingPop(Pop pop) {
    setCloned(instructionFactory.Pop(clone(pop.getTarget())), pop);
    Pop cloned1 = (Pop) cloned;
    cloned1.setTarget(createRegisterReplacement(cloned1.getTarget(), cloned1, new VirtualFetcher()));
  }

  public void visitLdir(Ldir ldir) {
    setCloned(instructionFactory.Ldir(), ldir);
    Ldir cloned1 = (Ldir) cloned;
    VirtualFetcher virtualFetcher = new VirtualFetcher();
    cloned1.setB(createRegisterReplacement(cloned1.getB(), cloned1, virtualFetcher));
    cloned1.setBc(createRegisterReplacement(cloned1.getBc(), cloned1, virtualFetcher));
  }

  @Override
  public void visitLddr(Lddr lddr) {
    setCloned(instructionFactory.Lddr(), lddr);
    Lddr cloned1 = (Lddr) cloned;
    VirtualFetcher virtualFetcher = new VirtualFetcher();
    cloned1.setB(createRegisterReplacement(cloned1.getB(), cloned1, virtualFetcher));
    cloned1.setBc(createRegisterReplacement(cloned1.getBc(), cloned1, virtualFetcher));
  }

  private <R extends PublicCloneable> R createRegisterReplacement(R cloneable, Instruction currentInstruction1, VirtualFetcher virtualFetcher) {
    if (cloneable instanceof IndirectMemory8BitReference indirectMemory8BitReference) {
      OpcodeReference target1 = (OpcodeReference) indirectMemory8BitReference.target;
      ImmutableOpcodeReference result;
      if (target1 instanceof Register register) {
        result = virtualRegisterFactory.createVirtualRegister(null, register, virtualFetcher);
      } else {
        result = clone(indirectMemory8BitReference.target);
      }

      return (R) new IndirectMemory8BitReference(result, indirectMemory8BitReference.getMemory());
    } else if (cloneable instanceof IndirectMemory16BitReference indirectMemory16BitReference) {
      OpcodeReference target1 = (OpcodeReference) indirectMemory16BitReference.target;
      ImmutableOpcodeReference result;
      if (target1 instanceof Register register) {
        result = virtualRegisterFactory.createVirtualRegister(null, register, virtualFetcher);
      } else {
        result = clone(indirectMemory16BitReference.target);
      }

      return (R) new IndirectMemory16BitReference(result, indirectMemory16BitReference.getMemory());
    } else if (cloneable instanceof Register register) {
      return (R) virtualRegisterFactory.createVirtualRegister(currentInstruction1, register, virtualFetcher);
    } else
      return super.clone(cloneable);
  }


  @Override
  public void visitingParameterizedBinaryAluInstruction(ParameterizedBinaryAluInstruction parameterizedBinaryAluInstruction) {
    super.visitingParameterizedBinaryAluInstruction(parameterizedBinaryAluInstruction);
    ParameterizedBinaryAluInstruction cloned1 = (ParameterizedBinaryAluInstruction) cloned;
    VirtualFetcher virtualFetcher = new VirtualFetcher();
    OpcodeReference targetReplacement = createRegisterReplacement(cloned1.getTarget(), cloned1, virtualFetcher);
    if (cloned1.getTarget() == cloned1.getSource()) {
      cloned1.setTarget(targetReplacement);
      cloned1.setSource(targetReplacement);
    } else {
      cloned1.setTarget(targetReplacement);
      cloned1.setSource(createRegisterReplacement(cloned1.getSource(), cloned1, virtualFetcher));
    }
    cloned1.setFlag(createRegisterReplacement(cloned1.getFlag(), cloned1, virtualFetcher));
  }
}
