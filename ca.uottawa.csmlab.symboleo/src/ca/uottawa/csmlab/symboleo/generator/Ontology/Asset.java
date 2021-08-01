package ca.uottawa.csmlab.symboleo.generator.Ontology;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import ca.uottawa.csmlab.symboleo.symboleo.Attribute;

public class Asset {

//------------------------
// MEMBER VARIABLES
//------------------------

//Asset Associations
  private List<Party> parties;
  private List<LegalPosition> legalPositions;
  private Contract contract;
  private String name;
  private List<Attribute> attributes;

  
//------------------------
// CONSTRUCTOR
//------------------------

  public Asset(Contract aContract, String name, List<Attribute> attributes) {
    parties = new ArrayList<Party>();
    legalPositions = new ArrayList<LegalPosition>();
    this.name = name;
    this.attributes = attributes;
    boolean didAddContract = setContract(aContract);
    if (!didAddContract) {
      throw new RuntimeException(
          "Unable to create asset due to contract. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

//------------------------
// INTERFACE
//------------------------
  /* Code from template association_GetMany */
  public Party getParty(int index) {
    Party aParty = parties.get(index);
    return aParty;
  }

  public List<Party> getParties() {
    List<Party> newParties = Collections.unmodifiableList(parties);
    return newParties;
  }

  public int numberOfParties() {
    int number = parties.size();
    return number;
  }

  public boolean hasParties() {
    boolean has = parties.size() > 0;
    return has;
  }

  public int indexOfParty(Party aParty) {
    int index = parties.indexOf(aParty);
    return index;
  }

  /* Code from template association_GetMany */
  public LegalPosition getLegalPosition(int index) {
    LegalPosition aLegalPosition = legalPositions.get(index);
    return aLegalPosition;
  }

  public List<LegalPosition> getLegalPositions() {
    List<LegalPosition> newLegalPositions = Collections.unmodifiableList(legalPositions);
    return newLegalPositions;
  }

  public int numberOfLegalPositions() {
    int number = legalPositions.size();
    return number;
  }

  public boolean hasLegalPositions() {
    boolean has = legalPositions.size() > 0;
    return has;
  }

  public int indexOfLegalPosition(LegalPosition aLegalPosition) {
    int index = legalPositions.indexOf(aLegalPosition);
    return index;
  }

  /* Code from template association_GetOne */
  public Contract getContract() {
    return contract;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfParties() {
    return 0;
  }

  /* Code from template association_AddManyToManyMethod */
  public boolean addParty(Party aParty) {
    boolean wasAdded = false;
    if (parties.contains(aParty)) {
      return false;
    }
    parties.add(aParty);
    if (aParty.indexOfAsset(this) != -1) {
      wasAdded = true;
    } else {
      wasAdded = aParty.addAsset(this);
      if (!wasAdded) {
        parties.remove(aParty);
      }
    }
    return wasAdded;
  }

  /* Code from template association_RemoveMany */
  public boolean removeParty(Party aParty) {
    boolean wasRemoved = false;
    if (!parties.contains(aParty)) {
      return wasRemoved;
    }

    int oldIndex = parties.indexOf(aParty);
    parties.remove(oldIndex);
    if (aParty.indexOfAsset(this) == -1) {
      wasRemoved = true;
    } else {
      wasRemoved = aParty.removeAsset(this);
      if (!wasRemoved) {
        parties.add(oldIndex, aParty);
      }
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addPartyAt(Party aParty, int index) {
    boolean wasAdded = false;
    if (addParty(aParty)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfParties()) {
        index = numberOfParties() - 1;
      }
      parties.remove(aParty);
      parties.add(index, aParty);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePartyAt(Party aParty, int index) {
    boolean wasAdded = false;
    if (parties.contains(aParty)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfParties()) {
        index = numberOfParties() - 1;
      }
      parties.remove(aParty);
      parties.add(index, aParty);
      wasAdded = true;
    } else {
      wasAdded = addPartyAt(aParty, index);
    }
    return wasAdded;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLegalPositions() {
    return 0;
  }

  /* Code from template association_AddManyToOptionalOne */
  public boolean addLegalPosition(LegalPosition aLegalPosition) {
    boolean wasAdded = false;
    if (legalPositions.contains(aLegalPosition)) {
      return false;
    }
    Asset existingAsset = aLegalPosition.getAsset();
    if (existingAsset == null) {
      aLegalPosition.setAsset(this);
    } else if (!this.equals(existingAsset)) {
      existingAsset.removeLegalPosition(aLegalPosition);
      addLegalPosition(aLegalPosition);
    } else {
      legalPositions.add(aLegalPosition);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLegalPosition(LegalPosition aLegalPosition) {
    boolean wasRemoved = false;
    if (legalPositions.contains(aLegalPosition)) {
      legalPositions.remove(aLegalPosition);
      aLegalPosition.setAsset(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addLegalPositionAt(LegalPosition aLegalPosition, int index) {
    boolean wasAdded = false;
    if (addLegalPosition(aLegalPosition)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfLegalPositions()) {
        index = numberOfLegalPositions() - 1;
      }
      legalPositions.remove(aLegalPosition);
      legalPositions.add(index, aLegalPosition);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLegalPositionAt(LegalPosition aLegalPosition, int index) {
    boolean wasAdded = false;
    if (legalPositions.contains(aLegalPosition)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfLegalPositions()) {
        index = numberOfLegalPositions() - 1;
      }
      legalPositions.remove(aLegalPosition);
      legalPositions.add(index, aLegalPosition);
      wasAdded = true;
    } else {
      wasAdded = addLegalPositionAt(aLegalPosition, index);
    }
    return wasAdded;
  }

  /* Code from template association_SetOneToMany */
  public boolean setContract(Contract aContract) {
    boolean wasSet = false;
    if (aContract == null) {
      return wasSet;
    }

    Contract existingContract = contract;
    contract = aContract;
    if (existingContract != null && !existingContract.equals(aContract)) {
      existingContract.removeAsset(this);
    }
    contract.addAsset(this);
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    ArrayList<Party> copyOfParties = new ArrayList<Party>(parties);
    parties.clear();
    for (Party aParty : copyOfParties) {
      aParty.removeAsset(this);
    }
    while (!legalPositions.isEmpty()) {
      legalPositions.get(0).setAsset(null);
    }
    Contract placeholderContract = contract;
    this.contract = null;
    if (placeholderContract != null) {
      placeholderContract.removeAsset(this);
    }
  }

}