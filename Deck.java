import java.util.ArrayList;
import java.util.Scanner;


public class Deck { // start of class

    Scanner scan = new Scanner(System.in);
    ArrayList <Card> Deck = new ArrayList<Card>();
    ArrayList <Card> playerDeck = new ArrayList<Card>();
    ArrayList <Card> cpuDeck = new ArrayList<Card>();
    ArrayList <Card> drawDeck = new ArrayList<Card>();
    ArrayList <String> order = new ArrayList<String>();
    Boolean gameIsStillGoingOn = true;
 
    public Deck()
    {
        String [] color = {"Red", "Yellow", "Blue", "Green"};
		int [] num = {0,1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9,10,10,11,11,12,12};		
		String [] wild = {"Wild", "Draw 4 Wild"};
		int [] nums = {13,14};
		int x = 0;
        for(int i = 0; i < 108; i++)
        {
            if(i < 100)
            {
                Deck.add(new Card(color[i/25],num[i%25]));

                } else 
                    {
                     Deck.add(new Card(wild[x/4],nums[x/4])); 
                     x++;
                    }
        }
    }

    public void startGame()
    {
        shuffle();
        dealDeck();
        getHand();
        while(gameIsStillGoingOn)
        {
        askPlayer();
        header();
        checkWinner();
        if(gameIsStillGoingOn == false)
        {
            break;
        }
        askCPU();
        getHand();
        header();
        checkWinner();
        if(gameIsStillGoingOn == false)
        {
            break;
        }
        }
    }

   
    public void dealDeck()
    {
        for(int i = 0; i < 14; i++){
            if((i % 2) == 0)
            {
                cpuDeck.add(Deck.get(0));
                Deck.remove(0);
            }else
            {
                playerDeck.add(Deck.get(0));
                Deck.remove(0);
            }
        }
                drawDeck.add(Deck.get(0));
                Deck.remove(0);
                Deck.add(Deck.get(0));
                order.add("FIRST CARD"); 
                
    }

    public void checkWinner()
    {
        if(playerDeck.size() == 0)
        {
            gameIsStillGoingOn = false;
            System.out.println("\nPLAYER WINNER");
        }
        else if(cpuDeck.size() == 0)
        {
            gameIsStillGoingOn = false;
            System.out.println("\nCPU WINNER");
        }
    }

    public void drawCard(ArrayList <Card> d)
    {
        
        d.add(Deck.get(0));
        Deck.remove(0);

        // ADD RULE THAT STATES IF THE DRAWN CARD IS PLAYABLE OR NOT

        if(d.get(d.size() - 1).getSuitName().equals(drawDeck.get(drawDeck.size() - 1).getSuitName()) || d.get(d.size() - 1).getSuitVal() == drawDeck.get(drawDeck.size() - 1).getSuitVal() || d.get(d.size() - 1).getSuitVal() > 12)
        {
            drawDeck.add(d.get(d.size() - 1));
            Deck.add(d.get(d.size() - 1));
            d.remove(d.size() - 1);
            if(d.size() == cpuDeck.size())
            {
                 order.add("CPU - DRAW");
            }
            else
            {
                 order.add("PLAYER - DRAW");
            }

            if(drawDeck.get(drawDeck.size() -1).getSuitVal() == 13)
        {
            if(d.size() == cpuDeck.size())
            {
                cpuWildCard();
            }
            else
            {
                wildCard();
            }        
        }
        if(drawDeck.get(drawDeck.size() - 1).getSuitVal() == 14)
        {
            if(d.size() == cpuDeck.size())
            {
                cpu4WildCard();
            }
            else
            {
                draw4Card(cpuDeck);
            }        
        }
        }

           else
        {
            drawCard(d);
        }


    }

    public void cpu4WildCard()
    {
        String colour = "";

        int randChoice = (int) (Math.random() * 4);

        if(randChoice == 0)
        {
            colour += "Red";
        }
        else if(randChoice == 1)
        {
            colour += "Blue";
        }
        else if(randChoice == 2)
        {
            colour += "Green";
        }
        else if(randChoice == 3)
        {
            colour += "Yellow";
        }

        for(int i = 0; i < 4; i++)
        {
            playerDeck.add(Deck.get(0));
            Deck.remove(0);
        }

        drawDeck.get((drawDeck.size() - 1)).setCardColor(colour);

    }

    public void cpuWildCard()
    {
        String colour = "";

        int randChoice = (int) (Math.random() * 4);

        if(randChoice == 0)
        {
            colour += "Red";
        }
        else if(randChoice == 1)
        {
            colour += "Blue";
        }
        else if(randChoice == 2)
        {
            colour += "Green";
        }
        else if(randChoice == 3)
        {
            colour += "Yellow";
        }

        drawDeck.get((drawDeck.size() - 1)).setCardColor(colour);

    }

