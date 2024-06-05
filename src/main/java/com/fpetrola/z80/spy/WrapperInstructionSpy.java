package com.fpetrola.z80.spy;

import com.fpetrola.z80.mmu.Memory;
import com.fpetrola.z80.opcodes.references.ImmutableOpcodeReference;
import com.fpetrola.z80.opcodes.references.MemoryPlusRegister8BitReference;
import com.fpetrola.z80.opcodes.references.WordNumber;
import com.fpetrola.z80.registers.Register;
import com.fpetrola.z80.registers.RegisterName;
import com.fpetrola.z80.registers.RegisterPair;

public abstract class WrapperInstructionSpy<T extends WordNumber> implements InstructionSpy<T> {
  protected volatile boolean capturing;
  protected ExecutionStep executionStep;
  protected MemorySpy memorySpy;
  protected boolean print = false;
  protected Memory memory;
  protected boolean indirectReference;

  public Memory wrapMemory(Memory aMemory) {
    this.memory = aMemory;
    if (memorySpy == null)
      memorySpy = new MemorySpy(aMemory);

    memorySpy.addMemoryWriteListener((address, value) -> {
      if (isCapturing())
        addWriteMemoryReference((T) address, (T) value);
    });

    memorySpy.addMemoryReadListener((address, value) -> {
      if (isCapturing())
        addReadMemoryReference((T) address, (T) value);
    });
    return memorySpy;
  }

  public ImmutableOpcodeReference wrapOpcodeReference(ImmutableOpcodeReference immutableOpcodeReference) {
    return new OpcodeReferenceSpy(immutableOpcodeReference);
  }

  public Register<T> wrapRegister(Register<T> register) {
    Register<T> result = register;
    if (!register.getName().equals(RegisterName.F.name())) {
      if (register instanceof RegisterPair) {
        result = new RegisterPairSpy(register, this);
      } else
        result = new RegisterSpy(register, this);
    }

    if (result instanceof RegisterSpy<T> registerSpy) {
      registerSpy.addRegisterWriteListener(((value, isIncrement) -> {
        if (isCapturing())
          addWriteReference(register.getName(), (T) value, isIncrement);
      }));
      registerSpy.addRegisterReadListener(((value) -> {
        if (isCapturing())
          addReadReference(register.getName(), (T) value);
      }));
    }
    return result;
  }

  public void addWriteReference(String opcodeReference, T value, boolean isIncrement) {
    if (capturing) {
      WriteOpcodeReference writeReference = executionStep.addWriteReference(opcodeReference, value, isIncrement, indirectReference);
      if (print)
        System.out.println(writeReference);
    }
  }

  public void addReadReference(String opcodeReference, T value) {
    if (capturing) {
      ReadOpcodeReference readReference = executionStep.addReadReference(opcodeReference, value, indirectReference);
      if (print)
        System.out.println(readReference);
    }
  }

  public void addWriteMemoryReference(T address, T value) {
    if (capturing) {
      WriteMemoryReference writeMemoryReference = executionStep.addWriteMemoryReference(address, value, indirectReference);
      if (print)
        System.out.println(writeMemoryReference);

    }
  }

  public void addReadMemoryReference(T address, T value) {
    if (capturing) {
      ReadMemoryReference<WordNumber> readMemoryReference = executionStep.addReadMemoryReference(address, value, indirectReference);
      if (print)
        System.out.println(readMemoryReference);

    }
  }

  public MemoryPlusRegister8BitReference wrapMemoryPlusRegister8BitReference(MemoryPlusRegister8BitReference memoryPlusRegister8BitReference) {
    return new MemoryPlusRegister8BitReferenceSpy(memoryPlusRegister8BitReference);
  }
}
