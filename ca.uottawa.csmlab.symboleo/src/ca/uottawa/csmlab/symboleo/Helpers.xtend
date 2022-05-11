package ca.uottawa.csmlab.symboleo

import ca.uottawa.csmlab.symboleo.symboleo.DomainType
import ca.uottawa.csmlab.symboleo.symboleo.RegularType
import ca.uottawa.csmlab.symboleo.symboleo.Attribute
import java.util.List
import java.util.ArrayList
import ca.uottawa.csmlab.symboleo.symboleo.Ref
import ca.uottawa.csmlab.symboleo.symboleo.Variable
import ca.uottawa.csmlab.symboleo.symboleo.VariableRef
import ca.uottawa.csmlab.symboleo.symboleo.VariableDotExpression
import org.eclipse.emf.ecore.EObject
import ca.uottawa.csmlab.symboleo.symboleo.Parameter
import ca.uottawa.csmlab.symboleo.symboleo.Expression
import ca.uottawa.csmlab.symboleo.symboleo.Or
import ca.uottawa.csmlab.symboleo.symboleo.And
import ca.uottawa.csmlab.symboleo.symboleo.Equality
import ca.uottawa.csmlab.symboleo.symboleo.Comparison
import ca.uottawa.csmlab.symboleo.symboleo.Plus
import ca.uottawa.csmlab.symboleo.symboleo.Minus
import ca.uottawa.csmlab.symboleo.symboleo.Multi
import ca.uottawa.csmlab.symboleo.symboleo.Div
import ca.uottawa.csmlab.symboleo.symboleo.PrimaryExpressionRecursive
import ca.uottawa.csmlab.symboleo.symboleo.PrimaryExpressionFunctionCall
import ca.uottawa.csmlab.symboleo.symboleo.OneArgMathFunction
import ca.uottawa.csmlab.symboleo.symboleo.TwoArgMathFunction
import ca.uottawa.csmlab.symboleo.symboleo.TwoArgStringFunction
import ca.uottawa.csmlab.symboleo.symboleo.OneArgStringFunction
import ca.uottawa.csmlab.symboleo.symboleo.ThreeArgStringFunction
import ca.uottawa.csmlab.symboleo.symboleo.ThreeArgDateFunction
import ca.uottawa.csmlab.symboleo.symboleo.NegatedPrimaryExpression
import ca.uottawa.csmlab.symboleo.symboleo.AtomicExpressionTrue
import ca.uottawa.csmlab.symboleo.symboleo.AtomicExpressionFalse
import ca.uottawa.csmlab.symboleo.symboleo.AtomicExpressionInt
import ca.uottawa.csmlab.symboleo.symboleo.AtomicExpressionDouble
import ca.uottawa.csmlab.symboleo.symboleo.AtomicExpressionEnum
import ca.uottawa.csmlab.symboleo.symboleo.AtomicExpressionString
import ca.uottawa.csmlab.symboleo.symboleo.AtomicExpressionParameter
import ca.uottawa.csmlab.symboleo.symboleo.ParameterType
import ca.uottawa.csmlab.symboleo.symboleo.BaseType
import ca.uottawa.csmlab.symboleo.symboleo.Alias
import ca.uottawa.csmlab.symboleo.symboleo.Enumeration
import org.eclipse.emf.ecore.EReference
import ca.uottawa.csmlab.symboleo.symboleo.SymboleoPackage
import ca.uottawa.csmlab.symboleo.scoping.MyAttributeImpl
import ca.uottawa.csmlab.symboleo.scoping.MyBaseTypeImpl
import ca.uottawa.csmlab.symboleo.symboleo.impl.RegularTypeImpl
import ca.uottawa.csmlab.symboleo.symboleo.AtomicExpressionDate

class Helpers {

  def static List<Attribute> getAttributesOfRegularType(RegularType argType) {
    val attributes = new ArrayList<Attribute>;
    var type = argType;
    attributes.addAll(type.getAttributes());
    while(type.getRegularType() !== null) {
      type = type.getRegularType();
      attributes.addAll(type.getAttributes());
    }
    if(type.ontologyType.name.equalsIgnoreCase("Event")) {
      val rti = type as RegularTypeImpl;
      val tsAttribute = new MyAttributeImpl("_timestamp",
        new MyBaseTypeImpl("Date"), rti);
      attributes.add(tsAttribute);
    }
    return attributes;
  }

