package com.fpetrola.z80.instructions;

import com.fpetrola.z80.instructions.base.InstructionVisitor;
import com.fpetrola.z80.instructions.base.ParameterizedUnaryAluInstruction;
import com.fpetrola.z80.opcodes.references.OpcodeReference;
import com.fpetrola.z80.opcodes.references.WordNumber;
import com.fpetrola.z80.registers.Register;
import com.fpetrola.z80.registers.flag.TableAluOperation;

public class RL<T extends WordNumber> extends ParameterizedUnaryAluInstruction<T> {
  public static final TableAluOperation rlTableAluOperation = new TableAluOperation() {
    public int execute(int a, int carry) {
      data = carry;

      // do shift operation
      a = a << 1;
      if (getC())
        a = a | 0x01;
      // standard flag updates
      setS((a & 0x0080) != 0);
      resetH();
      if ((a & 0x0FF00) == 0)
        resetC();
      else
        setC();
      a = a & lsb;
      if ((a & 0x00FF) == 0)
        setZ();
      else
        resetZ();
      setPV(parity[a]);
      resetN();
      // put value back

      return a;
    }
  };

  public RL(OpcodeReference target, Register<T> flag) {
    super(target, flag, (tFlagRegister, temp1) -> rlTableAluOperation.executeWithCarry(temp1, tFlagRegister));
  }

  public void accept(InstructionVisitor visitor) {
    if (!visitor.visitingRl(this))
      super.accept(visitor);
  }
}
