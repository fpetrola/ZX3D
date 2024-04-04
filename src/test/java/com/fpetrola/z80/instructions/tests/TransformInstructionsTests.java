package com.fpetrola.z80.instructions.tests;

import com.fpetrola.z80.instructions.BaseInstructionLoopTest;
import com.fpetrola.z80.instructions.base.Instruction;
import com.fpetrola.z80.opcodes.references.WordNumber;
import org.junit.Before;

public class TransformInstructionsTests<T extends WordNumber> extends BaseInstructionLoopTest<T> {
  protected int memPosition = 1000;
  protected int addedInstructions;

  @Before
  public void setUp() {
    super.setUp();
    useSecond();
  }

  @Override
  public int add(Instruction<T> instruction) {
    addedInstructions++;
    return super.add(instruction);
  }
}
