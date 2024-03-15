package com.fpetrola.z80.instructions.transformations;

import com.fpetrola.z80.cpu.InstructionExecutor;
import com.fpetrola.z80.instructions.base.Instruction;
import com.fpetrola.z80.opcodes.references.WordNumber;
import com.fpetrola.z80.registers.Plain8BitRegister;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Virtual8BitsRegister<T extends WordNumber> extends Plain8BitRegister<T> implements VirtualRegister<T> {
  private final InstructionExecutor instructionExecutor;
  private Instruction<T> instruction;
  private VirtualFetcher<T> virtualFetcher;
  private List<VirtualRegister<T>> lastRegisters = new ArrayList<>();

  private int updateTick;
  private T lastData;

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
    Optional<VirtualRegister<T>> max = lastRegisters.stream().max(Comparator.comparing(VirtualRegister::getUpdateTick));
    return max.orElse(null);
  }

  public T read() {
    T t = virtualFetcher.readFromVirtual(() -> instructionExecutor.execute(instruction), () -> data, () -> lastData != null ? lastData : getLastRegister().read());
    write(t);
    return t;
  }

  public void write(T value) {
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
    lastData = data;
    data = null;
  }

  public void setUpdateTick(int tick) {
    this.updateTick = tick;
  }

  @Override
  public int getUpdateTick() {
    return updateTick;
  }

  public void addLastRegister(VirtualRegister lastRegister) {
    if (lastRegister != null && !lastRegisters.contains(lastRegister)) {
      lastRegisters.add(lastRegister);
    }
  }
}
