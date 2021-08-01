package ca.uottawa.csmlab.symboleo.generator.Ontology;


public class Obligation extends LegalPosition {

//------------------------
// MEMBER VARIABLES
//------------------------

//Obligation State Machines
  public enum Status {
    Start, Create, Active, Violation, Discharge, Fulfillment, UnsuccessfulTermination
  }

  public enum StatusActive {
    Null, InEffect, Suspension
  }

  private Status status;
  private StatusActive statusActive;

//------------------------
// CONSTRUCTOR
//------------------------

  public Obligation(Contract aContract, Role aDebtor, Role aCreditor) {
    super(aContract, aDebtor, aCreditor);
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
    switch (aStatus) {
    case Create:
      setStatus(Status.Discharge);
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

  public boolean fulfilled() {
    boolean wasEventProcessed = false;

    StatusActive aStatusActive = statusActive;
    switch (aStatusActive) {
    case InEffect:
      exitStatus();
      setStatus(Status.Fulfillment);
      wasEventProcessed = true;
      break;
    default:
      // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean violated() {
    boolean wasEventProcessed = false;

    StatusActive aStatusActive = statusActive;
    switch (aStatusActive) {
    case InEffect:
      exitStatus();
      setStatus(Status.Violation);
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

  public boolean discharged() {
    boolean wasEventProcessed = false;

    StatusActive aStatusActive = statusActive;
    switch (aStatusActive) {
    case InEffect:
      exitStatus();
      setStatus(Status.Discharge);
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
    case Violation:
      delete();
      break;
    case Discharge:
      delete();
      break;
    case Fulfillment:
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

  public void delete() {
    super.delete();
  }

}
