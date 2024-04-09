package com.fpetrola.z80.jspeccy;

import com.fpetrola.z80.mmu.State;
import com.fpetrola.z80.mmu.State.InterruptionMode;
import com.fpetrola.z80.opcodes.references.WordNumber;
import com.fpetrola.z80.registers.Composed16BitRegister;
import com.fpetrola.z80.registers.Register;
import com.fpetrola.z80.registers.RegisterName;
import com.fpetrola.z80.registers.RegisterPair;
import com.fpetrola.z80.transformations.VirtualRegister;
import com.fpetrola.z80.transformations.VirtualRegisterFactory;
import snapshots.Z80State;
import z80core.Z80.IntMode;

import static com.fpetrola.z80.registers.RegisterName.*;

public abstract class RegistersBase<T extends WordNumber> {

  private State<T> state;

  public RegistersBase() {
    super();
  }

  private Register<T> getVirtualRegister(RegisterName registerName) {
    Register<T> register = getState().getRegister(registerName);
    Register<T> result;

    if (registerName != IY && registerName != IX && register instanceof RegisterPair<T>) {
      RegisterPair<WordNumber> wordNumberRegisterPair = (RegisterPair<WordNumber>) register;
      Register<T> high = (Register<T>) wordNumberRegisterPair.getHigh();
      Register<T> h = (Register) getVirtualRegisterFactory().lastVirtualRegisters.get(high);
      h = h != null ? h : high;
      Register<WordNumber> low = wordNumberRegisterPair.getLow();
      Register<WordNumber> l = (VirtualRegister) getVirtualRegisterFactory().lastVirtualRegisters.get(low);
      l = l != null ? l : low;
      result = new Composed16BitRegister<>(registerName, h, l);
    } else {
      VirtualRegister<T> l = (VirtualRegister) getVirtualRegisterFactory().lastVirtualRegisters.get(register);
      if (l != null) {
        result = l;
      } else {
        result = register;
      }
    }
    return result;
  }

  public void xor(int oper8) {

  }

  public void cp(int oper8) {
  }

  public final int getRegPC() {
    return getState().getRegister(RegisterName.PC).read().intValue();
  }

  public final void setRegPC(int address) {
    getState().getRegister(RegisterName.PC).write(mask16(address));
  }

  public void setFlags(int regF) {
    getFlag().write(mask8(regF));
  }

  private Register<T> getFlag() {
    return getVirtualRegister(F);
    //return getState().getFlag();
  }

  public final void setRegDE(int word) {
    getVirtualRegister(RegisterName.DE).write(mask16(word));
  }

  public final int getRegA() {
    return getVirtualRegister(RegisterName.A).read().intValue();
  }

  public final void setRegA(int value) {
    getVirtualRegister(RegisterName.A).write(mask8(value));
  }

  private T mask8(int value) {
    return WordNumber.createValue(value & 0xff);
  }

  private T mask16(int word) {
    return WordNumber.createValue(word & 0xffff);
  }

  public final int getRegB() {
    return getVirtualRegister(RegisterName.B).read().intValue();
  }

  public final void setRegB(int value) {
    getVirtualRegister(RegisterName.B).write(mask8(value));
  }

  public final int getRegC() {
    return getVirtualRegister(RegisterName.C).read().intValue();
  }

  public final void setRegC(int value) {
    getVirtualRegister(RegisterName.C).write(mask8(value));
  }

  public final int getRegD() {
    return getVirtualRegister(RegisterName.D).read().intValue();
  }

  public final void setRegD(int value) {
    getVirtualRegister(RegisterName.D).write(mask8(value));
  }

  public final int getRegE() {
    return getVirtualRegister(RegisterName.E).read().intValue();
  }

  public final void setRegE(int value) {
    getVirtualRegister(RegisterName.E).write(mask8(value));
  }

  public final int getRegH() {
    return getVirtualRegister(RegisterName.H).read().intValue();
  }

  public final void setRegH(int value) {
    getVirtualRegister(RegisterName.H).write(mask8(value));
  }

  public final int getRegL() {
    return getVirtualRegister(RegisterName.L).read().intValue();
  }

  public final void setRegL(int value) {
    getVirtualRegister(RegisterName.L).write(mask8(value));
  }

  public final int getRegAx() {
    return getVirtualRegister(RegisterName.Ax).read().intValue();
  }

  public final void setRegAx(int value) {
    getVirtualRegister(RegisterName.Ax).write(mask8(value));
  }

  public final int getRegFx() {
    return getVirtualRegister(RegisterName.Fx).read().intValue();
  }

  public final void setRegFx(int value) {
    getVirtualRegister(RegisterName.Fx).write(mask8(value));
  }

  public final int getRegBx() {
    return getVirtualRegister(RegisterName.Bx).read().intValue();
  }

  public final void setRegBx(int value) {
    getVirtualRegister(RegisterName.Bx).write(mask8(value));
  }

  public final int getRegCx() {
    return getVirtualRegister(RegisterName.Cx).read().intValue();
  }

