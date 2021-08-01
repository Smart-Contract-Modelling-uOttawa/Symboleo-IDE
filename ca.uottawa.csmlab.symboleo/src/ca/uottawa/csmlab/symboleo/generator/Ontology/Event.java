package ca.uottawa.csmlab.symboleo.generator.Ontology;

import java.util.ArrayList;
import java.util.List;

import ca.uottawa.csmlab.symboleo.symboleo.Attribute;

public class Event {
  
  public Contract getContract() {
    return contract;
  }

  public void setContract(Contract contract) {
    this.contract = contract;
  }

  private Contract contract;
  private String name;
  private List<Attribute> attributes;
  
  public Event(Contract aContract, String name, List<Attribute> attributes) {
    this.name = name;
    this.contract = contract;
    this.attributes = attributes;
  }
  
}
