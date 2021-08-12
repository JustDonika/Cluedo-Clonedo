/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/


import java.util.*;

// line 59 "model.ump"
// line 125 "model.ump"
public class Estate extends Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Estate Attributes
  private List<Tile> exitTiles;
  private String name;
  private Weapon weapon;
  private int x2;//right side of estate
  private int y2;//bottom of estate

  //Estate Associations
  private List<Weapon> weapons;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Estate(int aX, int bX, int aY, int bY, boolean aImpassible, String aName, Weapon aWeapon)
  {
    super(aX, aY, aImpassible);
    x2=bX;
    y2=bY;
    exitTiles = new ArrayList<Tile>();
    name = aName;
    weapon = aWeapon;
    weapons = new ArrayList<Weapon>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template attribute_SetMany */
  public void addExitTile(Tile aExitTile)
  {
    exitTiles.add(aExitTile);
  }

  //Added in manually
  public int getX2(){
    return x2;
  }

  public int getY2(){
    return y2;
  }

  public Tile[] getExitTiles()
  {
    return exitTiles.toArray(new Tile[exitTiles.size()]);
  }

  public String getName()
  {
    return name;
  }

  public Weapon getWeapon()
  {
    return weapon;
  }

  public Weapon getWeapon(int index)
  {
    return weapons.get(index);
  }

  public List<Weapon> getWeapons()
  {
    return Collections.unmodifiableList(weapons);
  }

  public int indexOfWeapon(Weapon aWeapon)
  {
    int index = weapons.indexOf(aWeapon);
    return index;
  }

  public boolean addWeapon(Weapon aWeapon)
  {
    boolean wasAdded = false;
    if (weapons.contains(aWeapon)) { return false; }
    weapons.add(aWeapon);
    if (aWeapon.indexOfEstate(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aWeapon.addEstate(this);
      if (!wasAdded)
      {
        weapons.remove(aWeapon);
      }
    }
    return wasAdded;
  }

  public boolean removeWeapon(Weapon aWeapon)
  {
    boolean wasRemoved = false;
    if (!weapons.contains(aWeapon))
    {
      return wasRemoved;
    }

    int oldIndex = weapons.indexOf(aWeapon);
    weapons.remove(oldIndex);
    if (aWeapon.indexOfEstate(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aWeapon.removeEstate(this);
      if (!wasRemoved)
      {
        weapons.add(oldIndex,aWeapon);
      }
    }
    return wasRemoved;
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "weapon" + "=" + (getWeapon() != null ? !getWeapon().equals(this)  ? getWeapon().toString().replaceAll("  ","    ") : "this" : "null");
  }
}