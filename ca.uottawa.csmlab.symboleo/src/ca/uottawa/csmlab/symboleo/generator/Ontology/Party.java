package ca.uottawa.csmlab.symboleo.generator.Ontology;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Party {

//------------------------
// MEMBER VARIABLES
//------------------------

//Party Associations
  private Contract contract;
  private List<Role> roles;
  private List<Asset> assets;
  private List<LegalPosition> performerOf;
  private List<LegalPosition> liableOf;
  private List<LegalPosition> rihgtHolderOf;

//------------------------
// CONSTRUCTOR
//------------------------

  public Party(Contract aContract, Role... allRoles) {
    boolean didAddContract = setContract(aContract);
    if (!didAddContract) {
      throw new RuntimeException(
          "Unable to create party due to contract. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    roles = new ArrayList<Role>();
    boolean didAddRoles = setRoles(allRoles);
    if (!didAddRoles) {
      throw new RuntimeException(
          "Unable to create Party, must have at least 1 roles. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    assets = new ArrayList<Asset>();
    performerOf = new ArrayList<LegalPosition>();
    liableOf = new ArrayList<LegalPosition>();
    rihgtHolderOf = new ArrayList<LegalPosition>();
  }

//------------------------
// INTERFACE
//------------------------
  /* Code from template association_GetOne */
  public Contract getContract() {
    return contract;
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

  /* Code from template association_GetMany */
  public LegalPosition getPerformerOf(int index) {
    LegalPosition aPerformerOf = performerOf.get(index);
    return aPerformerOf;
  }

  public List<LegalPosition> getPerformerOf() {
    List<LegalPosition> newPerformerOf = Collections.unmodifiableList(performerOf);
    return newPerformerOf;
  }

  public int numberOfPerformerOf() {
    int number = performerOf.size();
    return number;
  }

  public boolean hasPerformerOf() {
    boolean has = performerOf.size() > 0;
    return has;
  }

  public int indexOfPerformerOf(LegalPosition aPerformerOf) {
    int index = performerOf.indexOf(aPerformerOf);
    return index;
  }

  /* Code from template association_GetMany */
  public LegalPosition getLiableOf(int index) {
    LegalPosition aLiableOf = liableOf.get(index);
    return aLiableOf;
  }

  public List<LegalPosition> getLiableOf() {
    List<LegalPosition> newLiableOf = Collections.unmodifiableList(liableOf);
    return newLiableOf;
  }

  public int numberOfLiableOf() {
    int number = liableOf.size();
    return number;
  }

  public boolean hasLiableOf() {
    boolean has = liableOf.size() > 0;
    return has;
  }

  public int indexOfLiableOf(LegalPosition aLiableOf) {
    int index = liableOf.indexOf(aLiableOf);
    return index;
  }

  /* Code from template association_GetMany */
  public LegalPosition getRihgtHolderOf(int index) {
    LegalPosition aRihgtHolderOf = rihgtHolderOf.get(index);
    return aRihgtHolderOf;
  }

  public List<LegalPosition> getRihgtHolderOf() {
    List<LegalPosition> newRihgtHolderOf = Collections.unmodifiableList(rihgtHolderOf);
    return newRihgtHolderOf;
  }

  public int numberOfRihgtHolderOf() {
    int number = rihgtHolderOf.size();
    return number;
  }

  public boolean hasRihgtHolderOf() {
    boolean has = rihgtHolderOf.size() > 0;
    return has;
  }

  public int indexOfRihgtHolderOf(LegalPosition aRihgtHolderOf) {
    int index = rihgtHolderOf.indexOf(aRihgtHolderOf);
    return index;
  }

  /* Code from template association_SetOneToMandatoryMany */
  public boolean setContract(Contract aContract) {
    boolean wasSet = false;
    // Must provide contract to party
    if (aContract == null) {
      return wasSet;
    }

    if (contract != null && contract.numberOfParties() <= Contract.minimumNumberOfParties()) {
      return wasSet;
    }

    Contract existingContract = contract;
    contract = aContract;
    if (existingContract != null && !existingContract.equals(aContract)) {
      boolean didRemove = existingContract.removeParty(this);
      if (!didRemove) {
        contract = existingContract;
        return wasSet;
      }
    }
    contract.addParty(this);
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRoles() {
    return 1;
  }

  /* Code from template association_AddMNToOptionalOne */
  public boolean addRole(Role aRole) {
    boolean wasAdded = false;
    if (roles.contains(aRole)) {
      return false;
    }
    Party existingParty = aRole.getParty();
    if (existingParty != null && existingParty.numberOfRoles() <= minimumNumberOfRoles()) {
      return wasAdded;
    } else if (existingParty != null) {
      existingParty.roles.remove(aRole);
    }
    roles.add(aRole);
    setParty(aRole, this);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRole(Role aRole) {
    boolean wasRemoved = false;
    if (roles.contains(aRole) && numberOfRoles() > minimumNumberOfRoles()) {
      roles.remove(aRole);
      setParty(aRole, null);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_SetMNToOptionalOne */
  public boolean setRoles(Role... newRoles) {
    boolean wasSet = false;
    if (newRoles.length < minimumNumberOfRoles()) {
      return wasSet;
    }

    ArrayList<Role> checkNewRoles = new ArrayList<Role>();
    HashMap<Party, Integer> partyToNewRoles = new HashMap<Party, Integer>();
    for (Role aRole : newRoles) {
      if (checkNewRoles.contains(aRole)) {
        return wasSet;
      } else if (aRole.getParty() != null && !this.equals(aRole.getParty())) {
        Party existingParty = aRole.getParty();
        if (!partyToNewRoles.containsKey(existingParty)) {
          partyToNewRoles.put(existingParty, Integer.valueOf(existingParty.numberOfRoles()));
        }
        Integer currentCount = partyToNewRoles.get(existingParty);
        int nextCount = currentCount - 1;
        if (nextCount < 1) {
          return wasSet;
        }
        partyToNewRoles.put(existingParty, Integer.valueOf(nextCount));
      }
      checkNewRoles.add(aRole);
    }

    roles.removeAll(checkNewRoles);

    for (Role orphan : roles) {
      setParty(orphan, null);
    }
    roles.clear();
    for (Role aRole : newRoles) {
      if (aRole.getParty() != null) {
        aRole.getParty().roles.remove(aRole);
      }
      setParty(aRole, this);
      roles.add(aRole);
    }
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_GetPrivate */
  private void setParty(Role aRole, Party aParty) {
    try {
      java.lang.reflect.Field mentorField = aRole.getClass().getDeclaredField("party");
      mentorField.setAccessible(true);
      mentorField.set(aRole, aParty);
    } catch (Exception e) {
      throw new RuntimeException("Issue internally setting aParty to aRole", e);
    }
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

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAssets() {
    return 0;
  }

  /* Code from template association_AddManyToManyMethod */
  public boolean addAsset(Asset aAsset) {
    boolean wasAdded = false;
    if (assets.contains(aAsset)) {
      return false;
    }
    assets.add(aAsset);
    if (aAsset.indexOfParty(this) != -1) {
      wasAdded = true;
    } else {
      wasAdded = aAsset.addParty(this);
      if (!wasAdded) {
        assets.remove(aAsset);
      }
    }
    return wasAdded;
  }

  /* Code from template association_RemoveMany */
  public boolean removeAsset(Asset aAsset) {
    boolean wasRemoved = false;
    if (!assets.contains(aAsset)) {
      return wasRemoved;
    }

    int oldIndex = assets.indexOf(aAsset);
    assets.remove(oldIndex);
    if (aAsset.indexOfParty(this) == -1) {
      wasRemoved = true;
    } else {
      wasRemoved = aAsset.removeParty(this);
      if (!wasRemoved) {
        assets.add(oldIndex, aAsset);
      }
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

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPerformerOf() {
    return 0;
  }

  /* Code from template association_AddManyToManyMethod */
  public boolean addPerformerOf(LegalPosition aPerformerOf) {
    boolean wasAdded = false;
    if (performerOf.contains(aPerformerOf)) {
      return false;
    }
    performerOf.add(aPerformerOf);
    if (aPerformerOf.indexOfPerformer(this) != -1) {
      wasAdded = true;
    } else {
      wasAdded = aPerformerOf.addPerformer(this);
      if (!wasAdded) {
        performerOf.remove(aPerformerOf);
      }
    }
    return wasAdded;
  }

  /* Code from template association_RemoveMany */
  public boolean removePerformerOf(LegalPosition aPerformerOf) {
    boolean wasRemoved = false;
    if (!performerOf.contains(aPerformerOf)) {
      return wasRemoved;
    }

    int oldIndex = performerOf.indexOf(aPerformerOf);
    performerOf.remove(oldIndex);
    if (aPerformerOf.indexOfPerformer(this) == -1) {
      wasRemoved = true;
    } else {
      wasRemoved = aPerformerOf.removePerformer(this);
      if (!wasRemoved) {
        performerOf.add(oldIndex, aPerformerOf);
      }
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addPerformerOfAt(LegalPosition aPerformerOf, int index) {
    boolean wasAdded = false;
    if (addPerformerOf(aPerformerOf)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfPerformerOf()) {
        index = numberOfPerformerOf() - 1;
      }
      performerOf.remove(aPerformerOf);
      performerOf.add(index, aPerformerOf);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePerformerOfAt(LegalPosition aPerformerOf, int index) {
    boolean wasAdded = false;
    if (performerOf.contains(aPerformerOf)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfPerformerOf()) {
        index = numberOfPerformerOf() - 1;
      }
      performerOf.remove(aPerformerOf);
      performerOf.add(index, aPerformerOf);
      wasAdded = true;
    } else {
      wasAdded = addPerformerOfAt(aPerformerOf, index);
    }
    return wasAdded;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLiableOf() {
    return 0;
  }

  /* Code from template association_AddManyToManyMethod */
  public boolean addLiableOf(LegalPosition aLiableOf) {
    boolean wasAdded = false;
    if (liableOf.contains(aLiableOf)) {
      return false;
    }
    liableOf.add(aLiableOf);
    if (aLiableOf.indexOfLiable(this) != -1) {
      wasAdded = true;
    } else {
      wasAdded = aLiableOf.addLiable(this);
      if (!wasAdded) {
        liableOf.remove(aLiableOf);
      }
    }
    return wasAdded;
  }

  /* Code from template association_RemoveMany */
  public boolean removeLiableOf(LegalPosition aLiableOf) {
    boolean wasRemoved = false;
    if (!liableOf.contains(aLiableOf)) {
      return wasRemoved;
    }

    int oldIndex = liableOf.indexOf(aLiableOf);
    liableOf.remove(oldIndex);
    if (aLiableOf.indexOfLiable(this) == -1) {
      wasRemoved = true;
    } else {
      wasRemoved = aLiableOf.removeLiable(this);
      if (!wasRemoved) {
        liableOf.add(oldIndex, aLiableOf);
      }
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addLiableOfAt(LegalPosition aLiableOf, int index) {
    boolean wasAdded = false;
    if (addLiableOf(aLiableOf)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfLiableOf()) {
        index = numberOfLiableOf() - 1;
      }
      liableOf.remove(aLiableOf);
      liableOf.add(index, aLiableOf);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLiableOfAt(LegalPosition aLiableOf, int index) {
    boolean wasAdded = false;
    if (liableOf.contains(aLiableOf)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfLiableOf()) {
        index = numberOfLiableOf() - 1;
      }
      liableOf.remove(aLiableOf);
      liableOf.add(index, aLiableOf);
      wasAdded = true;
    } else {
      wasAdded = addLiableOfAt(aLiableOf, index);
    }
    return wasAdded;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRihgtHolderOf() {
    return 0;
  }

  /* Code from template association_AddManyToManyMethod */
  public boolean addRihgtHolderOf(LegalPosition aRihgtHolderOf) {
    boolean wasAdded = false;
    if (rihgtHolderOf.contains(aRihgtHolderOf)) {
      return false;
    }
    rihgtHolderOf.add(aRihgtHolderOf);
    if (aRihgtHolderOf.indexOfRightHolder(this) != -1) {
      wasAdded = true;
    } else {
      wasAdded = aRihgtHolderOf.addRightHolder(this);
      if (!wasAdded) {
        rihgtHolderOf.remove(aRihgtHolderOf);
      }
    }
    return wasAdded;
  }

  /* Code from template association_RemoveMany */
  public boolean removeRihgtHolderOf(LegalPosition aRihgtHolderOf) {
    boolean wasRemoved = false;
    if (!rihgtHolderOf.contains(aRihgtHolderOf)) {
      return wasRemoved;
    }

    int oldIndex = rihgtHolderOf.indexOf(aRihgtHolderOf);
    rihgtHolderOf.remove(oldIndex);
    if (aRihgtHolderOf.indexOfRightHolder(this) == -1) {
      wasRemoved = true;
    } else {
      wasRemoved = aRihgtHolderOf.removeRightHolder(this);
      if (!wasRemoved) {
        rihgtHolderOf.add(oldIndex, aRihgtHolderOf);
      }
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addRihgtHolderOfAt(LegalPosition aRihgtHolderOf, int index) {
    boolean wasAdded = false;
    if (addRihgtHolderOf(aRihgtHolderOf)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfRihgtHolderOf()) {
        index = numberOfRihgtHolderOf() - 1;
      }
      rihgtHolderOf.remove(aRihgtHolderOf);
      rihgtHolderOf.add(index, aRihgtHolderOf);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRihgtHolderOfAt(LegalPosition aRihgtHolderOf, int index) {
    boolean wasAdded = false;
    if (rihgtHolderOf.contains(aRihgtHolderOf)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfRihgtHolderOf()) {
        index = numberOfRihgtHolderOf() - 1;
      }
      rihgtHolderOf.remove(aRihgtHolderOf);
      rihgtHolderOf.add(index, aRihgtHolderOf);
      wasAdded = true;
    } else {
      wasAdded = addRihgtHolderOfAt(aRihgtHolderOf, index);
    }
    return wasAdded;
  }

  public void delete() {
    Contract placeholderContract = contract;
    this.contract = null;
    if (placeholderContract != null) {
      placeholderContract.removeParty(this);
    }
    for (Role aRole : roles) {
      setParty(aRole, null);
    }
    roles.clear();
    ArrayList<Asset> copyOfAssets = new ArrayList<Asset>(assets);
    assets.clear();
    for (Asset aAsset : copyOfAssets) {
      aAsset.removeParty(this);
    }
    ArrayList<LegalPosition> copyOfPerformerOf = new ArrayList<LegalPosition>(performerOf);
    performerOf.clear();
    for (LegalPosition aPerformerOf : copyOfPerformerOf) {
      aPerformerOf.removePerformer(this);
    }
    ArrayList<LegalPosition> copyOfLiableOf = new ArrayList<LegalPosition>(liableOf);
    liableOf.clear();
    for (LegalPosition aLiableOf : copyOfLiableOf) {
      aLiableOf.removeLiable(this);
    }
    ArrayList<LegalPosition> copyOfRihgtHolderOf = new ArrayList<LegalPosition>(rihgtHolderOf);
    rihgtHolderOf.clear();
    for (LegalPosition aRihgtHolderOf : copyOfRihgtHolderOf) {
      aRihgtHolderOf.removeRightHolder(this);
    }
  }

}
