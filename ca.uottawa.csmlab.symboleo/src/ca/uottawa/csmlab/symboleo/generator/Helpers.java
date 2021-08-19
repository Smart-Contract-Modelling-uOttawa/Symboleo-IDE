package ca.uottawa.csmlab.symboleo.generator;

import java.util.ArrayList;
import java.util.List;

import ca.uottawa.csmlab.symboleo.symboleo.Attribute;
import ca.uottawa.csmlab.symboleo.symboleo.RegularType;

class Helpers {

  public static List<Attribute> getAttributesOfRegularType(RegularType type) {
    List<Attribute> attributes = new ArrayList<>();
    attributes.addAll(type.getAttributes());
    while (type.getRegularType() != null) {
      type = type.getRegularType();
      attributes.addAll(type.getAttributes());
    }
    return attributes;
  }
  
}