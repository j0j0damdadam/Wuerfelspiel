public class PCspiel {

    Wuerfel[] Spielwürfel = new Wuerfel[5];
    ScoreItem[] Spielitems = new ScoreItem[6];
    int chosenNumber;
    int score = 0;

    public void setScoreItems() {
        for(int i=0;i<=5; i++) {
            Spielitems[i] = new ScoreItem();
        }
    }

    public void Spielphase1() {
        score = 0;
        int[] Count = new int[7];
        int maxCount = 0;
        int maxNumber = 0;
        for(int i=0;i<=4;i++) {
            Spielwürfel[i] = new Wuerfel();
            Spielwürfel[i].rollDice();
        }
        //Hier wird die Zahl herausgefunden, die am öftesten vorkommt:
        for(int i=0;i<=4;i++) {
            if(!Spielitems[Spielwürfel[i].diceNumber-1].isFull) {
                Count[Spielwürfel[i].diceNumber]++;
                if (maxCount < Count[Spielwürfel[i].diceNumber]) {
                    maxCount = Count[Spielwürfel[i].diceNumber];
                    maxNumber = Spielwürfel[i].diceNumber;
                }
            }
        }
        //Die Würfel mit der Zahl, die am häufigsten vorkommt, werden ausgewählt.
        for(int i=0;i<=4;i++) {
            if(Spielwürfel[i].diceNumber == maxNumber) Spielwürfel[i].isChosen = true;
        }
        chosenNumber = maxNumber;

    }

    public void Spielphase2() {
        int[] Count = new int[7];
        int maxCount = 0;
        int maxNumber = 0;
        for(int i=0;i<=4;i++) {
            if(!Spielwürfel[i].isChosen) Spielwürfel[i].rollDice();
        }
        // Hier wird wieder die Zahl herausgefunden, die am häufigsten vorkommt,
        // allerdings nur wenn diese Zahl bei den Spielitems noch nich belegt ist.
        for(int i=0;i<=4;i++) {
            if(!Spielitems[Spielwürfel[i].diceNumber-1].isFull) {
                Count[Spielwürfel[i].diceNumber]++;
                if (maxCount < Count[Spielwürfel[i].diceNumber]) {
                    maxCount = Count[Spielwürfel[i].diceNumber];
                    maxNumber = Spielwürfel[i].diceNumber;
                }
            }
        }
        for(int i=0;i<=4;i++) {
            Spielwürfel[i].isChosen = false;
        }
        // Die Würfel mit der Zahl, die am häufigsten vorkommt, werden ausgewählt.
        for(int i=0;i<=4;i++) {
            if(Spielwürfel[i].diceNumber == maxNumber) Spielwürfel[i].isChosen = true;
        }
        chosenNumber = maxNumber;
    }

    public void scoren() {
        //Der Score für die ausgewählte Zahl wird berechnet
        for(int i=0;i<=4;i++) {
            if(Spielwürfel[i].isChosen) score = score + Spielwürfel[i].diceNumber;
        }
        //Hier wird das ausgewählte Spielitem belegt
        if(!(chosenNumber == 0)) Spielitems[chosenNumber-1].isFull = true;
        // Für den Fall, dass keine Zahl ausgewählt wurde, weil alle gewürfelten Zahlen schon belegt sind.
        // In diesem Fall wird das erstbeste Spielitem belegt.
        else if(chosenNumber == 0)  {
            for(int i=0;i<=5;i++) {
                if(!Spielitems[i].isFull) Spielitems[i].isFull = true;
                chosenNumber = i+1;
                return;
            }
        }
    }

    public int getScore() {
        return score;
    }

    public int getChosenNumber() {
        return chosenNumber;
    }

    //Test-Main Methode, ob das Computerspiel funktioniert.
    public static void main(String args[]) {
        PCspiel pc = new PCspiel();
        pc.setScoreItems();
        for(int i=0; i<=5; i++) {
            pc.Spielphase1();
            pc.Spielphase2();
            pc.Spielphase2();
            pc.scoren();
            for (int j = 0; j <= 4; j++) System.out.print(pc.Spielwürfel[j].diceNumber + " ");
            System.out.println("Ausgewählte Zahl: " + pc.chosenNumber);
            System.out.println("Score: " + pc.score);
        }
    }



}
