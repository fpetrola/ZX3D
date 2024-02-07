package com.fpetrola.z80.blocks.references;

import com.fpetrola.z80.blocks.Block;
import com.fpetrola.z80.blocks.BlocksManager;
import com.fpetrola.z80.blocks.CodeBlock;
import com.fpetrola.z80.blocks.NullBlockChangesListener;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class BlockTest {

  private BlocksManager blocksManager;
  private CodeBlock block1;
  private CodeBlock block2;

  @Before
  public void setUp() {
    blocksManager = new BlocksManager(new NullBlockChangesListener());
    Block blockAt = blocksManager.findBlockAt(0);
    blocksManager.removeBlock(blockAt);
    block1 = new CodeBlock(0, 10, "CALL", blocksManager);
    block2 = new CodeBlock(11, 20, "JUMP", blocksManager);
    blocksManager.addBlock(block1);
    blocksManager.addBlock(block2);
  }

  @Test
  public void testSplitBlock() {
    // Add a reference from block1 to block2
    ReferencesHandler referencesHandler = block1.getReferencesHandler();
    referencesHandler.addBlockRelation(BlockRelation.createBlockRelation(2, 15));
    referencesHandler.addBlockRelation(BlockRelation.createBlockRelation(5, 15));
    referencesHandler.addBlockRelation(BlockRelation.createBlockRelation(7, 15));

    // Split block1 at address 8
    Block newBlock = block1.split(3 - 1, "CALL", CodeBlock.class);

    // Check if the split is successful
    assertTrue(block1.contains(0));
    assertTrue(block1.contains(2));
    assertFalse(block1.contains(3));

    assertFalse(newBlock.contains(2));
    assertTrue(newBlock.contains(3));
    assertTrue(newBlock.contains(10));
    assertFalse(newBlock.contains(11));

    // Check references after the split
    Collection<BlockRelation> referencesInBlock1 = block1.getReferencesHandler().getRelations();
    Collection<BlockRelation> referencesInNewBlock = newBlock.getReferencesHandler().getRelations();

    assertEquals(1, referencesInBlock1.size());
    assertEquals(2, referencesInNewBlock.size());
    BlockRelation blockRelation = referencesInNewBlock.iterator().next();
    assertEquals(newBlock, blocksManager.findBlockAt(blockRelation.getSourceAddress()));
  }

  @Test
  public void testJoinBlocks() {
    ReferencesHandler referencesHandler = block1.getReferencesHandler();
    referencesHandler.addBlockRelation(BlockRelation.createBlockRelation(2, 13));
    referencesHandler.addBlockRelation(BlockRelation.createBlockRelation(5, 17));

    Block newBlock = block2.split(14, "JUMP", CodeBlock.class);

    Collection<BlockRelation> referencesInNewBlock = newBlock.getReferencesHandler().getRelations();

    assertEquals(1, referencesInNewBlock.size());
    BlockRelation blockRelation1 = referencesInNewBlock.iterator().next();
    assertEquals(block1, blocksManager.findBlockAt(blockRelation1.getSourceAddress()));
    BlockRelation blockRelation = referencesInNewBlock.iterator().next();
    assertEquals(newBlock, blocksManager.findBlockAt(blockRelation.getTargetAddress()));

    block1.join(block2);

    assertTrue(block1.contains(0));
    assertTrue(block1.contains(14));
    assertFalse(block1.contains(15));

    assertFalse(newBlock.contains(14));
    assertTrue(newBlock.contains(15));
    assertTrue(newBlock.contains(20));
    assertFalse(newBlock.contains(21));

    Collection<BlockRelation> referencesInBlock1 = block1.getReferencesHandler().getRelations();
    referencesInNewBlock = newBlock.getReferencesHandler().getRelations();
    Collection<BlockRelation> referencesInBlock2 = block2.getReferencesHandler().getRelations();

    assertEquals(2, referencesInBlock1.size());
    assertEquals(0, referencesInBlock2.size());
    assertEquals(1, referencesInNewBlock.size());
  }

  @Test
  public void testReplaceBlockInReferences() {
    // Add a reference from block1 to block2
    CodeBlock block11 = block1;
    int address2 = 5;
    CodeBlock block21 = block2;
    int address11 = 15;
    BlockRelation reference = BlockRelation.createBlockRelation(address2, address11);
    block1.getReferencesHandler().addBlockRelation(reference);

    // Create a new block to replace block2
    CodeBlock newBlock = new CodeBlock(21, 30, "JUMP", blocksManager);

    // Replace block2 with newBlock in references
    Collection<BlockRelation> referencesInBlock1 = block1.getReferencesHandler().getRelations();
    Collection<BlockRelation> newReferences = referencesInBlock1;
//    blocksManager.removeBlock(block2);
    blocksManager.addBlock(newBlock);

    // Check if the references are updated
    assertEquals(1, referencesInBlock1.size());
    assertEquals(1, newReferences.size());
    BlockRelation blockRelation = newReferences.iterator().next();
    assertEquals(block2, blocksManager.findBlockAt(blockRelation.getTargetAddress()));
  }
}
