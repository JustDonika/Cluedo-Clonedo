/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/


import java.util.*;

// line 45 "model.ump"
// line 115 "model.ump"
public class Weapon
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Weapon Attributes
  private String name;
  private Estate position;

  //Weapon Associations
  private List<Estate> estates;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Weapon(String aName, Estate aPosition)
  {
    name = aName;
    position = aPosition;
    estates = new ArrayList<Estate>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getName()
  {
    return name;
  }

  public Estate getPosition()
  {
    return position;
  }

  public int numberOfEstates()
  {
    int number = estates.size();
    return number;
  }

  public int indexOfEstate(Estate aEstate)
  {
    int index = estates.indexOf(aEstate);
    return index;
  }

  public boolean addEstate(Estate aEstate)
  {
    boolean wasAdded = false;
    if (estates.contains(aEstate)) { return false; }
    estates.add(aEstate);
    if (aEstate.indexOfWeapon(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aEstate.addWeapon(this);
      if (!wasAdded)
      {
        estates.remove(aEstate);
      }
    }
    return wasAdded;
  }

  public boolean removeEstate(Estate aEstate)
  {
    boolean wasRemoved = false;
    if (!estates.contains(aEstate))
    {
      return wasRemoved;
    }

    int oldIndex = estates.indexOf(aEstate);
    estates.remove(oldIndex);
    if (aEstate.indexOfWeapon(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aEstate.removeWeapon(this);
      if (!wasRemoved)
      {
        estates.add(oldIndex,aEstate);
      }
    }
    return wasRemoved;
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "position" + "=" + (getPosition() != null ? !getPosition().equals(this)  ? getPosition().toString().replaceAll("  ","    ") : "this" : "null");
  }
}