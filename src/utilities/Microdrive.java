/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jsanchez
 */
public class Microdrive {
    // Definition of states for the Microdrive Machine States
//    private enum MDR_STATE {
//        GAP, SYNC, HEADER, DATA, CHECKSUM
//    };
    
    private static final int GAP = 0x04;
    private static final int GAP_LEN = 12;
    private static final int SYNC = 0x02;
    private static final int SYNC_LEN = 10;
    private static final int HEADER_LEN = 15;
    private static final int DATA_LEN = 527; // 15 + 512
    private static final int SECTOR_LEN = 543;
    
//    private MDR_STATE mdrState;
    private int position;
    private byte cartridge[];
    private short nBlocks;
    private int status;
    private int nSync;
    private int nGap;
//    private int nBytes;
    private boolean isCartridge;
    private int writeProtected;
    
    private FileInputStream mdrFile;
    private File filename;
    
    public Microdrive() {
        position = 0;
        // A microdrive cartridge can have 254 sectors of 543 bytes lenght
        nBlocks = 254;
//        mdrState = MDR_STATE.GAP;
        nGap = GAP_LEN;
        nSync = SYNC_LEN;
        isCartridge = false;
        status = 0xff;
//        nBytes = HEADER_LEN;
    }
    
    public int readStatus() {
        if (!isCartridge)
            return status;
        
        int offset = position % SECTOR_LEN;
        
        if (offset == 0 || offset == 15) {
            if (--nGap < 0) {
                status &= ~(GAP | SYNC);
                if (--nSync < 0) {
                    status |= GAP | SYNC;
                    nGap = GAP_LEN;
                    nSync = SYNC_LEN;
                }
            }
        }
        
        if (offset == 30) {
//            status |= GAP | SYNC;
//            nGap = GAP_LEN;
//            nSync = SYNC_LEN;
            position += 513; // 543 - 30
            if (position == cartridge.length - 1) {
                position = 0;
            }
        }
        
        if (isWriteProtected())
            status &= 0xfe;
        
        return status;
    }
    
    public int readData() {
        
        if (nGap < 0) {
            status |= GAP | SYNC;
            nGap = GAP_LEN;
            nSync = SYNC_LEN;
        }
        
//        if (mdrState == MDR_STATE.SYNC) {
//            status |= SYNC;
//            if (nBytes == HEADER_LEN) {
//                mdrState = MDR_STATE.HEADER;
//            }
//            
//            if (nBytes == DATA_LEN) {
//                mdrState = MDR_STATE.DATA;
//            }
//        }
        
        int out = cartridge[position++] & 0xff;
        
        if (position == cartridge.length - 1)
            position = 0;
        
//        switch (mdrState) {
//            case HEADER:
//                if (--nBytes == 0) {
//                    mdrState = MDR_STATE.GAP;
//                    status &= ~GAP;
//                    nBytes = DATA_LEN;
//                }
//                position++;
//                break;
//            case DATA:
//                if (--nBytes == 0) {
//                    mdrState = MDR_STATE.CHECKSUM;
//                }
//                position++;
//                break;
//            case CHECKSUM:
//                mdrState = MDR_STATE.GAP;
//                status &= ~GAP;
//                nBytes = HEADER_LEN;
//                position++;
//                if (position == cartridge.length - 1)
//                    position = 0;
//                break;
//            default:
//                System.out.println(String.format("readData: mdrState = %s. position = %d",
//                    mdrState.toString(), position));
//                out = 0xff;
//        }
        
        return out;
    }
    
    public void writeData(int value) {
//        System.out.println(String.format("mdrState = %s, writeData: %d",
//                mdrState.toString(), value & 0xff));
    }
    
    public boolean isWriteProtected() {
        return writeProtected == 0x00 ? true : false;
    }
    
    public boolean isCartridge() {
        return isCartridge;
    }
    
    public final boolean insert(File fileName) {
        
        if (isCartridge)
            return false;
        
        try {
            mdrFile = new FileInputStream(fileName);
        } catch (FileNotFoundException fex) {
            Logger.getLogger(Tape.class.getName()).log(Level.SEVERE, null, fex);
            return false;
        }

        try {
            cartridge = new byte[mdrFile.available()];
            mdrFile.read(cartridge);
            mdrFile.close();
            filename = fileName;
        } catch (IOException ex) {
            Logger.getLogger(Tape.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        isCartridge = true;
        status = 0xff & ~GAP;
        writeProtected = cartridge[cartridge.length - 1] != 0 ? 0x00 : 0x01;
//        testMDR();
        return true;
    }
    
    private void testMDR() {
        int length = (cartridge.length - 1) / 543;
        boolean sectors[] = new boolean[256];
        
        System.out.println(String.format("Cartridge %s", filename.getName()));
        System.out.println(String.format("# sectors %d", length));
        for (int idx = 0; idx < length; idx++ ) {
            int nSector = cartridge[idx * 543 + 1] & 0xff;
            sectors[nSector] = true;
            System.out.println(String.format("Sector %d present", idx));
        }
        
        for (int idx = length; idx > 0; idx-- ) {
            if (sectors[idx] == true) {
                System.out.println(String.format("Max sector: %d", idx));
                break;
            }
        }
        System.out.println("-----------------------------");
    }
}