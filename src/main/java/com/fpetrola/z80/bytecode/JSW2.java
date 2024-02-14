package com.fpetrola.z80.bytecode;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class JSW2 {
  public int A;
  public int F;
  public int B;
  public int C;
  public int D;
  public int E;
  public int H;
  public int L;
  public int AF;
  public int BC;
  public int DE;
  public int HL;
  public int IX;
  public int PC;
  public int SP;
  public int I;
  public int R;
  public int[] memory;

  public void $90C0() {
    this.IX = 33024;

    while (true) {
      this.A = this.memory[this.IX + 0];
      this.F = this.A - 255;
      if (this.F == 0) {
        return;
      }

      this.A = this.A & 3;
      if (this.F != 0) {
        this.F = this.A - 1;
        if (this.F != 0) {
          this.F = this.A - 2;
          if (this.F != 0) {
            this.memory[this.IX + 0] = 0;
            if (this.F != 0) {
              this.A = this.memory[this.IX + 1];
              this.A = 0;
              if (this.F != 0) {
                this.A = this.A - 2;
                this.F = this.A - 148;
                if (this.F < 0) {
                  this.A = this.A - 2;
                  this.F = this.A - 128;
                  if (this.F == 0) {
                    this.A = this.A ^ this.A;
                  }
                }
              } else {
                this.A = this.A + 2;
                this.F = this.A - 18;
                if (this.F < 0) {
                  this.A = this.A + 2;
                }
              }
            } else {
              this.A = this.memory[this.IX + 1];
              this.A = 0;
              if (this.F == 0) {
                this.A = this.A - 2;
                this.F = this.A - 20;
                if (this.F < 0) {
                  this.A = this.A - 2;
                  this.A = this.A | this.A;
                  if (this.F == 0) {
                    this.A = 128;
                  }
                }
              } else {
                this.A = this.A + 2;
                this.F = this.A - 146;
                if (this.F < 0) {
                  this.A = this.A + 2;
                }
              }
            }

            this.memory[this.IX + 1] = this.A;
            this.A = this.A & 127;
            this.F = this.A - this.memory[this.IX + 7];
            if (this.F == 0) {
              this.A = this.memory[this.IX + 0];
              this.A = this.A ^ 128;
              this.memory[this.IX + 0] = this.A;
            }
          } else {
            label79:
            {
              this.A = this.memory[this.IX + 0];
              this.A = this.A ^ 8;
              this.memory[this.IX + 0] = this.A;
              this.A = this.A & 24;
              if (this.F != 0) {
                this.A = this.memory[this.IX + 0];
                this.A = this.A + 32;
                this.memory[this.IX + 0] = this.A;
              }

              this.A = this.memory[this.IX + 3];
              this.A = this.A + this.memory[this.IX + 4];
              this.memory[this.IX + 3] = this.A;
              this.F = this.A - this.memory[this.IX + 7];
              if (this.F < 0) {
                this.F = this.A - this.memory[this.IX + 6];
                if (this.F != 0 && this.F >= 0) {
                  break label79;
                }

                this.A = this.memory[this.IX + 6];
                this.memory[this.IX + 3] = this.A;
              }

              this.A = this.memory[this.IX + 4];
              this.A = 0;
              this.memory[this.IX + 4] = this.A;
            }
          }
        } else {
          this.memory[this.IX + 0] = 0;
          if (this.F == 0) {
            this.A = this.memory[this.IX + 0];
            this.A = this.A - 32;
            this.A = this.A & 127;
            this.memory[this.IX + 0] = this.A;
            this.F = this.A - 96;
            if (this.F >= 0) {
              this.A = this.memory[this.IX + 2];
              this.A = this.A & 31;
              this.F = this.A - this.memory[this.IX + 6];
              if (this.F == 0) {
                this.memory[this.IX + 0] = 129;
              }
            }
          } else {
            this.A = this.memory[this.IX + 0];
            this.A = this.A + 32;
            this.A = this.A | 128;
            this.memory[this.IX + 0] = this.A;
            this.F = this.A - 160;
            if (this.F < 0) {
              this.A = this.memory[this.IX + 2];
              this.A = this.A & 31;
              this.F = this.A - this.memory[this.IX + 7];
              if (this.F == 0) {
                this.memory[this.IX + 0] = 97;
              }
            }
          }
        }
      }

      this.DE = 8;
      this.IX = this.IX + this.DE;
    }
  }

}
