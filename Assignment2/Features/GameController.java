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

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyChar()) {
            case 'w':
                move="UP";
                break;
            case 's':
                move="DOWN";
                break;
            case 'a':
                move="LEFT";
                break;
            case 'd' :
                move="RIGHT";
                break;
            case KeyEvent.VK_UP:
                move="UP";
                System.out.println("HI");
                break;
            case KeyEvent.VK_DOWN:
                move="DOWN";
                break;
            case KeyEvent.VK_LEFT:
                move="LEFT";
                break;
            case KeyEvent.VK_RIGHT:
                move="RIGHT";
                break;
        }
        System.out.println(e.getKeyChar());
    }
}