    public void askPlayer()
    {
        int choice = 0;
        do
        {
            whatCanIPlay(playerDeck);

        do
        {
        System.out.println("Which card?");
        choice = scan.nextInt();
        }while(choice < 1 || choice > playerDeck.size() + 1);
        choice -= 1;

        if(choice == playerDeck.size())
        {
            System.out.println("\nYOU: DREW A CARD");
            drawCard(playerDeck);
            break;
        }
        else if(playerDeck.get(choice).getSuitVal() == 13)
        {
            drawDeck.add(playerDeck.get(choice));
            Deck.add(playerDeck.get(choice));
            playerDeck.remove(choice);
            order.add("PLAYER");
            wildCard();
            break;
        }
        else if(playerDeck.get(choice).getSuitVal() == 14)
        {
            drawDeck.add(playerDeck.get(choice));
            Deck.add(playerDeck.get(choice));
            playerDeck.remove(choice);
            order.add("PLAYER");
            draw4Card(cpuDeck);
            break;
        }
        else if((drawDeck.get((drawDeck.size() - 1)).getSuitName().equals( playerDeck.get(choice).getSuitName())) || (drawDeck.get((drawDeck.size() - 1)).getSuitVal() == playerDeck.get(choice).getSuitVal()))
        {
            drawDeck.add(playerDeck.get(choice));
            Deck.add(playerDeck.get(choice));
            playerDeck.remove(choice);
            order.add("PLAYER");
            break;
        }
        
        }while(true);


    }

    public void askCPU()
    {
        Boolean hasCard = false;
        Boolean hasWild = false;
        Boolean has4Wild = false;

        int indexOfCard = 0;
        for(int i = 0; i < cpuDeck.size(); i++)
        {
            if((cpuDeck.get(i).getSuitName().equals(drawDeck.get(drawDeck.size() - 1).getSuitName())))
            {
                hasCard = true;
                indexOfCard = i;
                break;
            }
            else if((cpuDeck.get(i).getSuitVal() == drawDeck.get(drawDeck.size() - 1).getSuitVal()))
            {
                hasCard = true;
                indexOfCard = i;
                break;
            }
            
        }
        if(hasCard == false)
        {
        for(int i = 0; i < cpuDeck.size(); i++)
        {
            if(cpuDeck.get(i).getSuitVal() == 13)
            {
                hasWild = true;
                indexOfCard = i;
                break;
            }
            else if(cpuDeck.get(i).getSuitVal() == 14)
            {
                has4Wild = true;
                indexOfCard = i;
                break;
            }
        }
        }

        if(hasCard)
        {
        System.out.println("CPU CARD: " + cpuDeck.get(indexOfCard).getSuitName() + " " + cpuDeck.get(indexOfCard).getSuitVal());
        drawDeck.add(cpuDeck.get(indexOfCard));
        Deck.add(cpuDeck.get(indexOfCard));
        cpuDeck.remove(indexOfCard);
        order.add("CPU");
        }
        else if(hasWild)
        {
            System.out.println("CPU HAS WILD CARD");
            cpuWild(indexOfCard);
        }
        else if(has4Wild)
        {
            System.out.println("CPU HAS 4 WILD CARD");
            cpu4Wild(indexOfCard);
        }
        else
        {
        System.out.println("CPU: DREW A CARD");
        drawCard(cpuDeck);
        }
       
        
    }

    public void cpuWild(int indexOfCard)
    {
        String colour = "";
        drawDeck.add(cpuDeck.get(indexOfCard));
        Deck.add(cpuDeck.get(indexOfCard));
        cpuDeck.remove(indexOfCard);
        order.add("CPU");

        int randChoice = (int) (Math.random() * 4);

        if(randChoice == 0)
        {
            colour += "Red";
        }
        else if(randChoice == 1)
        {
            colour += "Blue";
        }
        else if(randChoice == 2)
        {
            colour += "Green";
        }
        else if(randChoice == 3)
        {
            colour += "Yellow";
        }

        drawDeck.get((drawDeck.size() - 1)).setCardColor(colour);
    }

    public void cpu4Wild(int indexOfCard)
    {
        String colour = "";
        drawDeck.add(cpuDeck.get(indexOfCard));
        Deck.add(cpuDeck.get(indexOfCard));
        cpuDeck.remove(indexOfCard);
        order.add("CPU");

        int randChoice = (int) (Math.random() * 4);

        if(randChoice == 0)
        {
            colour += "Red";
        }
        else if(randChoice == 1)
        {
            colour += "Blue";
        }
        else if(randChoice == 2)
        {
            colour += "Green";
        }
        else if(randChoice == 3)
        {
            colour += "Yellow";
        }

        for(int i = 0; i < 4; i++)
        {
            playerDeck.add(Deck.get(0));
            Deck.remove(0);
        }

        drawDeck.get((drawDeck.size() - 1)).setCardColor(colour);

    }

