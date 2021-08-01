package ca.uottawa.csmlab.symboleo.generator.Ontology;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.uottawa.csmlab.symboleo.generator.Ontology.Power.Status;
import ca.uottawa.csmlab.symboleo.generator.Ontology.Power.StatusActive;

public class Power extends LegalPosition {

//------------------------
// MEMBER VARIABLES
//------------------------

//Power State Machines
  public enum Status {
    Start, Create, Active, SuccessfulTermination, UnsuccessfulTermination
  }

  public enum StatusActive {
    Null, InEffect, Suspension
  }

  private Status status;
  private StatusActive statusActive;

//Power Associations
  private List<LegalPosition> legalPositions;
  private Contract contract;

//------------------------
// CONSTRUCTOR
//------------------------

  public Power(Contract aContract, Role aDebtor, Role aCreditor) {
    super(aContract, aDebtor, aCreditor);
    legalPositions = new ArrayList<LegalPosition>();
    setStatusActive(StatusActive.Null);
    setStatus(Status.Start);
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

  public boolean trigerredConditional() {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus) {
    case Start:
      setStatus(Status.Create);
      wasEventProcessed = true;
      break;
    default:
      // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean trigerredUnconditional() {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus) {
    case Start:
      setStatusActive(StatusActive.InEffect);
      wasEventProcessed = true;
      break;
    default:
      // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean expired() {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    StatusActive aStatusActive = statusActive;
    switch (aStatus) {
    case Create:
      setStatus(Status.UnsuccessfulTermination);
      wasEventProcessed = true;
      break;
    default:
      // Other states do respond to this event
    }

    switch (aStatusActive) {
    case InEffect:
      exitStatus();
      setStatus(Status.UnsuccessfulTermination);
      wasEventProcessed = true;
      break;
    default:
      // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean activated() {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus) {
    case Create:
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

  public boolean exerted() {
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
    }
  }

  private void setStatusActive(StatusActive aStatusActive) {
    statusActive = aStatusActive;
    if (status != Status.Active && aStatusActive != StatusActive.Null) {
      setStatus(Status.Active);
    }
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

  public boolean hasContract() {
    boolean has = contract != null;
    return has;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLegalPositions() {
    return 0;
  }

  /* Code from template association_AddUnidirectionalMany */
  public boolean addLegalPosition(LegalPosition aLegalPosition) {
    boolean wasAdded = false;
    if (legalPositions.contains(aLegalPosition)) {
      return false;
    }
    legalPositions.add(aLegalPosition);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLegalPosition(LegalPosition aLegalPosition) {
    boolean wasRemoved = false;
    if (legalPositions.contains(aLegalPosition)) {
      legalPositions.remove(aLegalPosition);
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

  /* Code from template association_SetOptionalOneToMany */
  public boolean setContract(Contract aContract) {
    boolean wasSet = false;
    Contract existingContract = contract;
    contract = aContract;
    if (existingContract != null && !existingContract.equals(aContract)) {
      existingContract.removePower(this);
    }
    if (aContract != null) {
      aContract.addPower(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    legalPositions.clear();
    if (contract != null) {
      Contract placeholderContract = contract;
      this.contract = null;
      placeholderContract.removePower(this);
    }
    super.delete();
  }

}
