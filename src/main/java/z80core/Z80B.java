
package z80core;

import com.fpetrola.z80.GraphFrame;
import com.fpetrola.z80.OOZ80;
import com.fpetrola.z80.State;
import com.fpetrola.z80.instructions.OpcodesSpy;

import machine.Clock;

public class Z80B extends RegistersBase implements IZ80 {
  private MemIoOps MemIoImpl;
  StateImpl state;
  private OOZ80 z80;
  private Timer timer;
  private final Clock clock;
  private int opCode;

  public Z80B(MemIoOps memory, NotifyOps notify, GraphFrame graph) {
    super();
    this.clock = Clock.getInstance();
    MemIoImpl = memory;
    OpcodesSpy spy = new OpcodesSpy();
    state = new StateImpl(this, spy);
    z80 = new OOZ80(new MemoryImplementation(memory), new IOImplementation(memory), state, graph, spy);
    reset();

    timer = new Timer("Z80");
  }

  private void interruption() {
    clock.addTstates(7);
    z80.interruption();
  }

  public void execute(int statesLimit) {
    while (clock.getTstates() < statesLimit) {

      if (activeNMI) {
        activeNMI = false;
//        lastFlagQ = false;
//        nmi();
        continue;
      }

      if (activeINT) {
        if (isIFF1() && !isPendingEI()) {
//          lastFlagQ = false;
          interruption();
        }
      }

      try {

        z80.execute(1);
        if (pendingEI && opCode != 0xFB) {
          pendingEI = false;
          z80.endInterruption();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void setBreakpoint(int address, boolean state) {
  }

  public void reset() {
    z80.reset();
  }

  public State getState() {
    return state;
  }

  public void update() {
    z80.update();
    state.updateFromEmulator();
  }

  public void enableSpy(boolean b) {
    z80.getSpy().enable(b);
  }
}