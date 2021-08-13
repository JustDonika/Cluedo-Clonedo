/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/


import javax.swing.*;
import java.util.*;

// line 24 "model.ump"
// line 94 "model.ump"
public class Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Card Attributes
  private String name;
  private ImageIcon cardDesign;

  //Card Associations
  private List<Player> players;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Card(String aName, ImageIcon cardDesign)
  {
    name = aName;
    players = new ArrayList<Player>();
    this.cardDesign=cardDesign;
  }

  //------------------------
  // INTERFACE
  //------------------------


  public ImageIcon getCardDesign(){
    return cardDesign;
  }

  public String getName()
  {
    return name;
  }

  public int numberOfPlayers()
  {
    return players.size();
  }

  public boolean hasPlayers()
  {
    return players.size() > 0;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    return players.indexOf(aPlayer);
  }
  /* Code from template association_GetMany */

  public static int minimumNumberOfPlayers()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    players.add(aPlayer);
    if (aPlayer.indexOfCard(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aPlayer.addCard(this);
      if (!wasAdded)
      {
        players.remove(aPlayer);
      }
    }
    return wasAdded;
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
    if (aPlayer.indexOfCard(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aPlayer.removeCard(this);
      if (!wasRemoved)
      {
        players.add(oldIndex,aPlayer);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }

  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]";
  }
}