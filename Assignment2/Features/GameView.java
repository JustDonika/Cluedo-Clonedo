import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
import java.util.concurrent.atomic.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

/**
 * A class for the visual implementation of Murder Mystery
 */

public class GameView extends JFrame implements WindowListener, ActionListener{

    //Images
    private ImageIcon map = new ImageIcon("Features/Assets/map/map.png");
    private ImageIcon bert = new ImageIcon("Features/Assets/players/Bert.png");
    private ImageIcon percy = new ImageIcon("Features/Assets/players/Percy.png");
    private ImageIcon malina = new ImageIcon("Features/Assets/players/Malina.png");
    private ImageIcon lucilla = new ImageIcon("Features/Assets/players/Lucilla.png");
    private JMenuBar menuBar;
    private JMenu mainMenu;
    private JMenuItem newGameOption;
    private JMenuItem quitOption;
    //For character select
    JFrame f=new JFrame("Choose a character:");
    final String[] s = {""};

    JPanel outerPanel;
    JTextArea textArea = new JTextArea();
    ArrayList<JRadioButton> buttonOptions = new ArrayList<>();
    String nameReturn="";
    Tile selected = null;
    String direction = null;

    /**
     * Constructs new visual framework for game.
     */
    public GameView(){
        super("Murder Mystery; Many Morbid Merriments!");
        initMenu();
        {
            //======== mainMenu ========
            {
                mainMenu.setText("Menu");

                //---- newGameOption ----
                newGameOption.setText("New Game");
                mainMenu.add(newGameOption);

                //---- quitOption ----
                quitOption.setText("Quit");
                mainMenu.add(quitOption);
            }
            menuBar.add(mainMenu);
        }
        setJMenuBar(menuBar);
        outerPanel= new JPanel(new GridLayout(1, 0));
        makePanel();
        getContentPane().add(outerPanel);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
        pack();
        setVisible(true);
    }

