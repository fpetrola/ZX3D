package com.fpetrola.z80.registers;

import com.fpetrola.z80.opcodes.references.IntegerWordNumber;
import com.fpetrola.z80.opcodes.references.WordNumber;
import com.fpetrola.z80.registers.flag.FlagProxyFactory;
import com.fpetrola.z80.registers.flag.TableFlagRegister;

import static com.fpetrola.z80.registers.RegisterName.*;

public class DefaultRegisterBankFactory<T extends WordNumber> {

  public DefaultRegisterBankFactory() {
  }

  public <T2 extends WordNumber> RegisterBank<T2> createBank() {
    return initBasicBank(new FlagProxyFactory().createFlagRegisterProxy(new TableFlagRegister(F)));
  }

  public RegisterBank<T> initBasicBank(Register<T> fRegister) {
    RegisterBank registerBank = new RegisterBank();

    registerBank.af = createComposed16BitRegister(AF, create8BitRegister(), fRegister);
    registerBank.bc = createComposed16BitRegister(BC, B, C);
    registerBank.de = createComposed16BitRegister(DE, D, E);
    registerBank.hl = createComposed16BitRegister(HL, H, L);

    registerBank._af = createComposed16BitRegister(AFx, Ax, Fx);
    registerBank._bc = createComposed16BitRegister(BCx, Bx, Cx);
    registerBank._de = createComposed16BitRegister(DEx, Dx, Ex);
    registerBank._hl = createComposed16BitRegister(HLx, Hx, Lx);

    registerBank.ix = createComposed16BitRegister(IX, IXH, IXL);
    registerBank.iy = createComposed16BitRegister(IY, IYH, IYL);
    registerBank.ir = createComposed16BitRegister(IR, createAlwaysIntegerPlain8BitRegister(I), createRRegister());

    registerBank.pc = createAlwaysIntegerPlain16BitRegister(PC);
    registerBank.sp = createAlwaysIntegerPlain16BitRegister(SP);

    registerBank.memptr = createPlain16BitRegister(MEMPTR);
    registerBank.virtual = createPlain16BitRegister(VIRTUAL);

    return registerBank;
  }

  protected Register<T> createRRegister() {
    return new RRegister<T>();
  }

  protected Register<T> createAlwaysIntegerPlain8BitRegister(RegisterName registerName) {
    return new AlwaysIntegerPlain8BitRegister<T>(registerName);
  }

  protected Register<T> create8BitRegister() {
    return new Plain8BitRegister(A);
  }

  protected RegisterPair<T> createComposed16BitRegister(RegisterName registerName, Register<T> h, Register<T> l) {
    return new Composed16BitRegister<T>(registerName, h, l);
  }

  protected Register createAlwaysIntegerPlain16BitRegister(RegisterName registerName) {
    return new AlwaysIntegerPlain16BitRegister(registerName);
  }

  protected Register<T> createPlain16BitRegister(RegisterName registerName) {
    return new Plain16BitRegister<T>(registerName);
  }

  protected RegisterPair createComposed16BitRegister(RegisterName registerName, RegisterName h, RegisterName l) {
    return new Composed16BitRegister(registerName, h, l);
  }

  public static class AlwaysIntegerPlain8BitRegister<T extends WordNumber> extends Plain8BitRegister<T> {
    public AlwaysIntegerPlain8BitRegister(RegisterName registerName) {
      super(registerName);
    }

    public void write(T value) {
      this.data = (T) new IntegerWordNumber(value.intValue());
    }
  }

  public static class AlwaysIntegerPlain16BitRegister<T extends WordNumber> extends Plain16BitRegister<T> {
    public AlwaysIntegerPlain16BitRegister(RegisterName registerName) {
      super(registerName);
    }

    public void write(T value) {
      this.data = (T) new IntegerWordNumber(value.intValue());
    }
  }

  public static class RRegister<T extends WordNumber> extends AlwaysIntegerPlain8BitRegister<T> {
    private boolean regRbit7;

    public RRegister() {
      super(RegisterName.R);
    }

    public void write(T value) {
      int regR = value.intValue() & 0x7f;
      regRbit7 = (value.intValue() > 0x7f);
      super.write((T) new IntegerWordNumber(regR));
    }

    public T read() {
      int regR = super.read().intValue();
      int result = regRbit7 ? (regR & 0x7f) | 0x80 : regR & 0x7f;
      return (T) new IntegerWordNumber(result);
    }
  }
}
