import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * A class for the visual implementation of Murder Mystery
 */

public class GameView extends JFrame implements WindowListener, ActionListener{

    ImageIcon map = new ImageIcon("Features/Assets/map/map.png");
    JPanel outerPanel;
    JTextArea textArea = new JTextArea();
    ArrayList<JRadioButton> buttonOptions = new ArrayList<>();
    String nameReturn="";

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
        outerPanel.removeAll();
        outerPanel.add(Map(GM));
        textArea.setEditable(false);
        JPanel rightPanel = new JPanel(new GridLayout(0, 1));
        rightPanel.add(textArea);
        JPanel cards = new JPanel(new GridLayout(1, 0));
        for(Card c : currentPlay.getHand()){
            cards.add(new JLabel(c.getCardDesign()));
            System.out.println(c.getCardDesign().getIconHeight());
        }
        rightPanel.add(cards);
        outerPanel.add(rightPanel);
    }

    /**
     * Provides an up to date map, complete with players
     * @param GM
     */
    private JLayeredPane Map(GameModel GM){
        JLayeredPane lp = new JLayeredPane();
        lp.setSize(480, 480);
        lp.add(new JLabel(map));
        return lp;
    }

    //Displays narrator prompts.
    public void narratorString(String s){
        textArea.setText(s);
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

    //Distinct from narratorString in that for these decisions, a pop up box is more sensible than a text fixture.
    public String narratorOptions(String s, ArrayList<String> options){
        return (String) JOptionPane.showInputDialog(null, s, "Narrator", JOptionPane.PLAIN_MESSAGE, null, options.toArray(), options.get(0));
    }

    public void displayGame(GameModel GM, Player p){
        makePanel(p, GM);
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
        for(JRadioButton jrb : buttonOptions){
            if(jrb.isSelected()){
                nameReturn=jrb.getName();
            }
        }
    }
}
