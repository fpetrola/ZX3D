package com.fpetrola.z80.instructions.base;

import com.fpetrola.z80.cpu.InstructionExecutor;
import com.fpetrola.z80.cpu.InstructionFetcher;
import com.fpetrola.z80.cpu.SpyInstructionExecutor;
import com.fpetrola.z80.mmu.State;
import com.fpetrola.z80.registers.RegisterPair;
import com.fpetrola.z80.transformations.InstructionFetcherForTest;
import com.fpetrola.z80.opcodes.references.*;
import com.fpetrola.z80.registers.Register;
import com.fpetrola.z80.registers.RegisterName;
import com.fpetrola.z80.spy.InstructionSpy;
import com.fpetrola.z80.transformations.RegisterTransformerInstructionSpy;

import static com.fpetrola.z80.registers.Flags.CARRY_FLAG;
import static com.fpetrola.z80.registers.Flags.ZERO_FLAG;
import static com.fpetrola.z80.registers.RegisterName.B;
import static org.junit.Assert.assertEquals;

public abstract class CPUExecutionContext<T extends WordNumber> extends DefaultZ80InstructionDriver<T> implements Z80ContextDriver<T> {
  OpcodeTargets ot;
  OpcodeConditions opc;
  Register<T> flag;

  public CPUExecutionContext(RegisterTransformerInstructionSpy registerTransformerInstructionSpy) {
    super(registerTransformerInstructionSpy);
    ot = new OpcodeTargets(state);
    flag = state.getFlag();
    opc = new OpcodeConditions(flag, state.getRegister(B));
  }

  protected InstructionFetcher createInstructionFetcher(InstructionSpy spy, State<T> state, InstructionExecutor instructionExecutor) {
    return new InstructionFetcherForTest(this.state, new SpyInstructionExecutor(spy));
  }

  public InstructionFetcherForTest getInstructionFetcherForTest() {
    return (InstructionFetcherForTest) instructionFetcher;
  }

  public int add(Instruction<T> instruction) {
    return getInstructionFetcherForTest().add(instruction);
  }

  public Instruction getInstructionAt(int i) {
    return getInstructionFetcherForTest().getInstructionAt(i);
  }

  public Instruction getTransformedInstructionAt(int i) {
    return getInstructionFetcherForTest().getTransformedInstructionAt(i);
  }

  @Override
  public Register<T> r(RegisterName registerName) {
    return state.r(registerName);
  }

  @Override
  public RegisterPair<T> rp(RegisterName registerName) {
    return (RegisterPair<T>) state.r(registerName);
  }

  @Override
  public Register<T> f() {
    return flag;
  }

  @Override
  public Register<T> pc() {
    return state.getPc();
  }

  @Override
  public OpcodeReference iRR(Register<T> memoryReader) {
    return ot.iRR(memoryReader);
  }

  @Override
  public OpcodeReference iRRn(Register<T> register, int plus) {
    return new CachedMemoryPlusRegister8BitReference(WordNumber.createValue(plus), register, mem(), pc(), 0);
  }

  @Override
  public ImmutableOpcodeReference c(int value) {
    return ot.c(value);
  }

  @Override
  public OpcodeReference iiRR(Register<T> memoryWriter) {
    return ot.iiRR(memoryWriter);
  }

  @Override
  public Condition nz() {
    return opc.nf(ZERO_FLAG);
  }

  @Override
  public BNotZeroCondition bnz() {
    return opc.bnz();
  }

  @Override
  public Condition z() {
    return opc.f(ZERO_FLAG);
  }

  @Override
  public Condition nc() {
    return opc.nf(CARRY_FLAG);
  }

  @Override
  public Condition c() {
    return opc.f(CARRY_FLAG);
  }

  @Override
  public Condition t() {
    return opc.t();
  }

  @Override
  public OpcodeReference nn(ImmutableOpcodeReference<T> r) {
    return new Memory16BitReference(state.getMemory(), r, 0);
  }
}