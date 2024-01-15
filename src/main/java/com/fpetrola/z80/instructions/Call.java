package com.fpetrola.z80.instructions;

import com.fpetrola.z80.State;

public class Call extends TargetOpCode {

  public final Condition condition;

  public Call(State state, Condition condition, OpcodeReference target) {
    super(state, target);
    this.condition = condition;
  }

  @Override
  public int execute() {
    if (condition.conditionMet()) {
      sp.decrement(2);
      final int position = target.read();
      final int address = sp.read();
      final int value = pc.read()+2;
      memory.write(address, value & 0xFF);
      memory.write(address + 1, (value >> 8));
      state.setNextPC(position);
//      pc.write(position);
      return 4 + 3 + 4 + target.cyclesCost();
    } else {
      pc.increment(2);

      return getCyclesCost();
    }
  }

  @Override
  public String toString() {
    final String conditionStr = condition.toString();
    return "CALL " + ((conditionStr.length() > 0) ? conditionStr + "," : "") + target;
  }
}
