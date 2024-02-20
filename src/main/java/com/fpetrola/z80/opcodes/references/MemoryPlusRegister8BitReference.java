package com.fpetrola.z80.opcodes.references;

import com.fpetrola.z80.helpers.Helper;
import com.fpetrola.z80.mmu.Memory;
import com.fpetrola.z80.registers.Register;
import com.fpetrola.z80.spy.InstructionSpy;

public class MemoryPlusRegister8BitReference<T extends WordNumber> implements OpcodeReference<T> {

  private Memory<T> memory;

  public ImmutableOpcodeReference<T> getTarget() {
    return target;
  }

  private ImmutableOpcodeReference<T> target;
  private int valueDelta;
  private T fetchedRelative;
  private Register<T> pc;
  private InstructionSpy spy;

  public MemoryPlusRegister8BitReference() {
  }

  public MemoryPlusRegister8BitReference(ImmutableOpcodeReference target, Memory memory, Register pc, int valueDelta, InstructionSpy spy) {
    this.target = target;
    this.memory = memory;
    this.pc = pc;
    this.valueDelta = valueDelta;
    this.spy = spy;
  }

  public T read() {
    T address = target.read().plus(fetchRelative());
    return memory.read(address);
  }

  public void write(T value) {
    T address = target.read().plus(fetchRelative());
    memory.write(address, value);
  }

  public byte fetchRelative() {
    spy.pause();
    T dd = memory.read(pc.read().plus(valueDelta));
    spy.doContinue();
    fetchedRelative = dd;
    return (byte) fetchedRelative.intValue();
  }

  public String toString() {
    byte dd = fetchRelative();
    String string2 = (dd > 0 ? "+" : "-") + Helper.convertToHex(Math.abs(dd));
    return "(" + target.toString() + string2 + ")";
  }

  public int getLength() {
    return 1;
  }

  public Object clone() throws CloneNotSupportedException {
    T lastFetchedRelative = fetchedRelative;
    return new MemoryPlusRegister8BitReference((ImmutableOpcodeReference) target.clone(), memory, pc, valueDelta, spy) {
      public byte fetchRelative() {
        return (byte) lastFetchedRelative.intValue();
      }
    };
  }
}
