package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keys implements KeyListener{
    public boolean wPress, aPress, sPress, dPress, ePress, tPress, pPress, shPress = false, escPress = false, press1, press2, press3, press4;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) { wPress = true; }
        if (code == KeyEvent.VK_A) { aPress = true; }
        if (code == KeyEvent.VK_S) { sPress = true; }
        if (code == KeyEvent.VK_D) { dPress = true; }
        if (code == KeyEvent.VK_E) { ePress = true; }
        if (code == KeyEvent.VK_T) { tPress = true; }
        if (code == KeyEvent.VK_P) { pPress = true; }
        if (code == KeyEvent.VK_SHIFT) { shPress = true; }
        if (code == KeyEvent.VK_ESCAPE) { escPress = true; }
        if (code == KeyEvent.VK_L) { escPress = true; }
        if (code == KeyEvent.VK_1) { press1 = true; }
        if (code == KeyEvent.VK_2) { press2 = true; }
        if (code == KeyEvent.VK_3) { press3 = true; }
        if (code == KeyEvent.VK_4) { press4 = true; }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) { wPress = false; }
        if (code == KeyEvent.VK_A) { aPress = false; }
        if (code == KeyEvent.VK_S) { sPress = false; }
        if (code == KeyEvent.VK_D) { dPress = false; }
        if (code == KeyEvent.VK_P) { pPress = false; }
        if (code == KeyEvent.VK_SHIFT) { shPress = false; }
        if (code == KeyEvent.VK_1) { press1 = false; }
        if (code == KeyEvent.VK_2) { press2 = false; }
        if (code == KeyEvent.VK_3) { press3 = false; }
        if (code == KeyEvent.VK_4) { press4 = false; }
    }
}

//class Mouse implements MouseListener {
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
//        if(e.)
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//
//    }
//}
