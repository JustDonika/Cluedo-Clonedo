/**
 * Activator class. Separation from GameModel allows for non-static behaviour.
 */
public class MurderMystery {
    public static void main(String args[]){
        GameModel GM = new GameModel();
        GM.main(null);
    }
}