  def static RegularType getBaseType(DomainType domainType) {
    switch (domainType) {
      RegularType:
        if(domainType.ontologyType !== null) {
          return domainType
        } else {
          return getBaseType(domainType.regularType)
        }
      default:
        null
    }
  }

  def static handleExpressionError(ResolveExpressionResult res) {
    if(res.error !== null) {
      return res
    }
  }

  def static ResolveExpressionResult resolveExpressionType(Expression exp,
    List<Variable> variables, List<Parameter> parameters) {
    switch (exp) {
      Or: {
        // left side type
        val l = ca.uottawa.csmlab.symboleo.Helpers.
          resolveExpressionType(exp.left, variables, parameters)
        if(l.error !== null) {
          return l
        }
        // right side type
        val r = ca.uottawa.csmlab.symboleo.Helpers.
          resolveExpressionType(exp.right, variables, parameters)
        if(r.error !== null) {
          return r
        }
        // if one is not boolean then return error
        if(!l.type.equals("Boolean")) {
          return new ResolveExpressionResult(exp, l.type + " is not Boolean.",
            SymboleoPackage.Literals.OR__LEFT);
        }
        if(!r.type.equals("Boolean")) {
          return new ResolveExpressionResult(exp, r.type + " is not Boolean.",
            SymboleoPackage.Literals.OR__RIGHT);
        }
        // return the result type
        return new ResolveExpressionResult("Boolean")
      }
      And: {
        // leftside type
        val l = ca.uottawa.csmlab.symboleo.Helpers.
          resolveExpressionType(exp.left, variables, parameters)
        if(l.error !== null) {
          return l
        }
        // rightside type
        val r = ca.uottawa.csmlab.symboleo.Helpers.
          resolveExpressionType(exp.right, variables, parameters)
        if(r.error !== null) {
          return r
        }
        // if one is not boolean then return error
        if(!l.type.equals("Boolean")) {
          return new ResolveExpressionResult(exp, l.type + " is not Boolean.",
            SymboleoPackage.Literals.AND__LEFT);
        }
        if(!r.type.equals("Boolean")) {
          return new ResolveExpressionResult(exp, r.type + " is not Boolean.",
            SymboleoPackage.Literals.AND__RIGHT);
        }
        // return the result type
        return new ResolveExpressionResult("Boolean")
      }
      // epxs containing "==" | "!="
      Equality: {
        val l = ca.uottawa.csmlab.symboleo.Helpers.
          resolveExpressionType(exp.left, variables, parameters)
        if(l.error !== null) {
          return l
        }
        val r = ca.uottawa.csmlab.symboleo.Helpers.
          resolveExpressionType(exp.right, variables, parameters)
        if(r.error !== null) {
          return r
        }
        // if types does not match return error
        if(!l.type.equals(r.type)) {
          return new ResolveExpressionResult(exp,
            l.type + " does not match " + r.type,
            SymboleoPackage.Literals.EQUALITY__LEFT);
        }
        // return the result type
        return new ResolveExpressionResult("Boolean")
      }
      Comparison: {
        val l = ca.uottawa.csmlab.symboleo.Helpers.
          resolveExpressionType(exp.left, variables, parameters)
        if(l.error !== null) {
          return l
        }
        val r = ca.uottawa.csmlab.symboleo.Helpers.
          resolveExpressionType(exp.right, variables, parameters)
        if(r.error !== null) {
          return r
        }
        // if types does not match return error
        if(!l.type.equals(r.type)) {
          return new ResolveExpressionResult(exp,
            l.type + " does not match " + r.type,
            SymboleoPackage.Literals.COMPARISON__LEFT);
        }
        // return the result type
        return new ResolveExpressionResult("Boolean")
      }
      Plus: {
        val l = ca.uottawa.csmlab.symboleo.Helpers.
          resolveExpressionType(exp.left, variables, parameters)
        if(l.error !== null) {
          return l
        }
        val r = ca.uottawa.csmlab.symboleo.Helpers.
          resolveExpressionType(exp.right, variables, parameters)
        if(r.error !== null) {
          return r
        }
        // if one is not number then return error
        if(!l.type.equals("Number")) {
          return new ResolveExpressionResult(exp, l.type + " is not Number.",
            SymboleoPackage.Literals.PLUS__LEFT);
        }
        if(!r.type.equals("Number")) {
          return new ResolveExpressionResult(exp, r.type + " is not Number.",
            SymboleoPackage.Literals.PLUS__RIGHT);
        }
        // return the result type
        return new ResolveExpressionResult("Number")
      }
      Minus: {
        val l = ca.uottawa.csmlab.symboleo.Helpers.
          resolveExpressionType(exp.left, variables, parameters)
        if(l.error !== null) {
          return l
        }
        val r = ca.uottawa.csmlab.symboleo.Helpers.
          resolveExpressionType(exp.right, variables, parameters)
        if(r.error !== null) {
          return r
        }
        // if one is not number then return error
        if(!l.type.equals("Number")) {
          return new ResolveExpressionResult(exp, l.type + " is not Number.",
            SymboleoPackage.Literals.MINUS__LEFT);
        }
        if(!r.type.equals("Number")) {
          return new ResolveExpressionResult(exp, r.type + " is not Number.",
            SymboleoPackage.Literals.MINUS__RIGHT);
        }
        // return the result type
        return new ResolveExpressionResult("Number")
      }
      Multi: {
        val l = ca.uottawa.csmlab.symboleo.Helpers.
          resolveExpressionType(exp.left, variables, parameters)
        if(l.error !== null) {
          return l
        }
        val r = ca.uottawa.csmlab.symboleo.Helpers.
          resolveExpressionType(exp.right, variables, parameters)
        if(r.error !== null) {
          return r
        }
        // if one is not number then return error
        if(!l.type.equals("Number")) {
          return new ResolveExpressionResult(exp, l.type + " is not Number.",
            SymboleoPackage.Literals.MULTI__LEFT);
        }
        if(!r.type.equals("Number")) {
          return new ResolveExpressionResult(exp, r.type + " is not Number.",
            SymboleoPackage.Literals.MULTI__RIGHT);
        }
        // return the result type
        return new ResolveExpressionResult("Number")
      }
      Div: {
        val l = ca.uottawa.csmlab.symboleo.Helpers.
          resolveExpressionType(exp.left, variables, parameters)
        if(l.error !== null) {
          return l
        }
        val r = ca.uottawa.csmlab.symboleo.Helpers.
          resolveExpressionType(exp.right, variables, parameters)
        if(r.error !== null) {
          return r
        }
        // if one is not number then return error
        if(!l.type.equals("Number")) {
          return new ResolveExpressionResult(exp, l.type + " is not Number.",
            SymboleoPackage.Literals.DIV__LEFT);
        }
        if(!r.type.equals("Number")) {
          return new ResolveExpressionResult(exp, r.type + " is not Number.",
            SymboleoPackage.Literals.DIV__RIGHT);
        }
        // return the result type
        return new ResolveExpressionResult("Number");
      }
      PrimaryExpressionRecursive: {
        return ca.uottawa.csmlab.symboleo.Helpers.resolveExpressionType(exp.inner,
          variables, parameters)
      }
      PrimaryExpressionFunctionCall: {
        switch (exp.function) {
          TwoArgMathFunction,
          OneArgMathFunction: return new ResolveExpressionResult("Number")
          ThreeArgStringFunction,
          TwoArgStringFunction,
          OneArgStringFunction: return new ResolveExpressionResult("String")
          ThreeArgDateFunction: return new ResolveExpressionResult("Date")
        }
      }
      NegatedPrimaryExpression: {
        val t = ca.uottawa.csmlab.symboleo.Helpers.
          resolveExpressionType(exp.expression, variables, parameters)
        if(t.error !== null) {
          return t
        }
        // if one is not boolean then return error
        if(!t.type.equals("Boolean")) {
          return new ResolveExpressionResult(exp, t.type + " is not Boolean.",
            SymboleoPackage.Literals.NEGATED_PRIMARY_EXPRESSION__EXPRESSION);
        }
        // return the result type
        return new ResolveExpressionResult("Boolean")
      }
      // booelan literals
      AtomicExpressionTrue,
      AtomicExpressionFalse:
        return new ResolveExpressionResult("Boolean")
      // number literals
      AtomicExpressionDouble,
      AtomicExpressionInt:
        return new ResolveExpressionResult("Number")
      AtomicExpressionDate:
        return new ResolveExpressionResult("Date")
      // enums
      AtomicExpressionEnum:
        return new ResolveExpressionResult(exp.enumeration.name)
      // string literals
      AtomicExpressionString:
        return new ResolveExpressionResult("String")
      // variable references
      AtomicExpressionParameter:
        return getVariableExpressionType(exp.value, variables, parameters)
    }
  }

