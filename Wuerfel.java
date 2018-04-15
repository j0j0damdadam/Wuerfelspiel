import javax.swing.*;
import java.awt.*;

public class Wuerfel extends JButton {

    int diceNumber;
    boolean isChosen;

    public void changeBold() {
        if(isChosen) {
            setFont(new Font("Sansserif", Font.BOLD,20));
        }
        else {
            setFont(new Font("Sansserif", Font.PLAIN, 12));
        }
    }

    public int rollDice() {
        double randomNumber = Math.random();
        randomNumber = randomNumber * 6;
        randomNumber = randomNumber + 1;
        diceNumber = (int)randomNumber;
        setText("" + diceNumber);
        return diceNumber;
    }


}
