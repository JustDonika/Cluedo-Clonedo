import java.awt.event.*;

public class GameController implements MouseListener, KeyListener {
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
        int keyCode = e.getKeyCode();
        switch(keyCode) {
            case KeyEvent.VK_UP:
                move="UP";
                break;
            case KeyEvent.VK_DOWN:
                move="DOWN";
                break;
            case KeyEvent.VK_LEFT:
                move="LEFT";
                break;
            case KeyEvent.VK_RIGHT :
                move="RIGHT";
                break;
            case KeyEvent.VK_W:
                move="UP";
                break;
            case KeyEvent.VK_S:
                move="DOWN";
                break;
            case KeyEvent.VK_A:
                move="LEFT";
                break;
            case KeyEvent.VK_D:
                move="RIGHT";
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
