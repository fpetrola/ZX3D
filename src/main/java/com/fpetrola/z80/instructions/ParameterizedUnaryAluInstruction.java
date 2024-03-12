package com.fpetrola.z80.instructions;

import com.fpetrola.z80.instructions.base.TargetInstruction;
import com.fpetrola.z80.opcodes.references.OpcodeReference;
import com.fpetrola.z80.opcodes.references.WordNumber;
import com.fpetrola.z80.registers.flag.FlagRegister;

public class ParameterizedUnaryAluInstruction<T extends WordNumber> extends TargetInstruction<T> {
  protected FlagRegister.UnaryAluOperation<T> unaryAluOperation;
  protected FlagRegister<T> flag;

  public ParameterizedUnaryAluInstruction(OpcodeReference<T> target, FlagRegister.UnaryAluOperation<T> unaryAluOperation, FlagRegister<T> flag) {
    super(target);
    this.unaryAluOperation = unaryAluOperation;
    this.flag = flag;
  }

  public int execute() {
    final T value2 = target.read();
    T execute = unaryAluOperation.execute(flag, value2);
    target.write(execute);
    return cyclesCost;
  }

  public FlagRegister<T> getFlag() {
    return flag;
  }

  public void setFlag(FlagRegister<T> flag) {
    this.flag = flag;
  }
}
