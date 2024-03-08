package com.fpetrola.z80.instructions;

import com.fpetrola.z80.instructions.base.Instruction;
import com.fpetrola.z80.instructions.base.TargetInstruction;
import com.fpetrola.z80.instructions.base.TargetSourceInstruction;
import com.fpetrola.z80.instructions.cache.InstructionCloner;
import com.fpetrola.z80.opcodes.references.WordNumber;
import com.fpetrola.z80.registers.*;
import com.fpetrola.z80.registers.flag.Delegate;
import com.fpetrola.z80.registers.flag.FlagProxyFactory;
import com.fpetrola.z80.registers.flag.FlagRegister;
import com.fpetrola.z80.spy.AbstractInstructionSpy;
import com.fpetrola.z80.spy.RegisterPairSpy;

import java.util.HashMap;
import java.util.Map;

import static com.fpetrola.z80.registers.RegisterName.VIRTUAL;

public class RegisterTransformerInstructionSpy<T extends WordNumber> extends AbstractInstructionSpy<T> {
  private final InstructionCloner<T> instructionCloner;
  protected Instruction<T> cloned;
  protected Map<Register, Register> targets = new HashMap<>();
  protected Delegate flagDelegate;

  public RegisterTransformerInstructionSpy(InstructionCloner<T> instructionCloner) {
    this.instructionCloner = instructionCloner;
  }

  public void enable() {
    super.enable();
    flagDelegate.disable();
  }

  public void disable() {
    super.disable();
    flagDelegate.enable();
  }

  public Register<T> wrapRegister(Register<T> register) {
    if (register instanceof FlagRegister flagRegister) {
      flagDelegate = new FlagProxyFactory().createDummyFlagRegisterProxy(flagRegister);
      return (Register<T>) flagDelegate;
    } else if (register instanceof RegisterPair registerPair) {
      return new Composed16BitRegister<>(VIRTUAL, registerPair.getHigh(), registerPair.getLow()) {
        public Register getHigh() {
          return super.getHigh();
        }

        @Override
        public T read() {
          if (enabled) {
            Register register1 = targets.get(register);
            if (register1 == null) {
              register1 = create16VirtualRegister(cloned, registerPair);
              targets.put(register, register1);
            }

            if (cloned instanceof TargetSourceInstruction targetSourceInstruction)
              targetSourceInstruction.setSource(register1);
          }
          return super.read();
        }
      };
    } else {
      return new Plain8BitRegister<T>(VIRTUAL) {
        public T read() {
          if (enabled) {
            Register source = targets.get(register);

            cloned.accept(new TransformerVisitor());
            if (cloned instanceof TargetSourceInstruction targetSourceInstruction)
              targetSourceInstruction.setSource(source);
          }
          return register.read();
        }

        public void write(T value) {
          if (enabled) {
            Register virtualRegister = createVirtualRegister(cloned, targets.get(register));
            targets.put(register, virtualRegister);
            ((TargetInstruction) cloned).setTarget(virtualRegister);
          } else
            register.write(value);
        }
      };
    }
  }

  public <T extends WordNumber> Register create16VirtualRegister(Instruction<T> targetInstruction, RegisterPair registerPair) {
    Register sourceH = targets.get(registerPair.getHigh());
    Register sourceL = targets.get(registerPair.getLow());

    Register virtualRegister = new Plain16BitRegister<T>(VIRTUAL) {
      private boolean executing;

      public T read() {
        if (data != null)
          return data;

        if (!executing) {
          executing = true;
          targetInstruction.execute();
          executing = false;
          return (T) data;
        } else
          return (T) registerPair.read();
      }
    };
    return virtualRegister;
  }


  public <T extends WordNumber> Register createVirtualRegister(Instruction<T> targetInstruction, Register register) {
    Register virtualRegister = new Plain8BitRegister<T>(VIRTUAL) {
      private boolean executing;

      public T read() {
        if (data != null)
          return data;

        if (!executing) {
          executing = true;
          targetInstruction.execute();
          executing = false;
          return (T) data;
        } else
          return (T) register.read();
      }
    };
    return virtualRegister;
  }

  public Instruction<T> processInstruction(Instruction<T> instruction) {
    cloned = instructionCloner.clone(instruction);
    return cloned;
  }
}