  public final void setRegCx(int value) {
    getVirtualRegister(RegisterName.Cx).write(mask8(value));
  }

  public final int getRegDx() {
    return getVirtualRegister(RegisterName.Dx).read().intValue();
  }

  public final void setRegDx(int value) {
    getVirtualRegister(RegisterName.Dx).write(mask8(value));
  }

  public final int getRegEx() {
    return getVirtualRegister(RegisterName.Ex).read().intValue();
  }

  public final void setRegEx(int value) {
    getVirtualRegister(RegisterName.Ex).write(mask8(value));
  }

  public final int getRegHx() {
    return getVirtualRegister(RegisterName.Hx).read().intValue();
  }

  public final void setRegHx(int value) {
    getVirtualRegister(RegisterName.Hx).write(mask8(value));
  }

  public final int getRegLx() {
    return getVirtualRegister(RegisterName.Lx).read().intValue();
  }

  public final void setRegLx(int value) {
    getVirtualRegister(RegisterName.Lx).write(mask8(value));
  }

  public final int getRegAF() {
    return getVirtualRegister(RegisterName.AF).read().intValue();
  }

  public final void setRegAF(int word) {
    getVirtualRegister(RegisterName.AF).write(mask16(word));
  }

  public final int getRegAFx() {
    return getVirtualRegister(RegisterName.AFx).read().intValue();
  }

  public final void setRegAFx(int word) {
    getVirtualRegister(RegisterName.AFx).write(mask16(word));
  }

  public final int getRegBC() {
    return getVirtualRegister(RegisterName.BC).read().intValue();
  }

  public final void setRegBC(int word) {
    getVirtualRegister(RegisterName.BC).write(mask8(word));
  }

  public final int getFlags() {
    return getFlag().read().intValue();
  }

  public final int getRegHLx() {
    return getVirtualRegister(RegisterName.HLx).read().intValue();
  }

  public final void setRegHLx(int word) {
    getVirtualRegister(RegisterName.HLx).write(mask16(word));
  }

  public final int getRegSP() {
    return getState().getRegister(RegisterName.SP).read().intValue();
  }

  public final void setRegSP(int word) {
    getState().getRegister(RegisterName.SP).write(mask16(word));
  }

  public final int getRegIX() {
    return getVirtualRegister(RegisterName.IX).read().intValue();
  }

  public final void setRegIX(int word) {
    getVirtualRegister(RegisterName.IX).write(mask16(word));
  }

  public final int getRegIY() {
    return getVirtualRegister(RegisterName.IY).read().intValue();
  }

  public final void setRegIY(int word) {
    getVirtualRegister(RegisterName.IY).write(mask16(word));
  }

  public final int getRegI() {
    return getState().getRegister(RegisterName.I).read().intValue();
  }

  public final void setRegI(int value) {
    getState().getRegister(RegisterName.I).write(mask8(value));
  }

  public final int getRegR() {
    return getState().getRegister(RegisterName.R).read().intValue();
  }

  public final void setRegR(int value) {
    getState().getRegister(RegisterName.R).write(mask8(value));
  }

  public final int getPairIR() {
    return getState().getRegister(RegisterName.IR).read().intValue();
  }

  public final int getMemPtr() {
    return getState().getRegister(RegisterName.MEMPTR).read().intValue();
  }

  public final void setMemPtr(int word) {
    getState().getRegister(RegisterName.MEMPTR).write(mask16(word));
  }

  public final boolean isCarryFlag() {
    return (getFlags() & 0x01) != 0;
  }

  public final void setCarryFlag(boolean carryState) {
    Register<T> f = getFlag();
    if (carryState)
      f.write(f.read().or(0x01));
    else
      f.write(f.read().and(0xFE));
  }

  public final int getRegDE() {
    return getVirtualRegister(RegisterName.DE).read().intValue();
  }

  public final void setZ80State(Z80State state) {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    setRegA(state.getRegA());
    setFlags(state.getRegF());
    setRegB(state.getRegB());
    setRegC(state.getRegC());
    setRegD(state.getRegD());
    setRegE(state.getRegE());
    setRegH(state.getRegH());
    setRegL(state.getRegL());
    setRegAx(state.getRegAx());
    setRegFx(state.getRegFx());
    setRegBx(state.getRegBx());
    setRegCx(state.getRegCx());
    setRegDx(state.getRegDx());
    setRegEx(state.getRegEx());
    setRegHx(state.getRegHx());
    setRegLx(state.getRegLx());
    setRegIX(state.getRegIX());
    setRegIY(state.getRegIY());
    setRegSP(state.getRegSP());
    setRegPC(state.getRegPC());
    setRegI(state.getRegI());
    setRegR(state.getRegR());
    setMemptr(state.getMemPtr());
    setHalted(state.isHalted());
    setFfIFF1(state.isIFF1());
    setFfIFF2(state.isIFF2());
    setModeINT(state.getIM());
    setActiveINT(state.isINTLine());
    setPendingEI(state.isPendingEI());
    setActiveNMI(state.isNMI());
    setFlagQ(false);
    setLastFlagQ(state.isFlagQ());

//    getState().updateFromEmulator();
  }

