package com.fpetrola.z80.instructions;

import com.fpetrola.z80.instructions.base.BlockInstruction;
import com.fpetrola.z80.mmu.IO;
import com.fpetrola.z80.mmu.Memory;
import com.fpetrola.z80.opcodes.references.WordNumber;
import com.fpetrola.z80.registers.Register;
import com.fpetrola.z80.registers.RegisterPair;
import com.fpetrola.z80.registers.flag.AluResult;
import com.fpetrola.z80.registers.flag.TableAluOperation;

public class Ini<T extends WordNumber> extends BlockInstruction<T> {
  public static final TableAluOperation iniTableAluOperation = new TableAluOperation() {
    public AluResult execute(int b, int carry) {
      data = 0;
      setZ(b == 0);
      setN();
      return new AluResult(b, data);
    }
  };

  public Ini(RegisterPair<T> bc, Register<T> hl, Register<T> flag, Memory<T> memory, IO<T> io) {
    super(bc, hl, flag, memory, io);
  }

  public int execute() {
    T hlValue = hl.read();
    T cValue = bc.getLow().read();
    T in = io.in(cValue);
    memory.write(hlValue, in);
    bc.getHigh().decrement();
    next();
    flagOperation();
    return 1;
  }

  protected void flagOperation() {
    iniTableAluOperation.executeWithCarry(bc.getHigh().read(), flag);
  }
}
