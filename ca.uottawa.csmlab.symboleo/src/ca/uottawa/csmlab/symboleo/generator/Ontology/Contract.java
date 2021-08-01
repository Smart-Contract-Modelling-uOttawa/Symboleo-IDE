package ca.uottawa.csmlab.symboleo.generator.Ontology;

import java.util.*;


public class Contract {

//------------------------
// MEMBER VARIABLES
//------------------------

//Contract State Machines
  public enum Status {
    Form, Active, SuccessfulTermination, UnsuccessfulTermination
  }

  public enum StatusActive {
    Null, InEffect, Suspension, Unassign, Rescission
  }

  private Status status;
  private StatusActive statusActive;

//Contract Associations
  private List<Power> powers;
  private List<LegalPosition> legalPositions;
  private List<Role> roles;
  private List<Party> parties;
  private List<Asset> assets;
  private List<Event> events;
  private String name;

//------------------------
// CONSTRUCTOR
//------------------------

  public Contract(String name) {
    this.name = name;
    powers = new ArrayList<Power>();
    legalPositions = new ArrayList<LegalPosition>();
    roles = new ArrayList<Role>();
    parties = new ArrayList<Party>();
    assets = new ArrayList<Asset>();
    setStatusActive(StatusActive.Null);
    setStatus(Status.Form);
  }

//------------------------
// INTERFACE
//------------------------

  public String getStatusFullName() {
    String answer = status.toString();
    if (statusActive != StatusActive.Null) {
      answer += "." + statusActive.toString();
    }
    return answer;
  }

  public Status getStatus() {
    return status;
  }

  public StatusActive getStatusActive() {
    return statusActive;
  }

