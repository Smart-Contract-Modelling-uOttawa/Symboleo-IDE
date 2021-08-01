package ca.uottawa.csmlab.symboleo.generator.Ontology;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LegalPosition {

//------------------------
// MEMBER VARIABLES
//------------------------

//LegalPosition Associations
  private List<Party> performer;
  private List<Party> liable;
  private List<Party> rightHolder;
  private Contract contract;
  private Role debtor;
  private Role creditor;
  private Asset asset;

//------------------------
// CONSTRUCTOR
//------------------------

  public LegalPosition(Contract aContract, Role aDebtor, Role aCreditor) {
    performer = new ArrayList<Party>();
    liable = new ArrayList<Party>();
    rightHolder = new ArrayList<Party>();
    boolean didAddContract = setContract(aContract);
    if (!didAddContract) {
      throw new RuntimeException(
          "Unable to create legalPosition due to contract. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddDebtor = setDebtor(aDebtor);
    if (!didAddDebtor) {
      throw new RuntimeException(
          "Unable to create debt due to debtor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCreditor = setCreditor(aCreditor);
    if (!didAddCreditor) {
      throw new RuntimeException(
          "Unable to create credit due to creditor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

//------------------------
// INTERFACE
//------------------------
  /* Code from template association_GetMany */
  public Party getPerformer(int index) {
    Party aPerformer = performer.get(index);
    return aPerformer;
  }

  public List<Party> getPerformer() {
    List<Party> newPerformer = Collections.unmodifiableList(performer);
    return newPerformer;
  }

  public int numberOfPerformer() {
    int number = performer.size();
    return number;
  }

  public boolean hasPerformer() {
    boolean has = performer.size() > 0;
    return has;
  }

  public int indexOfPerformer(Party aPerformer) {
    int index = performer.indexOf(aPerformer);
    return index;
  }

  /* Code from template association_GetMany */
  public Party getLiable(int index) {
    Party aLiable = liable.get(index);
    return aLiable;
  }

  public List<Party> getLiable() {
    List<Party> newLiable = Collections.unmodifiableList(liable);
    return newLiable;
  }

  public int numberOfLiable() {
    int number = liable.size();
    return number;
  }

  public boolean hasLiable() {
    boolean has = liable.size() > 0;
    return has;
  }

  public int indexOfLiable(Party aLiable) {
    int index = liable.indexOf(aLiable);
    return index;
  }

  /* Code from template association_GetMany */
  public Party getRightHolder(int index) {
    Party aRightHolder = rightHolder.get(index);
    return aRightHolder;
  }

  public List<Party> getRightHolder() {
    List<Party> newRightHolder = Collections.unmodifiableList(rightHolder);
    return newRightHolder;
  }

  public int numberOfRightHolder() {
    int number = rightHolder.size();
    return number;
  }

  public boolean hasRightHolder() {
    boolean has = rightHolder.size() > 0;
    return has;
  }

  public int indexOfRightHolder(Party aRightHolder) {
    int index = rightHolder.indexOf(aRightHolder);
    return index;
  }

  /* Code from template association_GetOne */
  public Contract getContract() {
    return contract;
  }

  /* Code from template association_GetOne */
  public Role getDebtor() {
    return debtor;
  }

  /* Code from template association_GetOne */
  public Role getCreditor() {
    return creditor;
  }

  /* Code from template association_GetOne */
  public Asset getAsset() {
    return asset;
  }

  public boolean hasAsset() {
    boolean has = asset != null;
    return has;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPerformer() {
    return 0;
  }

  /* Code from template association_AddManyToManyMethod */
  public boolean addPerformer(Party aPerformer) {
    boolean wasAdded = false;
    if (performer.contains(aPerformer)) {
      return false;
    }
    performer.add(aPerformer);
    if (aPerformer.indexOfPerformerOf(this) != -1) {
      wasAdded = true;
    } else {
      wasAdded = aPerformer.addPerformerOf(this);
      if (!wasAdded) {
        performer.remove(aPerformer);
      }
    }
    return wasAdded;
  }

  /* Code from template association_RemoveMany */
  public boolean removePerformer(Party aPerformer) {
    boolean wasRemoved = false;
    if (!performer.contains(aPerformer)) {
      return wasRemoved;
    }

    int oldIndex = performer.indexOf(aPerformer);
    performer.remove(oldIndex);
    if (aPerformer.indexOfPerformerOf(this) == -1) {
      wasRemoved = true;
    } else {
      wasRemoved = aPerformer.removePerformerOf(this);
      if (!wasRemoved) {
        performer.add(oldIndex, aPerformer);
      }
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addPerformerAt(Party aPerformer, int index) {
    boolean wasAdded = false;
    if (addPerformer(aPerformer)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfPerformer()) {
        index = numberOfPerformer() - 1;
      }
      performer.remove(aPerformer);
      performer.add(index, aPerformer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePerformerAt(Party aPerformer, int index) {
    boolean wasAdded = false;
    if (performer.contains(aPerformer)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfPerformer()) {
        index = numberOfPerformer() - 1;
      }
      performer.remove(aPerformer);
      performer.add(index, aPerformer);
      wasAdded = true;
    } else {
      wasAdded = addPerformerAt(aPerformer, index);
    }
    return wasAdded;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLiable() {
    return 0;
  }

  /* Code from template association_AddManyToManyMethod */
  public boolean addLiable(Party aLiable) {
    boolean wasAdded = false;
    if (liable.contains(aLiable)) {
      return false;
    }
    liable.add(aLiable);
    if (aLiable.indexOfLiableOf(this) != -1) {
      wasAdded = true;
    } else {
      wasAdded = aLiable.addLiableOf(this);
      if (!wasAdded) {
        liable.remove(aLiable);
      }
    }
    return wasAdded;
  }

  /* Code from template association_RemoveMany */
  public boolean removeLiable(Party aLiable) {
    boolean wasRemoved = false;
    if (!liable.contains(aLiable)) {
      return wasRemoved;
    }

    int oldIndex = liable.indexOf(aLiable);
    liable.remove(oldIndex);
    if (aLiable.indexOfLiableOf(this) == -1) {
      wasRemoved = true;
    } else {
      wasRemoved = aLiable.removeLiableOf(this);
      if (!wasRemoved) {
        liable.add(oldIndex, aLiable);
      }
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addLiableAt(Party aLiable, int index) {
    boolean wasAdded = false;
    if (addLiable(aLiable)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfLiable()) {
        index = numberOfLiable() - 1;
      }
      liable.remove(aLiable);
      liable.add(index, aLiable);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLiableAt(Party aLiable, int index) {
    boolean wasAdded = false;
    if (liable.contains(aLiable)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfLiable()) {
        index = numberOfLiable() - 1;
      }
      liable.remove(aLiable);
      liable.add(index, aLiable);
      wasAdded = true;
    } else {
      wasAdded = addLiableAt(aLiable, index);
    }
    return wasAdded;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRightHolder() {
    return 0;
  }

  /* Code from template association_AddManyToManyMethod */
  public boolean addRightHolder(Party aRightHolder) {
    boolean wasAdded = false;
    if (rightHolder.contains(aRightHolder)) {
      return false;
    }
    rightHolder.add(aRightHolder);
    if (aRightHolder.indexOfRihgtHolderOf(this) != -1) {
      wasAdded = true;
    } else {
      wasAdded = aRightHolder.addRihgtHolderOf(this);
      if (!wasAdded) {
        rightHolder.remove(aRightHolder);
      }
    }
    return wasAdded;
  }

  /* Code from template association_RemoveMany */
  public boolean removeRightHolder(Party aRightHolder) {
    boolean wasRemoved = false;
    if (!rightHolder.contains(aRightHolder)) {
      return wasRemoved;
    }

    int oldIndex = rightHolder.indexOf(aRightHolder);
    rightHolder.remove(oldIndex);
    if (aRightHolder.indexOfRihgtHolderOf(this) == -1) {
      wasRemoved = true;
    } else {
      wasRemoved = aRightHolder.removeRihgtHolderOf(this);
      if (!wasRemoved) {
        rightHolder.add(oldIndex, aRightHolder);
      }
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addRightHolderAt(Party aRightHolder, int index) {
    boolean wasAdded = false;
    if (addRightHolder(aRightHolder)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfRightHolder()) {
        index = numberOfRightHolder() - 1;
      }
      rightHolder.remove(aRightHolder);
      rightHolder.add(index, aRightHolder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRightHolderAt(Party aRightHolder, int index) {
    boolean wasAdded = false;
    if (rightHolder.contains(aRightHolder)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfRightHolder()) {
        index = numberOfRightHolder() - 1;
      }
      rightHolder.remove(aRightHolder);
      rightHolder.add(index, aRightHolder);
      wasAdded = true;
    } else {
      wasAdded = addRightHolderAt(aRightHolder, index);
    }
    return wasAdded;
  }

  /* Code from template association_SetOneToMandatoryMany */
  public boolean setContract(Contract aContract) {
    boolean wasSet = false;
    // Must provide contract to legalPosition
    if (aContract == null) {
      return wasSet;
    }

    if (contract != null && contract.numberOfLegalPositions() <= Contract.minimumNumberOfLegalPositions()) {
      return wasSet;
    }

    Contract existingContract = contract;
    contract = aContract;
    if (existingContract != null && !existingContract.equals(aContract)) {
      boolean didRemove = existingContract.removeLegalPosition(this);
      if (!didRemove) {
        contract = existingContract;
        return wasSet;
      }
    }
    contract.addLegalPosition(this);
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_SetOneToMany */
  public boolean setDebtor(Role aDebtor) {
    boolean wasSet = false;
    if (aDebtor == null) {
      return wasSet;
    }

    Role existingDebtor = debtor;
    debtor = aDebtor;
    if (existingDebtor != null && !existingDebtor.equals(aDebtor)) {
      existingDebtor.removeDebt(this);
    }
    debtor.addDebt(this);
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_SetOneToMany */
  public boolean setCreditor(Role aCreditor) {
    boolean wasSet = false;
    if (aCreditor == null) {
      return wasSet;
    }

    Role existingCreditor = creditor;
    creditor = aCreditor;
    if (existingCreditor != null && !existingCreditor.equals(aCreditor)) {
      existingCreditor.removeCredit(this);
    }
    creditor.addCredit(this);
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_SetOptionalOneToMany */
  public boolean setAsset(Asset aAsset) {
    boolean wasSet = false;
    Asset existingAsset = asset;
    asset = aAsset;
    if (existingAsset != null && !existingAsset.equals(aAsset)) {
      existingAsset.removeLegalPosition(this);
    }
    if (aAsset != null) {
      aAsset.addLegalPosition(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    ArrayList<Party> copyOfPerformer = new ArrayList<Party>(performer);
    performer.clear();
    for (Party aPerformer : copyOfPerformer) {
      aPerformer.removePerformerOf(this);
    }
    ArrayList<Party> copyOfLiable = new ArrayList<Party>(liable);
    liable.clear();
    for (Party aLiable : copyOfLiable) {
      aLiable.removeLiableOf(this);
    }
    ArrayList<Party> copyOfRightHolder = new ArrayList<Party>(rightHolder);
    rightHolder.clear();
    for (Party aRightHolder : copyOfRightHolder) {
      aRightHolder.removeRihgtHolderOf(this);
    }
    Contract placeholderContract = contract;
    this.contract = null;
    if (placeholderContract != null) {
      placeholderContract.removeLegalPosition(this);
    }
    Role placeholderDebtor = debtor;
    this.debtor = null;
    if (placeholderDebtor != null) {
      placeholderDebtor.removeDebt(this);
    }
    Role placeholderCreditor = creditor;
    this.creditor = null;
    if (placeholderCreditor != null) {
      placeholderCreditor.removeCredit(this);
    }
    if (asset != null) {
      Asset placeholderAsset = asset;
      this.asset = null;
      placeholderAsset.removeLegalPosition(this);
    }
  }

}
