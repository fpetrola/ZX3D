package com.fpetrola.z80.graph;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.AttributeType;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import com.fpetrola.z80.OOZ80;
import com.fpetrola.z80.spy.ExecutionStepData;

public class CustomGraph {

  protected Map<String, Map<String, Attribute>> edgeAttributes = new HashMap<>();
  protected Map<String, Map<String, Attribute>> vertexAttributes = new HashMap<>();
  public DefaultDirectedGraph<String, String> g2 = new DefaultDirectedGraph<>(String.class);
  private Map<String, String> vertexes = new HashMap<>();

  public void exportGraph() {
    try {
      DOTExporter<String, String> export = new DOTExporter<>();
      export.setEdgeAttributeProvider(new Function<String, Map<String, Attribute>>() {

        public Map<String, Attribute> apply(String t) {
          return edgeAttributes.get(t);
        }
      });

      export.setVertexAttributeProvider(new Function<String, Map<String, Attribute>>() {

        public Map<String, Attribute> apply(String t) {
          return vertexAttributes.get(t);
        }
      });
      export.exportGraph(g2, new FileWriter("graph.dot"));
    } catch (IOException e) {
    }

  }

  private void addVertex3() {
    String vertextName = OOZ80.convertToHex(0);
    String label = "label1";
    addVertex(vertextName, label);
  }

  public void addVertex(String vertextId, String label) {
    g2.addVertex(vertextId);
    Map<String, Attribute> attributes = new HashMap<>();
    attributes.put("label", new DefaultAttribute<String>(label, AttributeType.STRING));
    vertexAttributes.put(vertextId, attributes);
  }

  private void addEdge3() {
    String edgeName = "";
    String sourceVertex = "source";
    String targetVertex = "target";
    String label = "label2";
    addEdge(edgeName, sourceVertex, targetVertex, label);
  }

  protected String getVertexLabel(Object currentStep) {
    return currentStep.toString();
  }

  protected String getVertexId(Object currentStep) {
    return currentStep.toString();
  }

  private String addOrCreateVertex(Object currentStep) {
    String id = getVertexId(currentStep);
    String a = vertexes.get(id);
    String label = getVertexLabel(currentStep);
    if (a == null) {
      addVertex(id, label);
      vertexes.put(id, label);
    }
    else
    {
      vertexAttributes.get(id).put("label", DefaultAttribute.createAttribute(label));
    }
    return id;
  }

  public void addEdge(String edgeName, Object sourceVertex, Object targetVertex, String label) {
    String sourceVertexId = addOrCreateVertex(sourceVertex);
    String targetVertexId = addOrCreateVertex(targetVertex);

    Map<String, Attribute> attributes = new HashMap<>();
    attributes.put("label", new DefaultAttribute<String>(label, AttributeType.STRING));
    edgeAttributes.put(edgeName, attributes);
    g2.addEdge(sourceVertexId, targetVertexId, edgeName);
  }

}