    public void whatCanIPlay(ArrayList <Card> d)
    {
        int x = 0;
        Boolean canPlay = true;

        for(int i = 0; i < d.size(); i++)
        {
            
            if(d.get(i).getSuitName().equals(drawDeck.get(drawDeck.size() - 1).getSuitName()) || d.get(i).getSuitVal() == drawDeck.get(drawDeck.size() - 1).getSuitVal() || d.get(i).getSuitVal() > 12)
            {
                x++;
                if(x == 1)
                {
                    System.out.println("YOU CAN PLAY: ");
                }
                System.out.println(i + 1 + " : " + d.get(i).getSuitName() + " " + d.get(i).getSuitVal());
                canPlay = false;
            }
        }

        if(canPlay){
            System.out.println("YOU CAN'T PLAY: ");
            System.out.println(d.size() + 1 + " : " + "TO DRAW");
            
        }
            space();
    }

    public void draw4Card(ArrayList <Card> opponentDeck)
    {
       

        for(int i = 0 ; i < 4; i++)
        {
            opponentDeck.add(Deck.get(i));
            Deck.remove(0);
        }

        String color = "";
        int colour = 0;
        do
        {
        System.out.println("\nWhat color?\n1 = Red / 2 = Blue / 3 = Green / 4 = Yellow");
        colour = scan.nextInt();
        }while((colour < 1) || (colour > 4));

        if(colour == 1)
        {
            color += "Red";
        }
        if(colour == 2)
        {
            color += "Blue";
        }
        if(colour == 3)
        {
            color += "Green";
        }
        if(colour == 4)
        {
            color += "Yellow";
        }
        drawDeck.get(drawDeck.size() - 1).setCardColor(color);


    }

    public void wildCard()
    {
        String color = "";
        int colour = 0;
        do
        {
        System.out.println("\nWhat color?\n1 = Red / 2 = Blue / 3 = Green / 4 = Yellow");
        colour = scan.nextInt();
        }while((colour < 1) || (colour > 4));

        if(colour == 1)
        {
            color += "Red";
        }
        if(colour == 2)
        {
            color += "Blue";
        }
        if(colour == 3)
        {
            color += "Green";
        }
        if(colour == 4)
        {
            color += "Yellow";
        }
        drawDeck.get(drawDeck.size() - 1).setCardColor(color);
    }

    public void getDeck() 
    {
        space();
        for(int i = 0 ; i < Deck.size(); i++){
            System.out.println(Deck.get(i).getSuitName() + " " + Deck.get(i).getSuitVal());
        }
    }

    public void getHand()
    { 
        space();
        System.out.println("DRAW DECK: ");
        for(int i = 0; i < drawDeck.size(); i++)
        {
            System.out.println(i + 1 + ": " + drawDeck.get(i).getSuitName() + " " + drawDeck.get(i).getSuitVal() + " : " + order.get(i));
        }
        /*space();
        System.out.println("CPU DECK: ");
        for(int i = 0; i < cpuDeck.size(); i++)
        {
            System.out.println(i + 1 + ": " + cpuDeck.get(i).getSuitName() + " " + cpuDeck.get(i).getSuitVal());
        }
        */
        space();
        System.out.println("YOUR DECK: ");
        for(int i = 0; i < playerDeck.size(); i++)
        {
            System.out.println(i + 1 + ": " + playerDeck.get(i).getSuitName() + " " + playerDeck.get(i).getSuitVal());
        }
        space();
       
    }
    public void getDeck(ArrayList <Card> d, String s)
    {
        System.out.println("\n" + s.toUpperCase() + " DECK: ");
        for(int i = 0; i < d.size(); i++)
        {
            System.out.println(d.get(i).getSuitName() + " " + d.get(i).getSuitVal());
        }
    }

    public void shuffle()
    {
        for(int k = 0; k < 100; k++)
        {

        for(int i = Deck.size(); i > 0; i--)
        {
            int rand = (int)(Math.random() * i); 
            Card temp = Deck.get(rand);
            Deck.remove(rand);
            Deck.add(temp);
            
        }

        }

        if(Deck.get(14).getSuitVal() > 12){
            shuffle();
        }

    }

    public void space()
    {
        System.out.println();
    }

    public void header(){
        System.out.println("--------------------");
    }

		
} // end of class