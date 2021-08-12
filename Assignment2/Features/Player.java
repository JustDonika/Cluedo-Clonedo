/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/


import java.util.*;

// line 14 "model.ump"
// line 88 "model.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private boolean playerState; //Is player still in game
  private int moves;
  private String name;
  private List<Card> hand = new ArrayList<>();
  private List<Tile> travelled = new ArrayList<>();

  //Player Associations
  private List<Tile> tiles = new ArrayList<>();
  private List<Card> cards = new ArrayList<>();


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(boolean aPlayerState, int aMoves, String aName)
  {
    playerState = aPlayerState;
    moves = aMoves;
    name = aName;
    hand = new ArrayList<Card>();
    travelled = new ArrayList<Tile>();
    tiles = new ArrayList<Tile>();
    cards = new ArrayList<Card>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPlayerState(boolean aPlayerState)
  {
    boolean wasSet = false;
    playerState = aPlayerState;
    wasSet = true;
    return wasSet;
  }

  public boolean setMoves(int aMoves)
  {
    boolean wasSet = false;
    moves = aMoves;
    wasSet = true;
    return wasSet;
  }

  public boolean addHand(Card aHand)
  {
    boolean wasAdded = false;
    wasAdded = hand.add(aHand);
    return wasAdded;
  }

  public boolean addTravelled(Tile aTravelled)
  {
    boolean wasAdded = false;
    wasAdded = travelled.add(aTravelled);
    return wasAdded;
  }

  public boolean getPlayerState()
  {
    return playerState;
  }

  public int getMoves()
  {
    return moves;
  }

  public String getName()
  {
    return name;
  }

  public Card[] getHand()
  {
    hand=cards;
    Card[] newHand = hand.toArray(new Card[hand.size()]);
    return newHand;
  }

  //why does Umple not generate this one
  public void clearTravelled(){
    travelled.clear();
  }

  public ArrayList<Tile> getTravelled()
  {
    return (ArrayList<Tile>) travelled;
  }

  public Tile getTile(int index)
  {
    Tile aTile = tiles.get(index);
    return aTile;
  }

  public int indexOfCard(Card aCard)
  {
    int index = cards.indexOf(aCard);
    return index;
  }

  public void addTile(Tile aTile)
  {
    boolean wasAdded = false;
    if (tiles.contains(aTile)) { return; }
    tiles.add(aTile);
    if (aTile.indexOfPlayer(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      aTile.addPlayer(this);
    }
  }

  public boolean removeTile(Tile aTile)
  {
    boolean wasRemoved = false;
    if (!tiles.contains(aTile))
    {
      return wasRemoved;
    }

    int oldIndex = tiles.indexOf(aTile);
    tiles.remove(oldIndex);
    if (aTile.indexOfPlayer(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aTile.removePlayer(this);
      if (!wasRemoved)
      {
        tiles.add(oldIndex,aTile);
      }
    }
    return wasRemoved;
  }

  public boolean addCard(Card aCard)
  {
    boolean wasAdded = false;
    if (cards.contains(aCard)) { return false; }
    cards.add(aCard);
    if (aCard.indexOfPlayer(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aCard.addPlayer(this);
      if (!wasAdded)
      {
        cards.remove(aCard);
      }
    }
    return wasAdded;
  }

  public boolean removeCard(Card aCard)
  {
    boolean wasRemoved = false;
    if (!cards.contains(aCard))
    {
      return wasRemoved;
    }

    int oldIndex = cards.indexOf(aCard);
    cards.remove(oldIndex);
    if (aCard.indexOfPlayer(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aCard.removePlayer(this);
      if (!wasRemoved)
      {
        cards.add(oldIndex,aCard);
      }
    }
    return wasRemoved;
  }

  public String toString()
  {
    return super.toString() + "["+
            "playerState" + ":" + getPlayerState()+ "," +
            "moves" + ":" + getMoves()+ "," +
            "name" + ":" + getName()+ "]";
  }
}