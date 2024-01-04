package com.fpetrola.z80;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fpetrola.z80.instructions.OpCode;
import com.fpetrola.z80.mmu.Memory;
import com.fpetrola.z80.registers.RegisterBank;

public class MemoryProxy implements Memory {

  private static Memory memory;
  public static List<WriteAction> changes = new ArrayList<>();
  private static long lastTime = System.currentTimeMillis();
  private static boolean capturing;
  private static RegisterBank savedRegisterBank;
  private static boolean stateSaved;
  private static Object memoryState;
  private static boolean firstCapture;

  public MemoryProxy(Memory memory) {
    this.memory = memory;
  }

  public int read(int address, boolean log) {
    return memory.read(address, log);
  }

  public void write(int address, int value) {
//    if (address >= 16384 && address <= 23296) {
//      System.out.println(changes.size());
//      changes.clear();
//    }

//    int read = memory.read(address, false);
//    WriteAction action = new WriteAction(address, read, value);
//    addChange(action);
    memory.write2(address, value);
  }

  public static void addChange(WriteAction action) {
    if (capturing && action != null && stateSaved) {
//      memory.compareMemoryStates(memoryState);

      if (action.oldValue != action.newValue)
        changes.add(action);
    }
  }

  public void setGraph(GraphFrame graph) {
    memory.setGraph(graph);
  }

  public static void wayback() {
    ArrayList<WriteAction> changes2 = new ArrayList<>(changes);
    Collections.reverse(changes2);
//    List<WriteAction> changes3 = changes2.subList(0, 2000000);
    changes2.stream().forEach(c -> {
      if (c.register != null) {
        c.register.writeToRealEmulator(c.oldValue);
      } else {
        memory.write2(c.address, c.oldValue);
      }
    });

    // savedRegisterBank.copyTo(Z80.state.registers);

    changes.clear();
  }

  @Override
  public void write2(int address, int value) {
    memory.write2(address, value);
  }

  public static void verifyChanges(State state) {
    OpCode opcode = Z80.opcode;
    List<WriteAction> actions2 = state.registers.compareTo(Z80.state.registers);
    
    if (!actions2.isEmpty())
      System.out.println("diffs!!");

    if (capturing) {
      List<WriteAction> actions = state.registers.compareTo(Z80.lastRegisterBank);
      actions.forEach(c -> addChange(c));
    }
  }

  public static void writeByte(int address, byte currentValue, byte newValue) {
//    if (address > 16384 && address < 16384 + 4096 && capturing && newValue > 0 && newValue != currentValue)
//      System.out.println("writting screen");

    if (capturing)
      addChange(new WriteAction(address, currentValue, newValue));
  }

  public static void toggleCapture() {
    memory.stopEmulation();

    capturing = !capturing;

    try {
      if (!capturing) {
//        Thread.sleep(1000);
        stateSaved = false;

        wayback();

        Object memoryState2 = memory.getState();
//
//        boolean reflectionEquals = EqualsBuilder.reflectionEquals(((Object[]) memoryState)[1], ((Object[]) memoryState2)[1], false);
//        System.out.println(reflectionEquals);
        memory.setSate(memoryState2);
//         memory.setSate(memoryState);

//        Object state = memory.getState();
//        memory.compareMemoryStates(memoryState);
//        memory.compareMemoryStates(memoryState);
      } else {
//        memoryState = memory.getState();

//        Thread.sleep(1000);
//        saveRegisterState();
//        memoryState = memory.getState();
//        memory.compareMemoryStates(memoryState);
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    memory.startEmulation();
  }

  @Override
  public Object getState() {
    return memory.getState();
  }

  @Override
  public void setSate(Object memoryState) {
    memory.setSate(memoryState);
  }

  @Override
  public void stopEmulation() {
    // TODO Auto-generated method stub

  }

  @Override
  public void startEmulation() {
    // TODO Auto-generated method stub

  }

  @Override
  public void compareMemoryStates(Object memoryState) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setCustomState() {
    // TODO Auto-generated method stub

  }

  public static void startChanges() {
    if (capturing)
      stateSaved = true;
  }
}
