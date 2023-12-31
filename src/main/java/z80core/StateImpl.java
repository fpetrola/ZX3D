package z80core;

import java.util.BitSet;

import com.fpetrola.z80.State;
import com.fpetrola.z80.registers.Composed16BitRegister;
import com.fpetrola.z80.registers.Plain16BitRegister;
import com.fpetrola.z80.registers.RegisterBank;

import z80core.Z80.IntMode;

public class StateImpl extends State {

  public StateImpl(Z80 z80) {
    super(createBank(z80));
  }

  private static RegisterBank createBank(Z80 z80) {
    Composed16BitRegister af = new Composed16BitRegister("A", "F", (v) -> z80.setRegA(v), () -> z80.getRegA(), (v) -> z80.setFlags(v), () -> (z80.getFlags() & 0xD7));
    Composed16BitRegister bc = new Composed16BitRegister("B", "C", (v) -> z80.setRegB(v), () -> z80.getRegB(), (v) -> z80.setRegC(v), () -> z80.getRegC());
    Composed16BitRegister de = new Composed16BitRegister("D", "E", (v) -> z80.setRegD(v), () -> z80.getRegD(), (v) -> z80.setRegE(v), () -> z80.getRegE());
    Composed16BitRegister hl = new Composed16BitRegister("H", "L", (v) -> z80.setRegH(v), () -> z80.getRegH(), (v) -> z80.setRegL(v), () -> z80.getRegL());
    Composed16BitRegister _af = new Composed16BitRegister("A'", "F'", (v) -> z80.setRegAx(v), () -> z80.getRegAx(), (v) -> z80.setRegFx(v), () -> z80.getRegFx());
    Composed16BitRegister _bc = new Composed16BitRegister("B'", "C'", (v) -> z80.setRegBx(v), () -> z80.getRegBx(), (v) -> z80.setRegCx(v), () -> z80.getRegCx());
    Composed16BitRegister _de = new Composed16BitRegister("D'", "E'", (v) -> z80.setRegDx(v), () -> z80.getRegDx(), (v) -> z80.setRegEx(v), () -> z80.getRegEx());
    Composed16BitRegister _hl = new Composed16BitRegister("H'", "L'", (v) -> z80.setRegHx(v), () -> z80.getRegHx(), (v) -> z80.setRegLx(v), () -> z80.getRegLx());
    Composed16BitRegister ix = new Composed16BitRegister("IXH", "IXL", (v) -> z80.setRegIX(v), () -> z80.getRegIX());
    Composed16BitRegister iy = new Composed16BitRegister("IYH", "IYL", (v) -> z80.setRegIY(v), () -> z80.getRegIY());
    Composed16BitRegister ir = new Composed16BitRegister("I'", "R'", (v) -> z80.setRegI(v), () -> z80.getRegI(), (v) -> z80.setRegR(v), () -> z80.getRegR() & 0x7f);
    
    Plain16BitRegister pc = new Plain16BitRegister("PC") {
      public int readFromRealEmulator() {
        int result = z80.getRegPC();
        write(result);
        return result;
      };

      public void writeToRealEmulator(int value) {
        z80.setRegPC(value);
      };

      public String toString() {
        return "PC";
      }
    };
    Plain16BitRegister sp = new Plain16BitRegister("SP") {
      public int readFromRealEmulator() {
        int result = z80.getRegSP();
        write(result);
        return result;
      };

      public void writeToRealEmulator(int value) {
        z80.setRegSP(value);
      };
      
      
    };

    Plain16BitRegister memptr = new Plain16BitRegister("MEMPTR") {
      public int readFromRealEmulator() {
        int result = z80.getMemPtr();
        write(result);
        return result;
      };

      public void writeToRealEmulator(int value) {
        z80.setMemPtr(value);
      };
    };

    Plain16BitRegister states = new Plain16BitRegister("STATES") {

      public int readFromRealEmulator() {
        BitSet b = new BitSet();
        b.set(0, z80.isHalted());
        b.set(1, z80.isIFF1());
        b.set(2, z80.isIFF2());
//        b.set(3, z80.isINTLine());
        b.set(3, false);
        b.set(4, z80.isNMI());
        b.set(5, z80.isPendingEI());
        b.set(6, z80.getIM() == IntMode.IM0);
        b.set(7, z80.getIM() == IntMode.IM1);
        b.set(8, z80.getIM() == IntMode.IM2);

        long[] longArray = b.toLongArray();
        int result = longArray.length == 0 ? 0 : (int) longArray[0];
        write(result);
        return result;
      };

      public void writeToRealEmulator(int value) {
        BitSet b = BitSet.valueOf(new long[] { value });

        z80.setHalted(b.get(0));
        z80.setIFF1(b.get(1));
        z80.setIFF2(b.get(2));
//        z80.setINTLine(b.get(3));
        z80.setNMI(b.get(4));
        z80.setPendingEI(b.get(5));
        if (b.get(6))
          z80.setIM(IntMode.IM0);
        if (b.get(7))
          z80.setIM(IntMode.IM1);
        if (b.get(8))
          z80.setIM(IntMode.IM2);
      };
    };

    return new RegisterBank(af, bc, de, hl, _af, _bc, _de, _hl, pc, sp, ix, iy, ir, memptr, states);
  }
}
