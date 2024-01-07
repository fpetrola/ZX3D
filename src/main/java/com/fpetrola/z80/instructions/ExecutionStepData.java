package com.fpetrola.z80.instructions;

import java.util.ArrayList;
import java.util.List;

import com.fpetrola.z80.mmu.Memory;

public class ExecutionStepData {

  List<WriteOpcodeReference> writeReferences = new ArrayList<>();
  List<ReadOpcodeReference> readReferences = new ArrayList<>();
  List<WriteMemoryReference> writeMemoryReferences = new ArrayList<>();
  List<ReadMemoryReference> readMemoryReferences = new ArrayList<>();
  List<Undoable> accessReferences = new ArrayList<>();
  OpCode opcode;
  public int opcodeInt;
  public int pcValue;
  private Memory memory;

  public ExecutionStepData(Memory memory) {
    this.memory = memory;
  }

  public WriteOpcodeReference addWriteReference(OpcodeReference opcodeReference, int value, boolean isIncrement) {
    WriteOpcodeReference e = new WriteOpcodeReference(opcodeReference, value, isIncrement);
    writeReferences.add(e);
    accessReferences.add(e);
    return e;
  }

  public ReadOpcodeReference addReadReference(OpcodeReference opcodeReference, int value) {
    ReadOpcodeReference e = new ReadOpcodeReference(opcodeReference, value);
    readReferences.add(e);
    accessReferences.add(e);
    return e;
  }

  protected void clear() {
    writeReferences.clear();
    readReferences.clear();
    writeMemoryReferences.clear();
    readMemoryReferences.clear();
  }

  public WriteMemoryReference addWriteMemoryReference(int address, int value) {
    WriteMemoryReference e = new WriteMemoryReference(address, value, memory);
    writeMemoryReferences.add(e);
    accessReferences.add(e);
    return e;
  }

  public ReadMemoryReference addReadMemoryReference(int address, int value) {
    ReadMemoryReference e = new ReadMemoryReference(address, value, memory);
    readMemoryReferences.add(e);
    accessReferences.add(e);
    return e;
  }

  public void undo() {
    accessReferences.forEach(ar-> ar.undo());
  }
}