  def static ResolveExpressionResult handleDomainType(DomainType t) {
    switch (t) {
      Alias: return new ResolveExpressionResult(t.name)
      RegularType: return new ResolveExpressionResult(t.name)
      Enumeration: return new ResolveExpressionResult(t.name)
    }
  }

  def static ResolveExpressionResult getVariableExpressionType(Ref argRef,
    List<Variable> variables, List<Parameter> parameters) {
    val t = getDotExpressionType(argRef, variables, parameters);
    switch (t) {
      ParameterType: {
        if(t.domainType !== null) {
          return handleDomainType(t.domainType);
        } else if(t.baseType !== null) {
          return new ResolveExpressionResult(t.baseType.name)
        }
      }
      DomainType:
        handleDomainType(t)
      BaseType:
        return new ResolveExpressionResult(t.name)
    }
  }

  def static EObject getDotExpressionType(Ref argRef, List<Variable> variables,
    List<Parameter> parameters) {
    if(argRef instanceof VariableRef) {
      val selectedVar = variables.findFirst [ Variable v |
        v.name.equals(argRef.variable)
      ]
      val selectedParam = parameters.findFirst [ Parameter p |
        p.name.equals(argRef.variable)
      ]
      if(selectedVar !== null) {
        return selectedVar.type
      } else if(selectedParam !== null) {
        return selectedParam.type
      }
      return null
    }
    if(argRef instanceof VariableDotExpression) {
      if(argRef.tail.baseType !== null) {
        return argRef.tail.baseType
      } else if(argRef.tail.domainType !== null) {
        return argRef.tail.domainType
      }
    }
    return null;
  }

