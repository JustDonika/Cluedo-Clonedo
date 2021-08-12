import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
import java.util.concurrent.atomic.*;

/**
 * A class for the visual implementation of Murder Mystery
 */

public class GameView extends JFrame implements WindowListener, ActionListener{

    //Images
    ImageIcon map = new ImageIcon("Features/Assets/map/map.png");
    ImageIcon bert = new ImageIcon("Features/Assets/players/Bert.png");
    ImageIcon percy = new ImageIcon("Features/Assets/players/Percy.png");
    ImageIcon malina = new ImageIcon("Features/Assets/players/Malina.png");
    ImageIcon lucilla = new ImageIcon("Features/Assets/players/Lucilla.png");


    JPanel outerPanel;
    JTextArea textArea = new JTextArea();
    ArrayList<JRadioButton> buttonOptions = new ArrayList<>();
    String nameReturn="";
    Tile selected = null;
    String direction = null;

    public GameView(){
        super("Murder Mystery; Many Morbid Merriments!");
        outerPanel= new JPanel(new GridLayout(1, 0));
        makePanel();
        getContentPane().add(outerPanel);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
        pack();
        setVisible(true);
    }

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

    //Creates map
    public JLayeredPane Map(GameModel GM){
        JLayeredPane JLP = new JLayeredPane();
        JLP.setBounds(0, 0, 480, 480);
        JLabel mapImg = new JLabel(map);
        mapImg.setBounds(0, 0, 480, 480);
        JLP.add(mapImg, 1);
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
                selected=tileAt(GM, e.getX()+10, e.getY()-10);
            }
        });
        addKeyListener((new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyChar());
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




    //Caused issues but if you have time before due date the brief wants RadioButtons for character select.
    //public String characterSelect(String s, ArrayList<String> options){
    //    int y = 50;
    //    for(String buttonName : options){
    //        JRadioButton button = new JRadioButton(buttonName);
    //        button.setBounds(100, y, 100, 30);
    //        buttonOptions.add(button);
    //        y+=50;
    //    }
    //    ButtonGroup bg = new ButtonGroup();
    //    for(JRadioButton jrb : buttonOptions){
    //        bg.add(jrb);
    //    }
    //    JButton b = new JButton("Confirm");
    //    b.setBounds(100, y, 70, 30);
    //    b.addActionListener(this);
    //    for(JRadioButton jrb : buttonOptions) {
    //        add(jrb);
    //    }
    //    add(b);
    //    setSize(300, 300);
    //    while(nameReturn.equals("")) {
    //        try {
    //            wait(10);
    //        }
    //        catch(InterruptedException ie){
    //        }
    //    }
    //    String returnable = nameReturn;
    //    nameReturn="";
    //    return returnable;
    //}
}
