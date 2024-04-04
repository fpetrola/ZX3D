package com.fpetrola.z80.transformations;

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
  public List<Virtual8BitsRegister<T>> previousVersions = new ArrayList<>();
  protected T lastData;
  protected int reads;
  public Virtual8BitsRegister<T> lastVersionRead;

  public Virtual8BitsRegister(InstructionExecutor instructionExecutor, String name, Instruction<T> instruction, Virtual8BitsRegister<T> previousVersion, VirtualFetcher<T> virtualFetcher) {
    super(name);
    this.instructionExecutor = instructionExecutor;
    this.instruction = instruction;
    this.virtualFetcher = virtualFetcher;

    addPreviousVersion(previousVersion);

    if (instruction == null)
      this.instruction = new VirtualAssignmentInstruction(this, () -> this.getCurrentPreviousVersion());
  }

  public Virtual8BitsRegister<T> getCurrentPreviousVersion() {
    return previousVersions.isEmpty() ? null : previousVersions.get(previousVersions.size() - 1);
  }

  public T read() {
    T t = virtualFetcher.readFromVirtual(() -> instructionExecutor.execute(instruction), () -> data, () -> (lastVersionRead = getCurrentPreviousVersion()).readPrevious());
    if (data == t)
      reads++;
//    if (reads > 1)
//      System.out.println("uu");
    lastData = null;
    data = t;

    return t;
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
    data = null;
    reads = 0;
  }

  public void addPreviousVersion(Virtual8BitsRegister previousVersion) {
    if (previousVersion != null) {
    //  if (!previousVersions.isEmpty() &&  !previousVersions.contains(previousVersion))
      previousVersions.remove(previousVersion);
      previousVersions.add(previousVersion);
    }
    if (previousVersions.size() > 1) {
      previousVersion.saveData();
    }
  }

  public void saveData() {
    lastData = data;
    data = null;
  }

  private T readPrevious() {
    T result = lastData != null ? lastData : read();
    return result;
  }
}
