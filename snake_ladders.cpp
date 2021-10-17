#include<bits/stdc++.h>
using namespace std;

class Snake;
class Ladder;
class Player;
class Game;

class Snake{
    int start, end;

    public:
    Snake(int, int);
    int getStart();
    int getEnd();
};

class Ladder{
    int start, end;

    public:
    Ladder(int, int);
    int getStart();
    int getEnd();
};

class Player{
    int id;
    string name;
    int currentPosition;
    int getUniqueId();

    public:
    Player(string);
    int getId();
    string getName();
    int getCurrentPosition();
    void setCurrentPostion(int);
};

class Game{
    vector<Player> players;
    int currentTurn;
    Player* winner;
    unordered_map<int, int> snakesAndLadders;
    void nextPlayer();

    public:
    Game(vector<Snake>&, vector<Ladder>&, vector<Player>&);
    vector<Player>& getPlayers();
    Player* getWinner();
    bool roll(Player, int);
};

Snake::Snake(int start, int end){
    this->start = start;
    this->end = end;
}

int Snake::getStart(){
    return this->start;
}

int Snake::getEnd(){
    return this->end;
}

Ladder::Ladder(int start, int end){
    this->start = start;
    this->end = end;
}

int Ladder::getStart(){
    return this->start;
}

int Ladder::getEnd(){
    return this->end;
}

Player::Player(string name){
    this->name = name;
    this->id = getUniqueId();
    this->currentPosition = 0;
}

int Player::getUniqueId(){
    static int playerId = 1;
    return playerId++;
}

int Player::getId(){
    return this->id;
}

string Player::getName(){
    return this->name;
}

int Player::getCurrentPosition(){
    return this->currentPosition;
}

void Player::setCurrentPostion(int position){
    this->currentPosition = position;
}

Game::Game(vector<Snake>& snakes, vector<Ladder>& ladders, vector<Player>& players){
    this->players = players;
    
    for(Snake snake: snakes){
        snakesAndLadders[snake.getStart()] = snake.getEnd();
    }
    
    for(Ladder ladder: ladders){
        snakesAndLadders[ladder.getStart()] = ladder.getEnd();
    }

    this->currentTurn = 0;
    this->winner = NULL;
}

void Game::nextPlayer(){
    currentTurn = (currentTurn + 1) % (players.size());
}

vector<Player>& Game::getPlayers(){
    return this->players;
}

Player* Game::getWinner(){
    return this->winner;
}

bool Game::roll(Player player, int diceValue){
    if(winner || diceValue > 6 || diceValue < 1 || players.at(currentTurn).getId() != player.getId()){
        return false;
    }
    
    int destination = players.at(currentTurn).getCurrentPosition() + diceValue;
    
    if(destination <= 100){
        if(snakesAndLadders.find(destination) != snakesAndLadders.end()){
            players.at(currentTurn).setCurrentPostion(snakesAndLadders[destination]);
        }
        else{
            players.at(currentTurn).setCurrentPostion(destination);
        }
    }

    if(destination == 100){
        winner = &(players.at(currentTurn));
    }

    nextPlayer();
    return true;
}

int main(){
    Player p1("Robert");
	Player p2("Stannis");
	Player p3("Renly");

	Snake s1(17, 7);
	Snake s2(54, 34);
	Snake s3(62, 19);
	Snake s4(64, 60);
	Snake s5(87, 36);
	Snake s6(92, 73);
	Snake s7(95, 75);
	Snake s8(98, 79);

	Ladder l1(1, 38);
	Ladder l2(4, 14);
	Ladder l3(9, 31);
	Ladder l4(21, 42);
	Ladder l5(28, 84);
	Ladder l6(51, 67);
	Ladder l7(72, 91);
	Ladder l8(80, 99);

	vector<Snake> s = {s1, s2, s3, s4, s5, s6, s7, s8};
	vector<Ladder> l = {l1, l2, l3, l4, l5, l6, l7, l8};
	vector<Player> p = {p1, p2, p3};

	Game game(s,l,p);

	while(game.getWinner() == nullptr){
		int diceVal = (rand()%6)+1;
		game.roll(p1, diceVal);
		diceVal = (rand()%6)+1;
		game.roll(p2, diceVal);
		diceVal = (rand()%6)+1;
		game.roll(p3, diceVal);
	}

	cout<<"The winner is: "<<game.getWinner()->getName()<<endl;

	cout<<"All Scores: ";
	for(Player p: game.getPlayers()){
		cout<<p.getCurrentPosition()<<" ";
    }

    return 0;
}