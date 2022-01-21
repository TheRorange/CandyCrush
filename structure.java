import javax.swing.*;
import java.awt.*;
import java.util.Map;

public interface structure {
    public void personName() throws Exception;

    public static void frame(JPanel jPanel) throws Exception {

    }

    public static boolean hintSwap(int x, int changeX, int y, int changeY) throws Exception {
        return false;
    }

    public static void hintMessage(int i1, int j1, int i2, int j2) throws Exception {

    }

    public static void loserMessage() throws Exception {

    }

    public void picsSetter (Map<String, ImageIcon> pics) throws Exception;
    public void paintComponent(Graphics g);
    public void swap() throws Exception;

    public static void saveScore() throws Exception {

    }

    public void winnerMessage() throws Exception;
}
