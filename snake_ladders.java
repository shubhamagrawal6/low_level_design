import java.io.*;
import java.util.*;
import java.lang.Math;

class Snake{
    private int start;
    private int end;

    public Snake(int start, int end){
        this.start = start;
        this.end = end;
    }

    public int getStart(){
        return this.start;
    }

    public int getEnd(){
        return this.end;
    }
}

class Ladder{
    private int start;
    private int end;

    public Ladder(int start, int end){
        this.start = start;
        this.end = end;
    }

    public int getStart(){
        return this.start;
    }

    public int getEnd(){
        return this.end;
    }
}

class Player{
    private int id;
    private String name;
    private int currentPosition;
    private static int uniquePlayerId;

    static{
        Player.uniquePlayerId = 1;
    }

    private int getUniqueId(){
        return Player.uniquePlayerId++;
    }

    public Player(String name){
        this.name = name;
        this.id = getUniqueId();
        this.currentPosition = 0;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getCurrentPosition(){
        return this.currentPosition;
    }

    public void setCurrentPosition(int position){
        this.currentPosition = position;
    }
}

class Game{
    private ArrayList<Player> players;
    private int currentTurn;
    private Player winner;
    private HashMap<Integer, Integer> snakesAndLadders = new HashMap<Integer, Integer>();
    
    private void nextPlayer(){
        currentTurn = (currentTurn + 1) % (players.size());
    }

    public Game(ArrayList<Snake> snakes, ArrayList<Ladder> ladders, ArrayList<Player> players){
        this.players = players;

        for(Snake snake: snakes){
            snakesAndLadders.put(snake.getStart(), snake.getEnd());
        }

        for(Ladder ladder: ladders){
            snakesAndLadders.put(ladder.getStart(), ladder.getEnd());
        }

        this.currentTurn = 0;
        this.winner = null;
    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    public Player getWinner(){
        return this.winner;
    }

    public boolean roll(Player player, int diceValue){
        if(winner != null || diceValue > 6 || diceValue < 1 || players.get(currentTurn).getId() != player.getId()){
            return false;
        }

        int destination = players.get(currentTurn).getCurrentPosition() + diceValue;

        if(destination <= 100){
            if(snakesAndLadders.containsKey(destination)){
                players.get(currentTurn).setCurrentPosition(snakesAndLadders.get(destination));
            }
            else{
                players.get(currentTurn).setCurrentPosition(destination);
            }
        }

        if(destination == 100){
            winner = players.get(currentTurn);
        }

        nextPlayer();
        return true;
    }
}

public class prac{
    public static void main(String[] args){
        Player p1 = new Player("Robert");
        Player p2 = new Player("Stannis");
        Player p3 = new Player("Renly");

        Snake s1 = new Snake(17, 7);
        Snake s2 = new Snake(54, 34);
        Snake s3 = new Snake(62, 19);
        Snake s4 = new Snake(64, 60);
        Snake s5 = new Snake(87, 36);
        Snake s6 = new Snake(92, 73);
        Snake s7 = new Snake(95, 75);
        Snake s8 = new Snake(98, 79);

        Ladder l1 = new Ladder(1, 38);
        Ladder l2 = new Ladder(4, 14);
        Ladder l3 = new Ladder(9, 31);
        Ladder l4 = new Ladder(21, 42);
        Ladder l5 = new Ladder(28, 84);
        Ladder l6 = new Ladder(51, 67);
        Ladder l7 = new Ladder(72, 91);
        Ladder l8 = new Ladder(80, 99);

        ArrayList<Snake> snakes = new ArrayList<Snake>(Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8));
        ArrayList<Ladder> ladders = new ArrayList<Ladder>(Arrays.asList(l1, l2, l3, l4, l5, l6, l7, l8));
        ArrayList<Player> players = new ArrayList<Player>(Arrays.asList(p1, p2, p3));

        Game game = new Game(snakes, ladders, players);

        while(game.getWinner() == null){
            int diceValue = (int)(Math.random()*6 + 1);
            game.roll(p1, diceValue);
            diceValue = (int)(Math.random()*6 + 1);
            game.roll(p2, diceValue);
            diceValue = (int)(Math.random()*6 + 1);
            game.roll(p3, diceValue);
            diceValue = (int)(Math.random()*6 + 1);
        }

        System.out.println("The winner is: " + game.getWinner().getName());

        System.out.println("All Scores: ");
        for(Player p: game.getPlayers()){
            System.out.print(p.getCurrentPosition() + " ");
        }
    }
}