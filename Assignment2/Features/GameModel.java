import java.util.*;import java.awt.*;import java.awt.event.*;import java.util.List;import java.util.concurrent.*;import javax.swing.*;import javax.swing.border.*;public class GameModel {    //------------------------    // MEMBER VARIABLES    //------------------------    //Board Attributes    private static List<Card> answer = new ArrayList<>();    private static int numberPlayers;    private static boolean gameOver = false;    private static List<Player> order = new ArrayList<>();    //Board Associations    private static List<Estate> estates = new ArrayList<>();    private static List<Tile> tiles = new ArrayList<>();    private static List<Player> players = new ArrayList<>();    private static List<Card> cards = new ArrayList<>();    private static GameController controller = new GameController();    private static GameView GUI = new GameView();    private boolean isMoving = false;    //------------------------    // CONSTRUCTOR    //------------------------    public GameModel() {        answer = new ArrayList<Card>();        order = new ArrayList<Player>();        tiles = new ArrayList<Tile>();        estates = new ArrayList<Estate>();        players = new ArrayList<Player>();        cards = new ArrayList<Card>();    }    /**     * Sets up the order (Lucilla, Bert, Malina, Percy) for turns.     */    public void orderSetup() {        for (int i = 0; i < 4; i++) {            for (Player p : players) {                if (i == 0 && p.getName().equals("Lucilla")) {                    order.add(p);                } else if (i == 1 && p.getName().equals("Bert")) {                    order.add(p);                } else if (i == 2 && p.getName().equals("Malina")) {                    order.add(p);                } else if (i == 3 && p.getName().equals("Percy")) {                    order.add(p);                }            }        }    }    /**     * Implements cards, and assigns them artwork (courtesy of Caitlin)     */    public void createCards() {        ArrayList<String> cardNames = new ArrayList<>() {{            add("Bert");            add("Lucilla");            add("Malina");            add("Percy");            add("Broom");            add("iPad");            add("Knife");            add("Scissors");            add("Shovel");            add("Manic Manor");            add("Peril Palace");            add("Calamity Castle");            add("Haunted House");            add("Villa Celia");        }};        while (!cardNames.isEmpty()) {            cards.add(new Card(cardNames.get(0), new ImageIcon("Features/Assets/cards/" + cardNames.get(0) + ".png")));            cardNames.remove(0);        }        Card c = cards.get((int) (Math.random() * 4));        cards.remove(c);        answer.add(c);        c = cards.get((int) (3 + Math.random() * 5));        cards.remove(c);        answer.add(c);        c = cards.get((int) (7 + Math.random() * 5));        cards.remove(c);        answer.add(c);    }    /**     * Adds in tiles and estates     */    public void tilesAndEstates() {        //Tiles        for (int x = 1; x < 25; x++) {            for (int y = 1; y < 25; y++) {                //Grey areas are inaccessible                if ((x > 11 && x < 14 && (y > 5 && y < 8 || y > 17 && y < 20))/*top and bottom gray spots*/ || (y > 11 && y < 14 && (x > 5 && x < 8 || x > 17 && x < 20))/*Left and right gray spots*/) {                    Tile t = new Tile(x, y, true);                    tiles.add(t);                }                //Don't cover estates for now                else if (x > 2 && x < 8 && y > 2 && y < 8 || x > 2 && x < 8 && y > 17 && y < 23 || x > 17 && x < 23 && y > 2 && y < 8 || x > 17 && x < 23 && y > 17 && y < 23 || x > 9 && x < 16 && y > 10 && y < 15) {                } else {                    Tile t = new Tile(x, y, false);                    tiles.add(t);                }            }        }        //now we add weapons, then estates.        ArrayList<String> WeaponNames = new ArrayList<>() {{            add("Broom");            add("Scissors");            add("Knife");            add("Shovel");            add("iPad");        }};        Collections.shuffle(WeaponNames);        estates.add(new Estate(3, 7, 3, 7, false, "Haunted House", null));        estates.add(new Estate(3, 7, 18, 22, false, "Calamity Castle", null));        estates.add(new Estate(18, 22, 3, 7, false, "Manic Manor", null));        estates.add(new Estate(18, 22, 18, 22, false, "Peril Palace", null));        estates.add(new Estate(10, 15, 11, 14, false, "Villa Celia", null));        for (Estate e : estates) { //Initially put one in each            e.addWeapon(new Weapon(WeaponNames.get(0), e));            WeaponNames.remove(0);            tiles.add(e);        }    }    //-----------------------    // GETTERS    //-----------------------    public List<Card> getAnswer() {        return answer;    }    public int getNumberPlayers() {        return numberPlayers;    }    public boolean getGameOver() {        return gameOver;    }    public boolean getIsMoving() {        return isMoving;    }    public List<Player> getOrder() {        return order;    }    public List<Estate> getEstates() {        return estates;    }    public List<Tile> getTiles() {        return tiles;    }    public List<Player> getPlayers() {        return players;    }    public List<Card> getCards() {        return cards;    }    public GameController getController() {        return controller;    }    public GameView getGUI() {        return GUI;    }    public void main(String[] args) {        //TODO Replace with graphical equivalent        String s = GUI.narratorOptions("Welcome to Murder Madness, a game which is legally distinct from Cluedo!\nHow many players (3-4) wish to participate?", new ArrayList<String>() {{            add("Four");            add("Three");        }});        if (s.equals("Four")) {            numberPlayers = 4;        } else {            numberPlayers = 3;        }        //Now that the number of players is known, we can ask each player to decide on a character.        ArrayList<String> playerNames = new ArrayList<>() {{            add("Lucilla");            add("Bert");            add("Malina");            add("Percy");        }};        for (int i = 0; i < numberPlayers; i++) {            s = GUI.narratorOptions("Which character would you like to claim?", playerNames);            if (playerNames.remove(s)) {                players.add(new Player(false, 0, s));            } else {                i--;            }        }        for (String NonPlayer : playerNames) {            players.add(new Player(true, 0, NonPlayer));        }        orderSetup();        //Create and connect all tiles        tilesAndEstates();        connectTiles();        //Cards and Answer:        createCards();        //Now distribute the cards        Collections.shuffle(cards);        int startPoint = (int) (Math.random() * 4);        for (int i = startPoint; i < 11 + startPoint; i++) {            Card distributable = cards.get(0);            order.get(i % 4).addCard(distributable);            cards.remove(distributable);        }        //Randomly select first player.        int firstPlay = (int) (4 * Math.random());        while (order.get(firstPlay).getPlayerState()) { //Rerolls random number until valid player found.            firstPlay = (int) (4 * Math.random());        }        GUI.narratorString("Game is ready! First player is " + order.get(firstPlay).getName() + ", please pass the device to them.");        s = GUI.narratorOptions("Press continue when the correct player is ready", new ArrayList<String>() {{            add("Continue");        }});        GUI.addWindowListener(controller);        //now that everything has been established, start playerTurn loop.        if (s != new String()) {            playerTurn(firstPlay);        }    }    /**     * Sets up a turn for a given player     *     * @param turn     */    public void playerTurn(int turn) {        if (gameOver) {            return;        }        // Clear text        if (order.get(turn).getPlayerState()) {//If player has made a false accusation, or non-player is involved, they are skipped for everything except accusations.            playerTurn(getNextTurn(turn));        }        Player p = order.get(turn);        GUI.displayGame(this, order.get(turn));        GUI.narratorString("Options: Roll");        Scanner userInput = new Scanner(System.in);        String command = GUI.narratorOptions("Options: Roll", new ArrayList<String>() {{            add("Roll");        }});        //Roll dice        int die1 = (int) (1 + Math.random() * 6);        int die2 = (int) (1 + Math.random() * 6);        GUI.narratorString("Roll results: " + die1 + ", " + die2 + ", total: " + (die1 + die2) + ". Click on adjacent squares to move; click the estate you're in to accuse..");        p.setMoves(die1 + die2);        //Allow moves        String currMove = "";        while (p.getMoves() > 0) {            //TODO: Replace with arrow keys and WASD inputs if you can get GameController working            if (GUI.direction != null) {                currMove = GUI.direction;                GUI.direction=null;                //Get player tile info                Tile t = p.getTile(0);                Tile[] adjacent = new Tile[4];                Tile bestFit = null;                if (t instanceof Estate) {                    adjacent = ((Estate) t).getExitTiles();                } else {                    adjacent = t.getAdjacentTiles();                }                //EVERY MOVE OPTION:                if (currMove.equals("DOWN")) {                    for (Tile adj : adjacent) {                        if (bestFit == null || adj.getY() > bestFit.getY()) {                            bestFit = adj;                        }                    }                    if (bestFit.getY() <= t.getY()) {                    } else if (canEnter(bestFit, p)) {                        swapTiles(p, t, bestFit);                    }                } else if (currMove.equals("UP")) {                    for (Tile adj : adjacent) {                        if (bestFit == null || adj.getY() < bestFit.getY()) {                            bestFit = adj;                        }                    }                    if (bestFit.getY() >= t.getY()) {                    } else if (canEnter(bestFit, p)) {                        swapTiles(p, t, bestFit);                    }                } else if (currMove.equals("LEFT")) {                    for (Tile adj : adjacent) {                        if (bestFit == null || adj.getX() < bestFit.getX()) {                            bestFit = adj;                        }                    }                    if (bestFit.getX() >= t.getX()) {                    } else if (canEnter(bestFit, p)) {                        swapTiles(p, t, bestFit);                    }                } else if (currMove.equals("RIGHT")) {                    for (Tile adj : adjacent) {                        if (bestFit == null || adj.getX() > bestFit.getX()) {                            bestFit = adj;                        }                    }                    if (bestFit.getX() <= t.getX()) {                    } else if (canEnter(bestFit, p)) {                        swapTiles(p, t, bestFit);                    }                } else if (currMove.equals("ACCUSE") && p.getTile(0) instanceof Estate) {                    Accuse(p, turn);                    p.setMoves(0);                }                GUI.displayGame(this, p);                GUI.narratorString(p.getMoves() + " move(s) remain!");            }            //TODO: Implement pathfinding instead of only allowing directly adjacent clicks if we have time            if(GUI.selected!=null){                Tile t = p.getTile(0);                if(GUI.selected.equals(t) && t instanceof Estate){                    p.setMoves(0);                    Accuse(p, turn);                }                if(gameOver){                    return;                }                Tile[] adjacent;                if (t instanceof Estate) {                    adjacent = ((Estate) t).getExitTiles();                } else {                    adjacent = t.getAdjacentTiles();                }                Tile moveTo = GUI.selected;                GUI.selected = null;                 for(Tile maybe : adjacent){                     if(maybe.equals(moveTo) && canEnter(maybe, p)) {                         swapTiles(p, t, maybe);                     }                 }                GUI.displayGame(this, p);                GUI.narratorString(p.getMoves() + " move(s) remain! Click adjacent tiles to move, or the estate you're in to accuse!");            }        }        p.clearTravelled();        turn = getNextTurn(turn);        while (order.get(turn).getPlayerState()) { //Skips movement for non-players and those eliminated.            turn = getNextTurn(turn);        }        GUI.displayGame(this, null);        if (GUI.narratorOptions("It seems that " + p.getName() + "'s turn has ended! Now it's " + order.get(turn).getName() + "'s turn! Pass the device to them.\nPress continue when " + order.get(turn).getName() + " is ready.", new ArrayList<String>() {{            add("Continue");        }}) != new String()) {            playerTurn(turn);        }    }    public void swapTiles(Player moving, Tile original, Tile replacement){        original.removePlayer(moving);        moving.removeTile(original);        moving.addTile(replacement);        replacement.addPlayer(moving);        moving.setMoves(moving.getMoves() - 1);        moving.addTravelled(original);    }    //This is where a player can make a guess, and if they're confident, put their accusation to the test.    public void Accuse(Player p, int playerNo) {        String accusedPlayer = "";        while (!accusedPlayer.equals("Bert") && !accusedPlayer.equals("Percy") && !accusedPlayer.equals("Lucilla") && !accusedPlayer.equals("Malina")) {            accusedPlayer = GUI.narratorOptions("Gathering intel I see! Your guess is taking place at " + ((Estate) p.getTile(0)).getName() + ", what dastardly villain do you think did this?", new ArrayList<String>() {{                add("Percy");                add("Lucilla");                add("Malina");                add("Bert");            }});        }        for (Player suspect : order) {            if (suspect.getName().equals(accusedPlayer)) {                Tile original = suspect.getTile(0);                Tile playerIn = p.getTile(0);                original.removePlayer(suspect);                suspect.removeTile(original);                suspect.addTile(playerIn);                playerIn.addPlayer(suspect);            }        }        String suspectWeapon = "";        while (!suspectWeapon.equals("iPad") && !suspectWeapon.equals("Broom") && !suspectWeapon.equals("Shovel") && !suspectWeapon.equals("Scissors") && !suspectWeapon.equals("Knife")) {            suspectWeapon = GUI.narratorOptions("You're probably right, frankly even if they didn't commit this murder they doubtless committed another.\nI can definitely see them bashing... stabbing... What killed the victim again?", new ArrayList<String>() {{                add("Knife");                add("iPad");                add("Broom");                add("Shovel");                add("Scissors");            }});        }        String guessOrAccuse = GUI.narratorOptions("What a way to go... I can only imagine the suffering that poor... whatsisface, that victim guy, endured.\nBut are we sure that's how things went? If you aren't certain, maybe ask the others for insight.", new ArrayList<String>() {{            add("Accuse");            add("Guess");        }});        if (guessOrAccuse.equals("Guess")) {            guessLoop(accusedPlayer, suspectWeapon, p, getNextTurn(playerNo));        } else {            if (accusedPlayer.equals(answer.get(0).getName()) && suspectWeapon.equals(answer.get(1).getName()) && ((Estate) p.getTile(0)).getName().equals(answer.get(2).getName())) {                GUI.narratorOptions("You absolute Sherlock you! You solved the case! Cracked it wide open!\nWish we hadn't sent the culprit in to investigate their own crime, but we'll catch them later!\nGood job!", new ArrayList<String>() {{                    add("Wow I'm amazing!");                }});                gameOver = true;            } else {                GUI.narratorOptions("You are impressively wrong! My God, this is why we shouldn't use suspects as detectives.\nLeave it to the professionals I guess. Maybe they'll work out it was actually "                                + answer.get(0).getName() + " with the " + answer.get(1).getName() + " in the " + answer.get(2).getName()                                + "\nWhat, you think we should just capture the murderer? Without getting you to solve it? No thanks." +                                "\nJust stay here and we'll call you to other rooms when you're needed. You, beyond the fourth wall, go home, you're done.",                        new ArrayList<String>() {{                            add("Well that was rude");                        }});                p.setPlayerState(true);                if (order.get(0).getPlayerState() && order.get(1).getPlayerState() && order.get(2).getPlayerState() && order.get(3).getPlayerState()) {                    GUI.narratorString("\nWait they're already done? Nobody got it? Damn, should've given you guys more cards. Guess you all lose now.");                    gameOver = true;                }            }        }    }    //Go through players and find out if they're the suspect.    public void guessLoop(String accused, String weapon, Player guesser, int turn) {        String estate = ((Estate) guesser.getTile(0)).getName();        if(turn==order.indexOf(guesser)){            //No card was presented to disprove it.            return;        }        Player returnCards=order.get(turn);        GUI.displayGame(this, null);        GUI.narratorOptions("Pass the device to "+returnCards.getName()+", or back to "+guesser.getName()+" if that player is out of the game.\nWhen the appropriate player has the device, press continue", new ArrayList<String>(){{add("Continue");}});        //Correct player now has the device        ArrayList<Card> returnable = new ArrayList<>();        for(Card c : returnCards.getHand()){            if(c.getName().equals(estate) || c.getName().equals(accused) || c.getName().equals(weapon)){                returnable.add(c);            }        }        //If AI or eliminated player, only the guesser should see        if(returnCards.getPlayerState()){            if(returnable.size()==0){                GUI.narratorOptions("It seems "+returnCards.getName()+" had no refuting evidence!", new ArrayList<String>(){{add("Continue");}});            }            else{                GUI.narratorOptions("Oooo, "+returnCards.getName()+" has some intel!\nThey had "+returnable.get((int) (Math.random()*returnable.size())).getName()+" to refute with!", new ArrayList<String>(){{add("Continue");}});                return;            }        }        //If non-eliminated player, they see and then select evidence for the guesses        else{            if(returnable.size()==0){                GUI.narratorOptions("You have no refuting evidence.", new ArrayList<String>(){{add("Continue");}});            }            else{                String refutation =GUI.narratorOptions("You have at least one piece of refuting evidence!\n Select which you want to show the guesser.", new ArrayList<String>(){{for(Card c: returnable){add(c.getName());}}});                GUI.narratorOptions("Pass the device to "+returnCards.getName()+", and then press continue", new ArrayList<String>(){{add("Continue");}});                GUI.narratorOptions("Oooo, "+returnCards.getName()+" has some intel!\nThey had "+refutation+" to refute with!", new ArrayList<String>(){{add("Continue");}});                return;            }        }        turn=getNextTurn(turn);        guessLoop(accused, weapon, guesser, turn);    }    public boolean canEnter(Tile t, Player p) {        if ((t.getPlayers().isEmpty() || t instanceof Estate) && !t.getImpassible() && !p.getTravelled().contains(t)) {            return true;        }        return false;    }    //This method connects all adjacent passable tiles, and estates.    public void connectTiles() {        int x = 0;        int y = 0;        for (Tile t : tiles) {            if (!(t instanceof Estate) && !t.getImpassible()) {                x = t.getX();                y = t.getY();                for (Tile comparisonT : tiles) {                    if (!(comparisonT instanceof Estate) && !t.getImpassible()) {                        if (Math.abs(comparisonT.getX() - x) + Math.abs(comparisonT.getY() - y) == 1) {                            t.addAdjacentTile(comparisonT);                            comparisonT.addAdjacentTile(t);                        }                    }                }            } else if (t instanceof Estate) {                Estate e = (Estate) t;                Tile connect = null;                String name = e.getName();                if (name.equals("Haunted House")) {                    connect = findTile(8, 4);                    e.addExitTile(connect);                    connect.addAdjacentTile(e);                    connect = findTile(6, 8);                    e.addExitTile(connect);                    connect.addAdjacentTile(e);                }                if (name.equals("Calamity Castle")) {                    connect = findTile(4, 17);                    e.addExitTile(connect);                    connect.addAdjacentTile(e);                    connect = findTile(8, 19);                    e.addExitTile(connect);                    connect.addAdjacentTile(e);                }                if (name.equals("Manic Manor")) {                    connect = findTile(17, 6);                    e.addExitTile(connect);                    connect.addAdjacentTile(e);                    connect = findTile(21, 8);                    e.addExitTile(connect);                    connect.addAdjacentTile(e);                }                if (name.equals("Peril Palace")) {                    connect = findTile(17, 21);                    e.addExitTile(connect);                    connect.addAdjacentTile(e);                    connect = findTile(19, 17);                    e.addExitTile(connect);                    connect.addAdjacentTile(e);                }                if (name.equals("Villa Celia")) {                    connect = findTile(13, 10);                    e.addExitTile(connect);                    connect.addAdjacentTile(e);                    connect = findTile(9, 13);                    e.addExitTile(connect);                    connect.addAdjacentTile(e);                    connect = findTile(12, 15);                    e.addExitTile(connect);                    connect.addAdjacentTile(e);                    connect = findTile(16, 12);                    e.addExitTile(connect);                    connect.addAdjacentTile(e);                }            }        }        for (Player p : players) {            if (p.getName().equals("Lucilla")) {                findTile(12, 2).addPlayer(p);                p.addTile(findTile(12, 2));            }            if (p.getName().equals("Bert")) {                findTile(2, 10).addPlayer(p);                p.addTile(findTile(2, 10));            }            if (p.getName().equals("Malina")) {                findTile(10, 23).addPlayer(p);                p.addTile(findTile(10, 23));            }            if (p.getName().equals("Percy")) {                findTile(23, 15).addPlayer(p);                p.addTile(findTile(23, 15));            }        }    }    public Tile findTile(int x, int y) {        for (Tile t : tiles) {            if (t.getX() == x && t.getY() == y) {                return t;            }            if(t instanceof Estate){                Estate e = (Estate) t;                if(x>=e.getX() && x<=e.getX2() && y>=e.getY() && y<=e.getY2()){                    return t;                }            }        }        return null;    }    public int getNextTurn(int currTurn) {        if (currTurn == 3) {            return 0;        }        return currTurn + 1;    }}