    /**
     * Initializes menu components
     */
    private void initMenu() {
        menuBar = new JMenuBar();
        mainMenu = new JMenu();
        final GameView[] disposable = {this};
        newGameOption = new JMenuItem(new AbstractAction("New Game") {
            public void actionPerformed(ActionEvent e) {
                // Ask the user to confirm they wanted to do this
                int r = JOptionPane.showConfirmDialog(outerPanel,
                        new JLabel("Restart Murder Mystery (Currently breaks)?"), "Confirm restart",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (r == JOptionPane.YES_OPTION)
                    disposable[0].removeWindowListener(disposable[0].getWindowListeners()[0]);
                    disposable[0] = null;
                    MurderMystery.main(null);
            }
        });;
        quitOption = new JMenuItem(new AbstractAction("Quit") {
            public void actionPerformed(ActionEvent e) {
                // Ask the user to confirm they wanted to do this
                int r = JOptionPane.showConfirmDialog(outerPanel,
                        new JLabel("Exit Murder Mystery?"), "Confirm Exit",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (r == JOptionPane.YES_OPTION)
                    System.exit(0);

            }
        });
    }

    /**
     * Used for first initialization of panel
     */
    private void makePanel() {
        outerPanel.removeAll();
        outerPanel.add(new JLabel(map));
        textArea.setEditable(false);
        outerPanel.add(textArea, BorderLayout.SOUTH);
    }

    /**
     * Implements general screen for player, featuring cards, locations of players, etc.
     * @param currentPlay
     * @param GM
     */
    private void makePanel(Player currentPlay, GameModel GM) {
        //Refresh outerPanel
        outerPanel.removeAll();
        //Create map
        outerPanel.add(Map(GM));
        //Handle right side of screen
        textArea.setEditable(false);
        JPanel rightPanel = new JPanel(new GridLayout(0, 1));
        rightPanel.add(textArea);
        if(currentPlay!=null) {
            JPanel cards = new JPanel(new GridLayout(1, 0));
            for (Card c : currentPlay.getHand()) {
                cards.add(new JLabel(c.getCardDesign()));
            }
            rightPanel.add(cards);
        }
        outerPanel.add(rightPanel);
    }

    public String makeCharacterSelect(ArrayList<String> playerNames, boolean alreadyCreated){
        if(!alreadyCreated){
            f=new JFrame("Choose a character:");
        }
        ArrayList<JRadioButton> jrb = new ArrayList<>();
        for(String player : playerNames){
            jrb.add(new JRadioButton(new AbstractAction(player) {
                public void actionPerformed(ActionEvent e) {
                    s[0] = player;
                }
            }));
        }
        ButtonGroup bg=new ButtonGroup();
        int index=1;
        for(JRadioButton radio : jrb){
            radio.setBounds(75, 50*index, 100, 30);
            bg.add(radio);
            f.add(radio);
            index++;
        }
        f.setSize(300,300);
        f.setLayout(null);
        f.setVisible(true);
        if(s[0].equals("")){
            try {
                Thread.sleep(100);
            }
            catch(InterruptedException ie){
            }
            return makeCharacterSelect(playerNames, true);
        }
        String returnable = s[0];
        s[0]="";
        f.setBounds(0,0,0,0);
        f.setVisible(false);
        return returnable;
    }

    //Creates map
    public JLayeredPane Map(GameModel GM){
        JLayeredPane JLP = new JLayeredPane();
        JLP.setBounds(0, 0, 480, 480);
        JLabel mapImg = new JLabel(map);
        mapImg.setBounds(0, 0, 480, 480);
        JLP.add(mapImg, 1);
        if(GM==null){return JLP;}
        //Allows all to be visible in one estate.
        AtomicInteger index= new AtomicInteger();
        //With base map, add players
        for(Player p : GM.getOrder()){
            Tile t = p.getTile(0);
            ImageIcon toUse = new ImageIcon();
            if(p.getName().equals("Bert")){ toUse=bert;}else if(p.getName().equals("Percy")){toUse=percy;}else if(p.getName().equals("Malina")){toUse=malina;}else if(p.getName().equals("Lucilla")){toUse=lucilla;}
            JLabel playerImage = new JLabel(toUse);
            //Add player icon
            SwingUtilities.invokeLater(() -> {
            playerImage.setBounds(20*(t.getX()-1)+ index.get()%2*10, 20*(t.getY()-1)+index.get()/2*10, 10, 10);
            index.getAndIncrement();
            JLP.add(playerImage, 0);
            JLP.revalidate();
            });
        }
        //Prevents duplicate mouseListeners arriving and giving multiple inputs.
        for(MouseListener ML : getMouseListeners()){
            removeMouseListener(ML);
        }
        for(KeyListener KL : getKeyListeners()){
            removeKeyListener(KL);
        }
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                selected=tileAt(GM, e.getX()+10, e.getY()-30);
            }
        });
        addKeyListener((new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyChar()) {
                    case 'w':
                    case KeyEvent.VK_UP:
                        direction="UP";
                        break;
                    case 's':
                    case KeyEvent.VK_DOWN:
                        direction="DOWN";
                        break;
                    case 'a':
                    case KeyEvent.VK_LEFT:
                        direction="LEFT";
                        break;
                    case 'd' :
                    case KeyEvent.VK_RIGHT:
                        direction="RIGHT";
                        break;
                }
            }
        }));
        return JLP;
    }


    //Displays narrator prompts.
    public void narratorString(String s){
        textArea.setText(s);
    }

    //Append rather than replace text
    public void appendString(String s){
        textArea.setText(textArea.getText()+"\n"+s);
    }

    //Distinct from narratorString in that for these decisions, a pop up box is more sensible than a text fixture.
    public String narratorOptions(String s, ArrayList<String> options){
        if(options==null || options.isEmpty()){
            return null;
        }
        return (String) JOptionPane.showInputDialog(null, s, "Narrator", JOptionPane.PLAIN_MESSAGE, null, options.toArray(), options.get(0));
    }

    public void displayGame(GameModel GM, Player p){
        outerPanel.removeAll();
        makePanel(p, GM);
        outerPanel.revalidate();
        outerPanel.repaint();
    }

    //Finds tile at given location
    public Tile tileAt(GameModel GM, int x, int y){
        System.out.println(x);
        System.out.println(y);
        return GM.findTile(x/20, y/20);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        // Ask the user to confirm they wanted to do this
        int r = JOptionPane.showConfirmDialog(this,
                new JLabel("Exit Murder Mystery?"), "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (r == JOptionPane.YES_OPTION)
            System.exit(0);

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(JRadioButton jrb : buttonOptions) {
            if (jrb.isSelected()) {
                nameReturn = jrb.getName();
            }
        }
    }
}