  def static Boolean isDotExpressionTypeOfEvent(Ref argRef,
    List<Variable> variables, List<Parameter> parameters) {
    val t = getDotExpressionType(argRef, variables, parameters);
    switch (t) {
      ParameterType: {
        if(t.domainType !== null) {
          return getBaseType(t.domainType).getOntologyType().name.equalsIgnoreCase("Event")
        } else {
          return false
        }
      }
      DomainType:
        return getBaseType(t).getOntologyType().name.equalsIgnoreCase("Event")
      BaseType:
        return false
      default:
        return false
    }
  }

  def static String getDotExpressionStringType(Ref argRef,
    List<Variable> variables, List<Parameter> parameters) {
    val t = getDotExpressionType(argRef, variables, parameters);
    switch (t) {
      ParameterType: {
        if(t.domainType !== null) {
          return t.domainType.name;
        } else if(t.baseType !== null) {
          return t.baseType.name
        }
      }
      DomainType:
        t.name
      BaseType:
        return t.name
    }
  }
}

class ResolveExpressionResult {
  new(String argType, Expression argError, String argMessage, EReference argRef) {
    this.type = argType;
    this.message = argMessage;
    this.error = argError;
    this.ref = argRef;
  }

  new(String argType) {
    this(argType, null, null, null)

  }

  new(Expression argError, String argMessage, EReference argRef) {
    this(null, argError, argMessage, argRef)

  }

  public String type;
  public String message;
  public Expression error;
  public EReference ref;
}
