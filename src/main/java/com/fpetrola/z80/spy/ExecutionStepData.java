package com.fpetrola.z80.spy;

import java.util.ArrayList;
import java.util.List;

import com.fpetrola.z80.OOZ80;
import com.fpetrola.z80.instructions.base.Instruction;
import com.fpetrola.z80.mmu.Memory;
import com.fpetrola.z80.registers.RegisterName;

public class ExecutionStepData {

  public List<WriteOpcodeReference> writeReferences = new ArrayList<>();
  public List<ReadOpcodeReference> readReferences = new ArrayList<>();
  public List<WriteMemoryReference> writeMemoryReferences = new ArrayList<>();
  public List<ReadMemoryReference> readMemoryReferences = new ArrayList<>();
  transient public List<Object> accessReferences = new ArrayList<>();
  transient public Instruction instruction;
  public String instructionToString;
  public int opcodeInt;
  public int pcValue;
  transient private Memory memory;
  public int i;

  public ExecutionStepData(Memory memory) {
    this.memory = memory;
  }

  public WriteOpcodeReference addWriteReference(RegisterName opcodeReference, int value, boolean isIncrement, boolean indirectReference) {
    WriteOpcodeReference e = new WriteOpcodeReference(opcodeReference, value, isIncrement, indirectReference);
    writeReferences.add(e);
    addAccessReference(e);
    return e;
  }

  private void addAccessReference(Undoable e) {
    accessReferences.add(e);
  }

  public ReadOpcodeReference addReadReference(RegisterName opcodeReference, int value, boolean indirectReference) {
    ReadOpcodeReference e = new ReadOpcodeReference(opcodeReference, value, indirectReference);
    readReferences.add(e);
    addAccessReference(e);
    return e;
  }

  protected void clear() {
    writeReferences.clear();
    readReferences.clear();
    writeMemoryReferences.clear();
    readMemoryReferences.clear();
  }

  public WriteMemoryReference addWriteMemoryReference(int address, int value, boolean indirectReference) {
    WriteMemoryReference e = new WriteMemoryReference(address, value, memory, indirectReference);
    writeMemoryReferences.add(e);
    addAccessReference(e);
    return e;
  }

  public ReadMemoryReference addReadMemoryReference(int address, int value, boolean indirectReference) {
    ReadMemoryReference e = new ReadMemoryReference(address, value, memory, indirectReference);
    readMemoryReferences.add(e);
    addAccessReference(e);
    return e;
  }

  public void undo() {
//    accessReferences.forEach(ar -> ar.undo());
  }

  void printOpCodeHeader() {
    System.out.println(pcValue + " -------------------------------------------------");
    System.out.println(instruction + " (" + OOZ80.convertToHex(opcodeInt) + ")");
  }

  public void setIndex(int i) {
    this.i = i;
  }
}