  public State<T> getState() {
    return state;
  }

  public final boolean isIFF1() {
    return isFfIFF1();
  }

  public final void setIFF1(boolean state) {
    setFfIFF1(state);
  }

  public final boolean isIFF2() {
    return isFfIFF2();
  }

  public final void setIFF2(boolean state) {
    setFfIFF2(state);
  }

  public final boolean isNMI() {
    return isActiveNMI();
  }

  public final void setNMI(boolean nmi) {
    setActiveNMI(nmi);
  }

  // La línea de NMI se activa por impulso, no por nivel
  public final void triggerNMI() {
    setActiveNMI(true);
  }

  // La línea INT se activa por nivel
  public final boolean isINTLine() {
    return isActiveINT();
  }

  public void setINTLine(boolean intLine) {
    setActiveINT(intLine);
  }

  // Acceso al modo de interrupción
  public final IntMode getIM() {
    return getModeINT();
  }

  public final void setIM(IntMode mode) {
    setModeINT(mode);
  }

  public final boolean isHalted() {
    return state.isHalted();
  }

  public void setHalted(boolean state) {
    this.state.setHalted(state);
  }

  public void setPinReset() {
    setPinReset(true);
  }

  public final boolean isPendingEI() {
    return state.isPendingEI();
  }

  public final void setPendingEI(boolean state) {
    this.state.setPendingEI(state);
  }

  public final Z80State getZ80State() {
    Z80State state = new Z80State();
    state.setRegA(getRegA());
    state.setRegF(getFlags());
    state.setRegB(getRegB());
    state.setRegC(getRegC());
    state.setRegD(getRegD());
    state.setRegE(getRegE());
    state.setRegH(getRegH());
    state.setRegL(getRegL());
    state.setRegAx(getRegAx());
    state.setRegFx(getRegFx());
    state.setRegBx(getRegBx());
    state.setRegCx(getRegCx());
    state.setRegDx(getRegDx());
    state.setRegEx(getRegEx());
    state.setRegHx(getRegHx());
    state.setRegLx(getRegLx());
    state.setRegIX(getRegIX());
    state.setRegIY(getRegIY());
    state.setRegSP(getRegSP());
    state.setRegPC(getRegPC());
    state.setRegI(getRegI());
    state.setRegR(getRegR());
    state.setMemPtr(getMemptr());
    state.setHalted(isHalted());
    state.setIFF1(isFfIFF1());
    state.setIFF2(isFfIFF2());
    state.setIM(getModeINT());
    state.setINTLine(isActiveINT());
    state.setPendingEI(isPendingEI());
    state.setNMI(isActiveNMI());
    state.setFlagQ(isLastFlagQ());
    return state;
  }

  public boolean isFlagQ() {
    return state.isFlagQ();
  }

  public void setFlagQ(boolean flagQ) {
    this.state.setFlagQ(flagQ);
    ;
  }

  public boolean isLastFlagQ() {
    return state.isFlagQ();
  }

  public void setLastFlagQ(boolean lastFlagQ) {
    this.state.setFlagQ(lastFlagQ);
  }

  public int getMemptr() {
    return state.getRegister(RegisterName.MEMPTR).read().intValue();
  }

  public void setMemptr(int memptr) {
    state.getRegister(RegisterName.MEMPTR).write(mask16(memptr));
  }

  public int getDE() {
    return getVirtualRegister(RegisterName.DE).read().intValue();
  }

  public void setDE(int DE) {
    getVirtualRegister(RegisterName.DE).write(mask16(DE));
  }

  public boolean isActiveINT() {
    return state.isIntLine();
  }

  public void setActiveINT(boolean activeINT) {
    this.state.setINTLine(activeINT);
  }

  public boolean isActiveNMI() {
    return state.isActiveNMI();
  }

  public void setActiveNMI(boolean activeNMI) {
    this.state.setActiveNMI(activeNMI);
  }

  public IntMode getModeINT() {
    return IntMode.values()[state.getInterruptionMode().ordinal()];
  }

  public void setModeINT(IntMode modeINT) {
    state.setIntMode(InterruptionMode.values()[modeINT.ordinal()]);
  }

  public boolean isFfIFF1() {
    return state.isIff1();
  }

  public void setFfIFF1(boolean ffIFF1) {
    this.state.setIff1(ffIFF1);
    ;
  }

  public boolean isFfIFF2() {
    return state.isIff2();
  }

  public void setFfIFF2(boolean ffIFF2) {
    this.state.setIff2(ffIFF2);
    ;
  }

  public boolean isPinReset() {
    return state.isPinReset();
  }

  public void setPinReset(boolean pinReset) {
    this.state.setPinReset(pinReset);
  }

  public void setState(State state) {
    this.state = state;
  }

  public abstract VirtualRegisterFactory getVirtualRegisterFactory();
}