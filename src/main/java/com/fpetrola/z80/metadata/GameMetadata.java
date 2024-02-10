package com.fpetrola.z80.metadata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fpetrola.z80.analysis.sprites.AddressRange;

import java.util.ArrayList;
import java.util.List;

public class GameMetadata {
  public int mainLoopAddress;
  private List<DataStructure> dataStructures = new ArrayList<>();

  public void addDataStructure(DataStructure dataStructure) {
    dataStructures.add(dataStructure);
  }
}
