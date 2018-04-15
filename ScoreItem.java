import javax.swing.*;
import java.awt.*;

public class ScoreItem extends JButton {

    String Bezeichnung;
    int score;
    boolean isFull;

    public void bezeichnungZeigen() {
        setText(Bezeichnung);
    }

    public void setBold() {
        setFont(new Font("Sansserif", Font.BOLD,20));
    }

}
