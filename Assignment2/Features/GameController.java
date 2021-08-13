import java.awt.event.*;


public class GameController implements MouseListener, KeyListener, WindowListener {
    int x=99999;
    int y=99999;
    String move = "";

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        x=e.getX();
        y=e.getY();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }


    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyChar()) {
            case 'w':
            case KeyEvent.VK_UP:
                move="UP";
                break;
            case 's':
            case KeyEvent.VK_DOWN:
                move="DOWN";
                break;
            case 'a':
            case KeyEvent.VK_LEFT:
                move="LEFT";
                break;
            case 'd' :
            case KeyEvent.VK_RIGHT:
                move="RIGHT";
                break;
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

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
}
