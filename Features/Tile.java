/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/


import java.util.*;

// line 51 "model.ump"
// line 120 "model.ump"
public class Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tile Attributes
  private int x;
  private int y;
  private List<Tile> adjacentTiles;
  private boolean impassible=false;

  //Tile Associations
  private List<Player> players;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tile(int aX, int aY, boolean aImpassible)
  {
    x = aX;
    y = aY;
    adjacentTiles = new ArrayList<Tile>();
    impassible = aImpassible;
    players = new ArrayList<Player>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setX(int aX)
  {
    boolean wasSet = false;
    x = aX;
    wasSet = true;
    return wasSet;
  }

  public boolean setY(int aY)
  {
    boolean wasSet = false;
    y = aY;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetMany */
  public boolean addAdjacentTile(Tile aAdjacentTile)
  {
    boolean wasAdded = false;
    wasAdded = adjacentTiles.add(aAdjacentTile);
    return wasAdded;
  }

  public boolean removeAdjacentTile(Tile aAdjacentTile)
  {
    boolean wasRemoved = false;
    wasRemoved = adjacentTiles.remove(aAdjacentTile);
    return wasRemoved;
  }

  public boolean setImpassible(boolean aImpassible)
  {
    boolean wasSet = false;
    impassible = aImpassible;
    wasSet = true;
    return wasSet;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }
  /* Code from template attribute_GetMany */
  public Tile getAdjacentTile(int index)
  {
    Tile aAdjacentTile = adjacentTiles.get(index);
    return aAdjacentTile;
  }

  public Tile[] getAdjacentTiles()
  {
    Tile[] newAdjacentTiles = adjacentTiles.toArray(new Tile[adjacentTiles.size()]);
    return newAdjacentTiles;
  }

  public int numberOfAdjacentTiles()
  {
    int number = adjacentTiles.size();
    return number;
  }

  public boolean hasAdjacentTiles()
  {
    boolean has = adjacentTiles.size() > 0;
    return has;
  }

  public int indexOfAdjacentTile(Tile aAdjacentTile)
  {
    int index = adjacentTiles.indexOf(aAdjacentTile);
    return index;
  }

  public boolean getImpassible()
  {
    return impassible;
  }


  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }

  public void addPlayer(Player aPlayer)
  {
    if (players.contains(aPlayer)) { return;}
    players.add(aPlayer);
    aPlayer.addTile(this);
  }
  /* Code from template association_RemoveMany */
  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    if (!players.contains(aPlayer))
    {
      return wasRemoved;
    }

    int oldIndex = players.indexOf(aPlayer);
    players.remove(oldIndex);

    wasRemoved = aPlayer.removeTile(this);
    if (!wasRemoved)
    {
      players.add(oldIndex,aPlayer);
    }
    return wasRemoved;
  }


  public String toString()
  {
    return super.toString() + "["+
            "x" + ":" + getX()+ "," +
            "y" + ":" + getY()+ "," +
            "impassible" + ":" + getImpassible()+ "]";
  }

  public boolean equals(Tile t){
    if(t.x==x && t.y==y){
      return true;
    }
    return false;
  }
}