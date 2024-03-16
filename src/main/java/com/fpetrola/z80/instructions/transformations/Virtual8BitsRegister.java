package com.fpetrola.z80.instructions.transformations;

import com.fpetrola.z80.cpu.InstructionExecutor;
import com.fpetrola.z80.instructions.base.Instruction;
import com.fpetrola.z80.opcodes.references.WordNumber;
import com.fpetrola.z80.registers.Plain8BitRegister;

import java.util.ArrayList;
import java.util.List;

public class Virtual8BitsRegister<T extends WordNumber> extends Plain8BitRegister<T> implements VirtualRegister<T> {
  private final InstructionExecutor instructionExecutor;
  private Instruction<T> instruction;
  private VirtualFetcher<T> virtualFetcher;
  private List<VirtualRegister<T>> lastRegisters = new ArrayList<>();

  protected T lastData;
  protected boolean cleared;

  public Virtual8BitsRegister(InstructionExecutor instructionExecutor, String name, Instruction<T> instruction, VirtualRegister<T> lastRegister, VirtualFetcher<T> virtualFetcher) {
    super(name);
    this.instructionExecutor = instructionExecutor;
    this.instruction = instruction;
    addLastRegister(lastRegister);
    this.virtualFetcher = virtualFetcher;

    if (instruction == null)
      this.instruction = new VirtualAssignmentInstruction(this, () -> this.getLastRegister());
  }

  public VirtualRegister<T> getLastRegister() {
    return lastRegisters.isEmpty() ? null : lastRegisters.get(lastRegisters.size() - 1);
  }

  public T read() {
    T t = virtualFetcher.readFromVirtual(() -> instructionExecutor.execute(instruction), () -> cleared ? null : data, () -> lastData != null ? lastData : getLastRegister().read(), instruction);
    write(cleared && lastData != null ? null : t);
    return t;
  }

  public void write(T value) {
    cleared = false;
    lastData = null;
    super.write(value);
  }

  public void decrement() {
    read();
    super.decrement();
  }

  public void increment() {
    read();
    super.increment();
  }

  public void reset() {
    if (!cleared) {
      data = null;
      lastData = null;
    }
  }

  public boolean addLastRegister(VirtualRegister lastRegister) {
    if (lastRegister != null) {
      lastRegisters.remove(lastRegister);
      lastRegisters.add(lastRegister);
    }
    return lastRegisters.size() > 1;
  }

  public void clear() {
    cleared = true;
    if (data != null) {
      lastData = data;
      data = null;
    }
  }
}
