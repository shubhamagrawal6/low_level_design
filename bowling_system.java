import java.io.*;
import java.io.ObjectInputFilter.Status;
import java.util.*;
import java.lang.Math;

class Player{
    private static final int MAX_ROLLS_ALLOWED = 23;
    private String name;
    private int score;
    private int rolls[] = new int[MAX_ROLLS_ALLOWED];
    private boolean firstRoll;
    private boolean canPlay;
    private int frameIndex;
    private int currentRoll;

    private boolean isStrike(){
        return (this.firstRoll == true) && (this.rolls[this.frameIndex] == 10);
    }

    private boolean isSpare(){
        return (this.rolls[this.frameIndex] + this.rolls[this.frameIndex+1]) == 10;
    }

    private void updateScore(){
        if(this.isStrike()){
            this.score += 20;
            this.rolls[this.currentRoll++] = 0;
            this.frameIndex += 2;
            if(this.frameIndex >= this.MAX_ROLLS_ALLOWED){
                this.canPlay = false;
            }
        }
        else{
            if(this.frameIndex >= this.MAX_ROLLS_ALLOWED - 1){
                this.score += this.rolls[this.frameIndex];
                this.canPlay = false;
            }
            else if(this.firstRoll){
                this.firstRoll = false;
            }
            else{
                if(this.isSpare()){
                    this.score += 5;
                }
                this.score += (this.rolls[this.frameIndex] + this.rolls[this.frameIndex + 1]);
                this.frameIndex += 2;
                this.firstRoll = true;
                if(this.frameIndex >= this.MAX_ROLLS_ALLOWED - 3){
                    this.canPlay = false;
                }
            }
        }
    }

    public Player(String name){
        this.name = name;
        this.score = 0;
        this.firstRoll = true;
        this.frameIndex = 0;
        this.canPlay = true;
        this.currentRoll = 0;
    }

    public String getName(){
        return this.name;
    }

    public int getScore(){
        return this.score;
    }

    public boolean isCanPlay(){
        return this.canPlay;
    }

    public void roll(int pins){
        if(this.isCanPlay() == false){
            return;
        }

        this.rolls[this.currentRoll++] = pins;
        this.updateScore();
    }
}

class GameSession{
    private int alley;
    private int id;
    private static int uniqueId = 1;
    private ArrayList<Player> players = new ArrayList<Player>();

    private int getUniqueId(){
        return this.uniqueId++;
    }

    public GameSession(){
        this.alley = -1;
        this.id = getUniqueId();
        this.players.clear();
    }

    public void setAlley(int alley){
        this.alley = alley;
    }

    public int getId(){
        return this.id;
    }

    public void setPlayers(ArrayList<Player> players){
        this.players = players;
    }

    public boolean declareWinner(){
        int maxScore = 0;
        Player winner = null;

        for(Player p: this.players){
            if(p.isCanPlay()){
                System.out.println("Player " + p.getName() + " hasn't completed yet. The current score: " + p.getScore());
                System.out.println("Match is in progress");
                
                return false;
            }
            
            if(p.getScore() > maxScore){
                maxScore = p.getScore();
                winner = p;
            }
        }
        
        if(winner != null){
            System.out.println("The winner is: " + winner.getName() + " with a score of " + winner.getScore());
        }
        
        Game.makeActive(this.alley);
        return true;
    }

    public void makeRoll(Player player, int pins){
        for(Player p: this.players){
            if(p.getName() == player.getName()){
                p.roll(pins);
            }
        }
    }
}

class Game{
    private HashMap<Integer, GameSession> gameSessions = new HashMap<Integer, GameSession>();
    public static ArrayList<Integer> alleys = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4)); 

    public static void makeActive(int alley){
        Game.alleys.add(alley);
    }

    public int createSession(ArrayList<Player> players){
        if(Game.alleys.size() == 0){
            System.out.println("All alleys are occupied!");
            return 0;
        }

        GameSession gameSession = new GameSession();
        gameSession.setPlayers(players);
        gameSession.setAlley(Game.alleys.get(Game.alleys.size()-1));
        Game.alleys.remove(Game.alleys.size()-1);
        gameSessions.put(gameSession.getId(), gameSession);
        return gameSession.getId();
    }

    public void roll(int index, Player player, int pins){
        GameSession gameSession = this.gameSessions.get(index);
        gameSession.makeRoll(player, pins);
    }

    public void declareWinner(int index){
        boolean winnerFlag = this.gameSessions.get(index).declareWinner();
        
        if(winnerFlag == false){
            System.out.println("No Winners for this game yet");
        }
    }
}

public class prac {
    public static void main(String[] args){
        Player p1 = new Player("Thor");
        Player p2 = new Player("Loki");
        Player p3 = new Player("Hela");
        Player p4 = new Player("Odin");

        ArrayList<Player> vec = new ArrayList<Player>();
        vec.add(p1);
        vec.add(p2);
        vec.add(p3);
        vec.add(p4);

        Game g = new Game();
        int index = g.createSession(vec);

        ArrayList<Integer> s1 = new ArrayList<Integer>();
        ArrayList<Integer> s2 = new ArrayList<Integer>();
        ArrayList<Integer> s3 = new ArrayList<Integer>();
        ArrayList<Integer> s4 = new ArrayList<Integer>();

        int score;
        Random random = new Random();
        for(int i=0; i<20; i++){
            score = Math.abs(random.nextInt(10));
            s1.add(score);
            g.roll(index, p1, score);
            score = Math.abs(random.nextInt(10));
            s2.add(score);
            g.roll(index, p2, score);
            score = Math.abs(random.nextInt(10));
            s3.add(score);
            g.roll(index, p3, score);
            score = Math.abs(random.nextInt(10));
            s4.add(score);
            g.roll(index, p4, score);
        }

        System.out.println("Player 1: ");
        for(int i: s1){
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("Player 2: ");
        for(int i: s2){
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("Player 3: ");
        for(int i: s3){
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("Player 4: ");
        for(int i: s4){
            System.out.print(i + " ");
        }
        System.out.println();

        g.createSession(vec);
        g.createSession(vec);
        g.createSession(vec);
        g.declareWinner(index);
        g.createSession(vec);
    }
}