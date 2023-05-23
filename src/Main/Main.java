package Main;

import javax.swing.JFrame;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Miriri");

        GameScreen gs = GameScreen.getInstance();
        window.add(gs);
        window.pack();

        window.setVisible(true);
    }
}
