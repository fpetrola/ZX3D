package com.fpetrola.z80.instructions;

import com.fpetrola.z80.instructions.base.InstructionVisitor;
import com.fpetrola.z80.instructions.base.ParameterizedUnaryAluInstruction;
import com.fpetrola.z80.opcodes.references.OpcodeReference;
import com.fpetrola.z80.opcodes.references.WordNumber;
import com.fpetrola.z80.registers.Register;
import com.fpetrola.z80.registers.flag.AluResult;
import com.fpetrola.z80.registers.flag.TableAluOperation;

public class Dec<T extends WordNumber> extends ParameterizedUnaryAluInstruction<T> {
  public static final TableAluOperation dec8TableAluOperation = new TableAluOperation() {
    public AluResult execute(int a, int carry) {
      data = carry;
      int value = a;
      setHalfCarryFlagSub(value, 1);
      setPV(value == 0x80);
      value--;
      setS((value & 0x0080) != 0);
      value = value & 0x00ff;
      setZ(value == 0);
      setN();
      setUnusedFlags(value);

      return new AluResult(value, data);
    }
  };

  public Dec(OpcodeReference target, Register<T> flag) {
    super(target, flag, (tFlagRegister, value) -> dec8TableAluOperation.executeWithCarry(value, tFlagRegister));
  }

  public int execute() {
    final T value2 = target.read();
    T execute = unaryAluOperation.execute(flag, value2);
    target.write(execute);
    return cyclesCost;
  }

  public void accept(InstructionVisitor visitor) {
    super.accept(visitor);
    visitor.visitingDec(this);
  }
}
