import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Tests {
    @Test //Tests that displayGame method can handle null inputs
    public void test1(){
        GameView GV = new GameView();
        GV.displayGame(null, null);
    }

    @Test //Tests that all cards input correctly
    public void test2() {
        GameModel GM = new GameModel();
        GM.createCards();
        int cardCount=0;
        for(Card c : GM.getCards()){
            cardCount++;
            if(c.getCardDesign().getIconWidth()==0){
                fail();
            }
        }
        for(Card c : GM.getAnswer()){
            cardCount++;
            if(c.getCardDesign().getIconWidth()==0){
                fail();
            }
        }
        if(cardCount!=14){fail();}
    }

    @Test
    public void test3() {//Tests that tile is selected even for extreme mouse inputs. Top left is 20, 20, top right is 500, 500; tests top left corner
        GameModel GM = new GameModel();
        GM.tilesAndEstates();
        GM.connectTiles();
        GameView GV = GM.getGUI();
        if(GV.tileAt(GM, 20, 20)==null){
            fail();
        }
    }

    @Test
    public void test4() {//Tests that tile is selected even for extreme mouse inputs. Top left is 20, 20, bottom right is 499, 499; tests bottom right corner.
        GameModel GM = new GameModel();
        GM.tilesAndEstates();
        GM.connectTiles();
        GameView GV = GM.getGUI();
        if(GV.tileAt(GM, 499, 499)==null){
            fail();
        }
    }

    @Test
    public void test5() {//Tests that tile is selected even for extreme mouse inputs. Top left is 20, 20, bottom right is 499, 499; tests bottom left corner
        GameModel GM = new GameModel();
        GM.tilesAndEstates();
        GM.connectTiles();
        GameView GV = GM.getGUI();
        if(GV.tileAt(GM, 20, 499)==null){
            fail();
        }
    }

    @Test
    public void test6() {//Tests that tile is selected even for extreme mouse inputs. Top left is 20, 20, bottom right is 499, 499; tests top right corner
        GameModel GM = new GameModel();
        GM.tilesAndEstates();
        GM.connectTiles();
        GameView GV = GM.getGUI();
        if(GV.tileAt(GM, 499, 20)==null){
            fail();
        }
    }

    @Test
    public void test7() {//Tests that incorrect input produces a null tile
        GameModel GM = new GameModel();
        GM.tilesAndEstates();
        GM.connectTiles();
        GameView GV = GM.getGUI();
        if(GV.tileAt(GM, 500, 499)!=null){
            fail();
        }
    }

    @Test
    public void test8() {//Tests that null narrator string does not cause error
        GameModel GM = new GameModel();
        GM.tilesAndEstates();
        GM.connectTiles();
        GameView GV = GM.getGUI();
        GV.displayGame(GM, null);
        GV.narratorString(null);
    }

    @Test
    public void test9() {//Tests that narratorOptions can handle both null string and additionally an empty ArrayList.
        GameModel GM = new GameModel();
        GM.tilesAndEstates();
        GM.connectTiles();
        GameView GV = GM.getGUI();
        GV.displayGame(GM, null);
        GV.narratorOptions(null, new ArrayList<String>());
    }

    @Test
    public void test10() {//Tests that narratorOptions can handle both null string and additionally null ArrayList.
        GameModel GM = new GameModel();
        GM.tilesAndEstates();
        GM.connectTiles();
        GameView GV = GM.getGUI();
        GV.displayGame(GM, null);
        GV.narratorOptions(null, null);
    }


}