package ca.uottawa.csmlab.symboleo.generator.Ontology;

import java.util.*;

import ca.uottawa.csmlab.symboleo.symboleo.Attribute;

public class Role {

//------------------------
// MEMBER VARIABLES
//------------------------

//Role Associations
  private List<LegalPosition> debt;
  private List<LegalPosition> credit;
  private Party party;
  private Contract contract;
  private String name;
  private List<Attribute> attributes;

//------------------------
// CONSTRUCTOR
//------------------------

  public Role(Contract aContract, String name, List<Attribute> attributes) {
    this.name = name;
    debt = new ArrayList<LegalPosition>();
    credit = new ArrayList<LegalPosition>();
    this.attributes = attributes;
    boolean didAddContract = setContract(aContract);
    if (!didAddContract) {
      throw new RuntimeException(
          "Unable to create role due to contract. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

//------------------------
// INTERFACE
//------------------------
  /* Code from template association_GetMany */
  public LegalPosition getDebt(int index) {
    LegalPosition aDebt = debt.get(index);
    return aDebt;
  }

  public List<LegalPosition> getDebt() {
    List<LegalPosition> newDebt = Collections.unmodifiableList(debt);
    return newDebt;
  }

  public int numberOfDebt() {
    int number = debt.size();
    return number;
  }

  public boolean hasDebt() {
    boolean has = debt.size() > 0;
    return has;
  }

  public int indexOfDebt(LegalPosition aDebt) {
    int index = debt.indexOf(aDebt);
    return index;
  }

  /* Code from template association_GetMany */
  public LegalPosition getCredit(int index) {
    LegalPosition aCredit = credit.get(index);
    return aCredit;
  }

  public List<LegalPosition> getCredit() {
    List<LegalPosition> newCredit = Collections.unmodifiableList(credit);
    return newCredit;
  }

  public int numberOfCredit() {
    int number = credit.size();
    return number;
  }

  public boolean hasCredit() {
    boolean has = credit.size() > 0;
    return has;
  }

  public int indexOfCredit(LegalPosition aCredit) {
    int index = credit.indexOf(aCredit);
    return index;
  }

  /* Code from template association_GetOne */
  public Party getParty() {
    return party;
  }

  public boolean hasParty() {
    boolean has = party != null;
    return has;
  }

  /* Code from template association_GetOne */
  public Contract getContract() {
    return contract;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDebt() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public LegalPosition addDebt(Contract aContract, Role aCreditor) {
    return new LegalPosition(aContract, this, aCreditor);
  }

  public boolean addDebt(LegalPosition aDebt) {
    boolean wasAdded = false;
    if (debt.contains(aDebt)) {
      return false;
    }
    Role existingDebtor = aDebt.getDebtor();
    boolean isNewDebtor = existingDebtor != null && !this.equals(existingDebtor);
    if (isNewDebtor) {
      aDebt.setDebtor(this);
    } else {
      debt.add(aDebt);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDebt(LegalPosition aDebt) {
    boolean wasRemoved = false;
    // Unable to remove aDebt, as it must always have a debtor
    if (!this.equals(aDebt.getDebtor())) {
      debt.remove(aDebt);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addDebtAt(LegalPosition aDebt, int index) {
    boolean wasAdded = false;
    if (addDebt(aDebt)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfDebt()) {
        index = numberOfDebt() - 1;
      }
      debt.remove(aDebt);
      debt.add(index, aDebt);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDebtAt(LegalPosition aDebt, int index) {
    boolean wasAdded = false;
    if (debt.contains(aDebt)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfDebt()) {
        index = numberOfDebt() - 1;
      }
      debt.remove(aDebt);
      debt.add(index, aDebt);
      wasAdded = true;
    } else {
      wasAdded = addDebtAt(aDebt, index);
    }
    return wasAdded;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCredit() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public LegalPosition addCredit(Contract aContract, Role aDebtor) {
    return new LegalPosition(aContract, aDebtor, this);
  }

  public boolean addCredit(LegalPosition aCredit) {
    boolean wasAdded = false;
    if (credit.contains(aCredit)) {
      return false;
    }
    Role existingCreditor = aCredit.getCreditor();
    boolean isNewCreditor = existingCreditor != null && !this.equals(existingCreditor);
    if (isNewCreditor) {
      aCredit.setCreditor(this);
    } else {
      credit.add(aCredit);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCredit(LegalPosition aCredit) {
    boolean wasRemoved = false;
    // Unable to remove aCredit, as it must always have a creditor
    if (!this.equals(aCredit.getCreditor())) {
      credit.remove(aCredit);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addCreditAt(LegalPosition aCredit, int index) {
    boolean wasAdded = false;
    if (addCredit(aCredit)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfCredit()) {
        index = numberOfCredit() - 1;
      }
      credit.remove(aCredit);
      credit.add(index, aCredit);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCreditAt(LegalPosition aCredit, int index) {
    boolean wasAdded = false;
    if (credit.contains(aCredit)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfCredit()) {
        index = numberOfCredit() - 1;
      }
      credit.remove(aCredit);
      credit.add(index, aCredit);
      wasAdded = true;
    } else {
      wasAdded = addCreditAt(aCredit, index);
    }
    return wasAdded;
  }

  /* Code from template association_SetOptionalOneToMandatoryMany */
  public boolean setParty(Party aParty) {
    //
    // This source of this source generation is
    // association_SetOptionalOneToMandatoryMany.jet
    // This set file assumes the generation of a maximumNumberOfXXX method does not
    // exist because
    // it's not required (No upper bound)
    //
    boolean wasSet = false;
    Party existingParty = party;

    if (existingParty == null) {
      if (aParty != null) {
        if (aParty.addRole(this)) {
          existingParty = aParty;
          wasSet = true;
        }
      }
    } else if (existingParty != null) {
      if (aParty == null) {
        if (existingParty.minimumNumberOfRoles() < existingParty.numberOfRoles()) {
          existingParty.removeRole(this);
          existingParty = aParty; // aParty == null
          wasSet = true;
        }
      } else {
        if (existingParty.minimumNumberOfRoles() < existingParty.numberOfRoles()) {
          existingParty.removeRole(this);
          aParty.addRole(this);
          existingParty = aParty;
          wasSet = true;
        }
      }
    }
    if (wasSet) {
      party = existingParty;
    }
    return wasSet;
  }

  /* Code from template association_SetOneToMandatoryMany */
  public boolean setContract(Contract aContract) {
    boolean wasSet = false;
    // Must provide contract to role
    if (aContract == null) {
      return wasSet;
    }

    if (contract != null && contract.numberOfRoles() <= Contract.minimumNumberOfRoles()) {
      return wasSet;
    }

    Contract existingContract = contract;
    contract = aContract;
    if (existingContract != null && !existingContract.equals(aContract)) {
      boolean didRemove = existingContract.removeRole(this);
      if (!didRemove) {
        contract = existingContract;
        return wasSet;
      }
    }
    contract.addRole(this);
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    for (int i = debt.size(); i > 0; i--) {
      LegalPosition aDebt = debt.get(i - 1);
      aDebt.delete();
    }
    for (int i = credit.size(); i > 0; i--) {
      LegalPosition aCredit = credit.get(i - 1);
      aCredit.delete();
    }
    if (party != null) {
      if (party.numberOfRoles() <= 1) {
        party.delete();
      } else {
        Party placeholderParty = party;
        this.party = null;
        placeholderParty.removeRole(this);
      }
    }
    Contract placeholderContract = contract;
    this.contract = null;
    if (placeholderContract != null) {
      placeholderContract.removeRole(this);
    }
  }

}