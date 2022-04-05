package ca.uottawa.csmlab.symboleo.scoping;

import org.eclipse.emf.ecore.InternalEObject;

import ca.uottawa.csmlab.symboleo.symboleo.BaseType;
import ca.uottawa.csmlab.symboleo.symboleo.impl.AttributeImpl;

public class MyAttributeImpl extends AttributeImpl {
  public MyAttributeImpl(String name, BaseType baseType, InternalEObject container) {
    super();
    setName(name);
    setBaseType(baseType);
    eContainer = container;
  }
}
