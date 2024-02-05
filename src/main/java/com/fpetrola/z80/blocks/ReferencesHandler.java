package com.fpetrola.z80.blocks;

import java.util.*;
import java.util.stream.Collectors;

public class ReferencesHandler {
  private final AbstractBlock abstractBlock;
  protected Set<Block> referencedBlocks = new HashSet<Block>();
  protected Set<BlockRelation> references = new HashSet<BlockRelation>();

  public ReferencesHandler(AbstractBlock abstractBlock) {
    this.abstractBlock = abstractBlock;
  }

  
  public void removeBlockReferences(Collection<BlockRelation> newBlockRelations) {
    new ArrayList<BlockRelation>(newBlockRelations).forEach(r -> abstractBlock.removeBlockReference(r));
  }

  
  public void removeBlockReference(BlockRelation blockRelation) {
    references.remove(blockRelation);
    blockRelation.getTargetBlock().getReferences().remove(blockRelation);

    if (blockRelation.getSourceBlock() == abstractBlock)
      abstractBlock.getBlocksManager().blockChangesListener.removingKnownBlock(blockRelation.getSourceBlock(), blockRelation.getTargetBlock());
  }

  
  public Collection<BlockRelation> getReferences() {
    return references;
  }

  
  public void addBlockReferences(Collection<BlockRelation> references1) {
    references1.forEach(r -> abstractBlock.addBlockRelation(r));
  }

  List<BlockRelation> selectSourceBlockReferences(Block block) {
    return references.stream().filter(r -> block.contains(r.getSourceAddress())).collect(Collectors.toList());
  }

  List<BlockRelation> selectTargetBlockReferences(Block block) {
    return references.stream().filter(r -> block.contains(r.getTargetAddress())).collect(Collectors.toList());
  }

  public List<BlockRelation> replaceBlockInReferences(Collection<BlockRelation> references1, Block block, Block replaceBlock) {
    return references1.stream().map(r -> {
      if (r.getSourceBlock() == block) r.setSourceBlock(replaceBlock);
      if (r.getTargetBlock() == block) r.setTargetBlock(replaceBlock);
      return r;
    }).collect(Collectors.toList());
  }

  
  public Set<Block> getReferencedByBlocks() {
    return references.stream().map(r -> r.getSourceBlock()).collect(Collectors.toSet());
  }

  
  public void addBlockRelation(BlockRelation e) {
    if (e.getSourceBlock() == abstractBlock) {
      references.add(e);
      e.getTargetBlock().getReferences().add(e);
      abstractBlock.getBlocksManager().blockChangesListener.addingKnownBLock(abstractBlock, e.getTargetBlock(), e.getSourceAddress());
    } else e.getSourceBlock().addBlockRelation(e);
  }
}