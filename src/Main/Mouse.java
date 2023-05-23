package Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.channels.ClosedSelectorException;

public class Mouse implements MouseListener {
    GameScreen gs;
    public int x, y;
    public boolean leftClick = false;
    public boolean rightClick = false;

    Mouse(GameScreen gs) {
        this.gs = gs;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        int button = e.getButton();
        if(button == MouseEvent.BUTTON1) {
            leftClick = true;
        }
        if(button == MouseEvent.BUTTON2) {
            rightClick = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        leftClick = false;
        rightClick = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public int checkDirection() {
        int rez = 0;

        int tmpX = gs.screenWidthHalf - x;
        int tmpY = gs.screenHeightHalf - y;
        int absX, absY;
        if(tmpX < 0)
            absX = -tmpX;
        else
            absX = tmpX;
        if(tmpY < 0)
            absY = -tmpY;
        else
            absY = tmpY;

        if(tmpX > 0 && tmpY > 0) {
            if(absX < absY)
                rez = 0;
            else
                rez = 3;
        } else if(tmpX > 0 && tmpY < 0) {
            if(absX > absY)
                rez = 3;
            else
                rez = 2;
        } else if(tmpX < 0 && tmpY > 0) {
            if(absX < absY)
                rez = 0;
            else
                rez = 1;
        } else if(tmpX < 0 && tmpY < 0) {
            if(absX > absY)
                rez = 1;
            else
                rez = 2;
        }

        return rez;
    }
}
