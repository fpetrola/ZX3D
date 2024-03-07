package com.fpetrola.z80.instructions;

import com.fpetrola.z80.instructions.base.TargetSourceInstruction;
import com.fpetrola.z80.mmu.IO;
import com.fpetrola.z80.opcodes.references.ImmutableOpcodeReference;
import com.fpetrola.z80.opcodes.references.OpcodeReference;
import com.fpetrola.z80.opcodes.references.WordNumber;

public class Out<T extends WordNumber> extends TargetSourceInstruction<T, ImmutableOpcodeReference<T>> {
  Out(ImmutableOpcodeReference source, OutPortOpcodeReference outPortOpcodeReference) {
    super(outPortOpcodeReference, source);
  }

  public int execute() {
    target.write(source.read());
    return cyclesCost;
  }

  static class OutPortOpcodeReference<T> implements OpcodeReference<T> {
    private final IO<T> io;
    private final ImmutableOpcodeReference target;

    public OutPortOpcodeReference(IO<T> io, ImmutableOpcodeReference target) {
      this.io = io;
      this.target = target;
    }

    public void write(T value) {
      io.out((T) target.read(), value);
    }

    public T read() {
      return (T) target.read();
    }

    public int getLength() {
      return target.getLength();
    }

    public Object clone() throws CloneNotSupportedException {
      return target.clone();
    }
  }
}
