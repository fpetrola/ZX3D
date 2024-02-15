package com.fpetrola.z80.opcodes.references;

import com.fpetrola.z80.helpers.Helper;
import com.fpetrola.z80.mmu.Memory;
import com.fpetrola.z80.registers.Register;
import com.fpetrola.z80.spy.InstructionSpy;

public class MemoryPlusRegister8BitReference<T extends WordNumber> implements OpcodeReference<T> {

  private Memory<T> memory;

  public OpcodeReference<T> getTarget() {
    return target;
  }

  private OpcodeReference<T> target;
  private int valueDelta;
  private T fetchedRelative;
  private Register<T> pc;
  private InstructionSpy spy;

  public MemoryPlusRegister8BitReference() {
  }

  public MemoryPlusRegister8BitReference(OpcodeReference target, Memory memory, Register pc, int valueDelta, InstructionSpy spy) {
    this.target = target;
    this.memory = memory;
    this.pc = pc;
    this.valueDelta = valueDelta;
    this.spy = spy;
  }

  public T read() {
    T address = target.read().increment(fetchRelative());
    return memory.read(address);
  }

  public void write(T value) {
    T address = target.read().increment(fetchRelative());
    memory.write(address, value);
  }

  public byte fetchRelative() {
    spy.pause();
    T dd = memory.read(pc.read().increment(valueDelta));
    spy.doContinue();
    fetchedRelative = dd;
    return (byte) fetchedRelative.intValue();
  }

  public int cyclesCost() {
    return 3 + 5 + 4 + 3;
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
    return new MemoryPlusRegister8BitReference((OpcodeReference) target.clone(), memory, pc, valueDelta, spy) {
      public byte fetchRelative() {
        return (byte) lastFetchedRelative.intValue();
      }
    };
  }
}