  public boolean activated() {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus) {
    case Form:
      setStatusActive(StatusActive.InEffect);
      wasEventProcessed = true;
      break;
    default:
      // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean terminated() {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus) {
    case Active:
      exitStatus();
      setStatus(Status.UnsuccessfulTermination);
      wasEventProcessed = true;
      break;
    default:
      // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean rescinded() {
    boolean wasEventProcessed = false;

    StatusActive aStatusActive = statusActive;
    switch (aStatusActive) {
    case InEffect:
      exitStatusActive();
      setStatusActive(StatusActive.Rescission);
      wasEventProcessed = true;
      break;
    default:
      // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean suspended() {
    boolean wasEventProcessed = false;

    StatusActive aStatusActive = statusActive;
    switch (aStatusActive) {
    case InEffect:
      exitStatusActive();
      setStatusActive(StatusActive.Suspension);
      wasEventProcessed = true;
      break;
    default:
      // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean revokedParty() {
    boolean wasEventProcessed = false;

    StatusActive aStatusActive = statusActive;
    switch (aStatusActive) {
    case InEffect:
      exitStatusActive();
      setStatusActive(StatusActive.Unassign);
      wasEventProcessed = true;
      break;
    default:
      // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean fulfilledActiveObligations() {
    boolean wasEventProcessed = false;

    StatusActive aStatusActive = statusActive;
    switch (aStatusActive) {
    case InEffect:
      exitStatus();
      setStatus(Status.SuccessfulTermination);
      wasEventProcessed = true;
      break;
    default:
      // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean resumed() {
    boolean wasEventProcessed = false;

    StatusActive aStatusActive = statusActive;
    switch (aStatusActive) {
    case Suspension:
      exitStatusActive();
      setStatusActive(StatusActive.InEffect);
      wasEventProcessed = true;
      break;
    default:
      // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean assignedParty() {
    boolean wasEventProcessed = false;

    StatusActive aStatusActive = statusActive;
    switch (aStatusActive) {
    case Unassign:
      exitStatusActive();
      setStatusActive(StatusActive.InEffect);
      wasEventProcessed = true;
      break;
    default:
      // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitStatus() {
    switch (status) {
    case Active:
      exitStatusActive();
      break;
    }
  }

  private void setStatus(Status aStatus) {
    status = aStatus;

    // entry actions and do activities
    switch (status) {
    case Active:
      if (statusActive == StatusActive.Null) {
        setStatusActive(StatusActive.InEffect);
      }
      break;
    case SuccessfulTermination:
      delete();
      break;
    case UnsuccessfulTermination:
      delete();
      break;
    }
  }

  private void exitStatusActive() {
    switch (statusActive) {
    case InEffect:
      setStatusActive(StatusActive.Null);
      break;
    case Suspension:
      setStatusActive(StatusActive.Null);
      break;
    case Unassign:
      setStatusActive(StatusActive.Null);
      break;
    case Rescission:
      setStatusActive(StatusActive.Null);
      break;
    }
  }

  private void setStatusActive(StatusActive aStatusActive) {
    statusActive = aStatusActive;
    if (status != Status.Active && aStatusActive != StatusActive.Null) {
      setStatus(Status.Active);
    }

    // entry actions and do activities
    switch (statusActive) {
    case Rescission:
      delete();
      break;
    }
  }

  /* Code from template association_GetMany */
  public Power getPower(int index) {
    Power aPower = powers.get(index);
    return aPower;
  }

  public List<Power> getPowers() {
    List<Power> newPowers = Collections.unmodifiableList(powers);
    return newPowers;
  }

  public int numberOfPowers() {
    int number = powers.size();
    return number;
  }

  public boolean hasPowers() {
    boolean has = powers.size() > 0;
    return has;
  }

  public int indexOfPower(Power aPower) {
    int index = powers.indexOf(aPower);
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

  /* Code from template association_GetMany */
  public Role getRole(int index) {
    Role aRole = roles.get(index);
    return aRole;
  }

  public List<Role> getRoles() {
    List<Role> newRoles = Collections.unmodifiableList(roles);
    return newRoles;
  }

  public int numberOfRoles() {
    int number = roles.size();
    return number;
  }

  public boolean hasRoles() {
    boolean has = roles.size() > 0;
    return has;
  }

  public int indexOfRole(Role aRole) {
    int index = roles.indexOf(aRole);
    return index;
  }

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
  public Asset getAsset(int index) {
    Asset aAsset = assets.get(index);
    return aAsset;
  }

  public List<Asset> getAssets() {
    List<Asset> newAssets = Collections.unmodifiableList(assets);
    return newAssets;
  }

  public int numberOfAssets() {
    int number = assets.size();
    return number;
  }

  public boolean hasAssets() {
    boolean has = assets.size() > 0;
    return has;
  }

  public int indexOfAsset(Asset aAsset) {
    int index = assets.indexOf(aAsset);
    return index;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPowers() {
    return 0;
  }

  /* Code from template association_AddManyToOptionalOne */
  public boolean addPower(Power aPower) {
    boolean wasAdded = false;
    if (powers.contains(aPower)) {
      return false;
    }
    Contract existingContract = aPower.getContract();
    if (existingContract == null) {
      aPower.setContract(this);
    } else if (!this.equals(existingContract)) {
      existingContract.removePower(aPower);
      addPower(aPower);
    } else {
      powers.add(aPower);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePower(Power aPower) {
    boolean wasRemoved = false;
    if (powers.contains(aPower)) {
      powers.remove(aPower);
      aPower.setContract(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addPowerAt(Power aPower, int index) {
    boolean wasAdded = false;
    if (addPower(aPower)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfPowers()) {
        index = numberOfPowers() - 1;
      }
      powers.remove(aPower);
      powers.add(index, aPower);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePowerAt(Power aPower, int index) {
    boolean wasAdded = false;
    if (powers.contains(aPower)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfPowers()) {
        index = numberOfPowers() - 1;
      }
      powers.remove(aPower);
      powers.add(index, aPower);
      wasAdded = true;
    } else {
      wasAdded = addPowerAt(aPower, index);
    }
    return wasAdded;
  }

  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfLegalPositionsValid() {
    boolean isValid = numberOfLegalPositions() >= minimumNumberOfLegalPositions();
    return isValid;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLegalPositions() {
    return 2;
  }

  /* Code from template association_AddMandatoryManyToOne */
  public LegalPosition addLegalPosition(Role aDebtor, Role aCreditor) {
    LegalPosition aNewLegalPosition = new LegalPosition(this, aDebtor, aCreditor);
    return aNewLegalPosition;
  }

  public boolean addLegalPosition(LegalPosition aLegalPosition) {
    boolean wasAdded = false;
    if (legalPositions.contains(aLegalPosition)) {
      return false;
    }
    Contract existingContract = aLegalPosition.getContract();
    boolean isNewContract = existingContract != null && !this.equals(existingContract);

    if (isNewContract && existingContract.numberOfLegalPositions() <= minimumNumberOfLegalPositions()) {
      return wasAdded;
    }
    if (isNewContract) {
      aLegalPosition.setContract(this);
    } else {
      legalPositions.add(aLegalPosition);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLegalPosition(LegalPosition aLegalPosition) {
    boolean wasRemoved = false;
    // Unable to remove aLegalPosition, as it must always have a contract
    if (this.equals(aLegalPosition.getContract())) {
      return wasRemoved;
    }

    // contract already at minimum (2)
    if (numberOfLegalPositions() <= minimumNumberOfLegalPositions()) {
      return wasRemoved;
    }

    legalPositions.remove(aLegalPosition);
    wasRemoved = true;
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

  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfRolesValid() {
    boolean isValid = numberOfRoles() >= minimumNumberOfRoles();
    return isValid;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRoles() {
    return 2;
  }

  public boolean addRole(Role aRole) {
    boolean wasAdded = false;
    if (roles.contains(aRole)) {
      return false;
    }
    Contract existingContract = aRole.getContract();
    boolean isNewContract = existingContract != null && !this.equals(existingContract);

    if (isNewContract && existingContract.numberOfRoles() <= minimumNumberOfRoles()) {
      return wasAdded;
    }
    if (isNewContract) {
      aRole.setContract(this);
    } else {
      roles.add(aRole);
    }
    wasAdded = true;
    return wasAdded;
  }
  
  public boolean addEvent(Event aEvent) {
    boolean wasAdded = false;
    if (events.contains(aEvent)) {
      return false;
    }
    Contract existingContract = aEvent.getContract();
    boolean isNewContract = existingContract != null && !this.equals(existingContract);

    if (isNewContract && existingContract.numberOfRoles() <= minimumNumberOfRoles()) {
      return wasAdded;
    }
    if (isNewContract) {
      aEvent.setContract(this);
    } else {
      events.add(aEvent);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRole(Role aRole) {
    boolean wasRemoved = false;
    // Unable to remove aRole, as it must always have a contract
    if (this.equals(aRole.getContract())) {
      return wasRemoved;
    }

    // contract already at minimum (2)
    if (numberOfRoles() <= minimumNumberOfRoles()) {
      return wasRemoved;
    }

    roles.remove(aRole);
    wasRemoved = true;
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addRoleAt(Role aRole, int index) {
    boolean wasAdded = false;
    if (addRole(aRole)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfRoles()) {
        index = numberOfRoles() - 1;
      }
      roles.remove(aRole);
      roles.add(index, aRole);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRoleAt(Role aRole, int index) {
    boolean wasAdded = false;
    if (roles.contains(aRole)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfRoles()) {
        index = numberOfRoles() - 1;
      }
      roles.remove(aRole);
      roles.add(index, aRole);
      wasAdded = true;
    } else {
      wasAdded = addRoleAt(aRole, index);
    }
    return wasAdded;
  }

  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfPartiesValid() {
    boolean isValid = numberOfParties() >= minimumNumberOfParties();
    return isValid;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfParties() {
    return 2;
  }

  /* Code from template association_AddMandatoryManyToOne */
  public Party addParty(Role... allRoles) {
    Party aNewParty = new Party(this, allRoles);
    return aNewParty;
  }

  public boolean addParty(Party aParty) {
    boolean wasAdded = false;
    if (parties.contains(aParty)) {
      return false;
    }
    Contract existingContract = aParty.getContract();
    boolean isNewContract = existingContract != null && !this.equals(existingContract);

    if (isNewContract && existingContract.numberOfParties() <= minimumNumberOfParties()) {
      return wasAdded;
    }
    if (isNewContract) {
      aParty.setContract(this);
    } else {
      parties.add(aParty);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeParty(Party aParty) {
    boolean wasRemoved = false;
    // Unable to remove aParty, as it must always have a contract
    if (this.equals(aParty.getContract())) {
      return wasRemoved;
    }

    // contract already at minimum (2)
    if (numberOfParties() <= minimumNumberOfParties()) {
      return wasRemoved;
    }

    parties.remove(aParty);
    wasRemoved = true;
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
  public static int minimumNumberOfAssets() {
    return 0;
  }

  public boolean addAsset(Asset aAsset) {
    boolean wasAdded = false;
    if (assets.contains(aAsset)) {
      return false;
    }
    Contract existingContract = aAsset.getContract();
    boolean isNewContract = existingContract != null && !this.equals(existingContract);
    if (isNewContract) {
      aAsset.setContract(this);
    } else {
      assets.add(aAsset);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAsset(Asset aAsset) {
    boolean wasRemoved = false;
    // Unable to remove aAsset, as it must always have a contract
    if (!this.equals(aAsset.getContract())) {
      assets.remove(aAsset);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addAssetAt(Asset aAsset, int index) {
    boolean wasAdded = false;
    if (addAsset(aAsset)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfAssets()) {
        index = numberOfAssets() - 1;
      }
      assets.remove(aAsset);
      assets.add(index, aAsset);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAssetAt(Asset aAsset, int index) {
    boolean wasAdded = false;
    if (assets.contains(aAsset)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfAssets()) {
        index = numberOfAssets() - 1;
      }
      assets.remove(aAsset);
      assets.add(index, aAsset);
      wasAdded = true;
    } else {
      wasAdded = addAssetAt(aAsset, index);
    }
    return wasAdded;
  }

  public void delete() {
    while (!powers.isEmpty()) {
      powers.get(0).setContract(null);
    }
    while (legalPositions.size() > 0) {
      LegalPosition aLegalPosition = legalPositions.get(legalPositions.size() - 1);
      aLegalPosition.delete();
      legalPositions.remove(aLegalPosition);
    }

    while (roles.size() > 0) {
      Role aRole = roles.get(roles.size() - 1);
      aRole.delete();
      roles.remove(aRole);
    }

    for (int i = parties.size(); i > 0; i--) {
      Party aParty = parties.get(i - 1);
      aParty.delete();
    }
    for (int i = assets.size(); i > 0; i--) {
      Asset aAsset = assets.get(i - 1);
      aAsset.delete();
    }
  }

}

