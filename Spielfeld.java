import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Spielfeld extends JFrame {


    public Spielfeld() {
        initComponents();
        pack();
        pc.setScoreItems();
    }

    private void initComponents() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500,500));
        setTitle("Muttergeficktes Kniffel-Spiel");

        panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        Start = new JButton();
        Start.addActionListener(bl);
        Start.setText("Wuerfeln");
        Anzeige = new JTextPane();
        Anzeige.setText("Jetzt gehts los.");
        Anzeige.setFont(new Font("Sansserif", Font.PLAIN, 20));
        panel.add(Anzeige);
        panel.add(Start);
        getContentPane().add(panel, BorderLayout.CENTER);

        Wuerfelleiste = new JPanel();
        Wuerfelleiste.setLayout(new GridLayout(1,5));
        spielWuerfel = new Wuerfel[5];
        for(int i=0; i<=4; i++) {
            Wuerfel w = new Wuerfel();
            spielWuerfel[i] = w;
            w.setPreferredSize(new Dimension(50,50));
            w.addActionListener(bl);
            Wuerfelleiste.add(w);
        }
        getContentPane().add(Wuerfelleiste, BorderLayout.PAGE_START);

        Scoreleiste = new JPanel();
        Scoreleiste.setLayout(new GridLayout(6,1));
        spielItems = new ScoreItem[6];
        for(int i=0; i<=5; i++) {
            ScoreItem s = new ScoreItem();
            spielItems[i] = s;
            s.setPreferredSize(new Dimension(100,50));
            Scoreleiste.add(s);
            s.Bezeichnung = (i+1)+"er";
            s.bezeichnungZeigen();
            s.addActionListener(bl);
        }
        getContentPane().add(Scoreleiste, BorderLayout.LINE_START);

        ScoreleistePC = new JPanel();
        ScoreleistePC.setLayout(new GridLayout(6,1));
        spielItemsPC = new ScoreItem[6];
        for(int i=0; i<=5; i++) {
            ScoreItem s = new ScoreItem();
            spielItemsPC[i] = s;
            s.setPreferredSize(new Dimension(100,50));
            ScoreleistePC.add(s);
            s.Bezeichnung = (i+1)+"er";
            s.bezeichnungZeigen();
            s.addActionListener(bl);
        }
        getContentPane().add(ScoreleistePC, BorderLayout.LINE_END);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Spielfeld spielfeld = new Spielfeld();
                spielfeld.setVisible(true);
            }
        });
    }

    public class buttonListener implements java.awt.event.ActionListener {
        public void actionPerformed( java.awt.event.ActionEvent evt ) {

            if(Spielzug == 7) {
                if(evt.getSource() == Start) {
                    //Hier Fenster schließen einfügen
                }
            }

            //Ende des Spiels:
            if(Spielzug == 6) {
                for(int i=0;i<=5;i++) score = score+spielItems[i].score;
                for(int i=0;i<=5;i++) PCscore = PCscore+ spielItemsPC[i].score;

                if(score>PCscore) Anzeige.setText("Gewonnen! Ihr Ergebnis: " + score + " Punkte." +
                        "Das Ergebnis des Gegners: " + PCscore + ".");
                else if(score==PCscore) Anzeige.setText("Unentschieden! Ihr Ergebnis: " + score + " Punkte." +
                        "Das Ergebnis des Gegners: " + PCscore + ".");
                else if(score<PCscore) Anzeige.setText("Verloren! Ihr Ergebnis: " + score + " Punkte." +
                        "Das Ergebnis des Gegners: " + PCscore + " Punkte.");
                Start.setText("Beenden.");
                Spielzug++;
                return;
            }

            // Erstes Mal würfeln:
            if (Spielphase == 1) {
                if(evt.getSource() == Start) {
                    for (int i = 0; i <= 4; i++) {
                        spielWuerfel[i].rollDice();
                    }
                    Anzeige.setText("1x gewürfelt. Welche Würfel wollen Sie behalten?");
                    Spielphase++;
                    return;
                }
            }

            // Zweites und drittes mal würfeln:
            if (Spielphase == 2 || Spielphase == 3) {
                for (int i = 0; i <= 4; i++) {
                    if(evt.getSource() == spielWuerfel[i]) {
                        spielWuerfel[i].isChosen = !spielWuerfel[i].isChosen;
                        spielWuerfel[i].changeBold();
                    }
                }
                if(evt.getSource() == Start) {
                    for (int i = 0; i <= 4; i++) {
                        if(!spielWuerfel[i].isChosen) {
                            spielWuerfel[i].rollDice();
                        }
                    }
                    Anzeige.setText(Spielphase + "x gewürfelt. Welche Würfel wollen Sie nun behalten?");
                    Spielphase++;
                    return;
                }


            }

            // Zahlen auswählen und scoren
            if (Spielphase == 4) {
                for (int i = 0; i <= 4; i++) {
                    if(evt.getSource() == spielWuerfel[i]) {
                        spielWuerfel[i].isChosen = !spielWuerfel[i].isChosen;
                        spielWuerfel[i].changeBold();
                    }
                }

                for (int j = 0; j <= 5; j++) {
                    if(evt.getSource() == spielItems[j]) {
                        if(spielItems[j].isFull) {
                            Anzeige.setText("Dieses Feld ist bereits belegt.");
                            return;
                        }
                        for (int i = 0; i <= 4; i++) {
                            if (spielWuerfel[i].isChosen) {
                                if (spielWuerfel[i].diceNumber != j+1) {
                                    spielItems[j].score = 0;
                                    Anzeige.setText("Das ist nich möglich. Wählen Sie andere Würfel oder ein anderes Feld auf der linken Seite.");
                                    return;
                                }
                                else if (spielWuerfel[i].diceNumber == j+1) {
                                    spielItems[j].score = spielItems[j].score + spielWuerfel[i].diceNumber;
                                }
                            }
                        }
                        spielItems[j].Bezeichnung = spielItems[j].Bezeichnung + ": " + spielItems[j].score;
                        spielItems[j].isFull = true;
                        spielItems[j].bezeichnungZeigen();
                        for (int i = 0; i <= 4; i++) {
                            spielWuerfel[i].setText(" ");
                            spielWuerfel[i].isChosen = false;
                            spielWuerfel[i].changeBold();
                        }


                        // PC-Zug beginnt hier

                        pc.Spielphase1();
                        pc.Spielphase2();
                        pc.Spielphase2();
                        pc.scoren();
                        spielItemsPC[pc.getChosenNumber()-1].score = pc.getScore();
                        spielItemsPC[pc.getChosenNumber()-1].Bezeichnung = spielItemsPC[pc.getChosenNumber()-1].Bezeichnung + ": " + spielItemsPC[pc.getChosenNumber()-1].score;
                        spielItemsPC[pc.getChosenNumber()-1].isFull = true;
                        spielItemsPC[pc.getChosenNumber()-1].bezeichnungZeigen();
                        Anzeige.setText("Der Gegner hat " + (pc.getScore()/pc.getChosenNumber()) + " " + pc.getChosenNumber() + "er gewürfelt. " +
                                "Sie sind an der Reihe!");

                        //PC-Zug ist hier zu Ende


                        Spielzug++;
                        Spielphase = 1;

                        return;
                    }
                }
            }
        }
    }

    private JPanel panel;
    private JPanel Wuerfelleiste;
    private JPanel Scoreleiste;
    private JPanel ScoreleistePC;
    private JButton Start;
    private JTextPane Anzeige;
    private Wuerfel[] spielWuerfel;
    private ScoreItem[] spielItems;
    private ScoreItem[] spielItemsPC;
    // private JButton Start;
    buttonListener bl = new buttonListener();
    private int Spielphase = 1;
    private int Spielzug = 0;
    private int score = 0;
    private int PCscore = 0;
    private PCspiel pc = new PCspiel();

}
