package com.fpetrola.z80.jspeccy;

import com.fpetrola.z80.mmu.Memory;

import z80core.MemIoOps;

public class MemoryImplementation implements Memory {
  private MemIoOps memory;
  int[] data = new int[0x10000];

  MemoryWriteListener memoryWriteListener;

  public MemoryImplementation(MemIoOps memory2) {
    this.memory = memory2;
  }

  public void update() {
    for (int i = 0; i < 0xFFFF; i++) {
      int j = memory.peek82(i) & 0xFF;
//      memory.poke82(i, j);
      data[i] = j;
    }
  }

  public int read(int address) {
    return data[address & 0xFFFF] & 0xff;
  }

  @Override
  public void write(int address, int value) {

    byte b = (byte) (value & 0xFF);
//    byte peek84 = (byte) memory.peek84(address);
//    if (peek84 != b) {
//      System.out.println("dsgadg");
//    }

    if (memoryWriteListener != null)
      memoryWriteListener.writtingMemoryAt(address, value);

    data[address & 0xffff] = b;
    memory.poke8(address & 0xffff, value);
  }

  public boolean compare() {
    for (int i = 0; i < 0xFFFF; i++) {
      int j = memory.peek82(i) & 0xFF;
      if (data[i] != j)
        return false;
    }
    return true;
  }

  @Override
  public void setMemoryWriteListener(MemoryWriteListener memoryWriteListener) {
    this.memoryWriteListener = memoryWriteListener;
  }
}