#include<bits/stdc++.h>
using namespace std;

class Player;
class GameSession;
class Game;

class Player{
    static const int MAX_ROLLS_ALLOWED = 23;
    string name;
    int score;
    int rolls[MAX_ROLLS_ALLOWED];
    bool firstRoll;
    bool canPlay;
    int frameIndex;
    int currentRoll;
    bool isStrike();
    bool isSpare();
    void updateScore();

    public:
    Player(string);
    string getName();
    int getScore();
    bool isCanPlay();
    void roll(int);
};

class GameSession{
    int alley;
    int id;
    int getUniqueId();
    vector<Player> players;

    public:
    GameSession();
    void setAlley(int);
    int getId();
    void setPlayers(vector<Player>&);
    bool declareWinner();
    void makeRoll(Player&, int);
};

class Game{
    unordered_map<int, GameSession> gameSessions;

    public:
    static vector<int> alleys; 
    static void makeActive(int);
    int createSession(vector<Player>&);
    void roll(int, Player, int);
    void declareWinner(int);
};

Player::Player(string name){
    memset(rolls, 0, sizeof rolls);
    this->name = name;
    this->score = 0;
    this->firstRoll = true;
    this->frameIndex = 0;
    this->canPlay = true;
    this->currentRoll = 0;
}

string Player::getName(){
    return this->name;
}

int Player::getScore(){
    return this->score;
}

bool Player::isCanPlay(){
    return this->canPlay;
}

bool Player::isStrike(){
    return this->firstRoll == true && rolls[frameIndex] == 10;
}

bool Player::isSpare(){
    return rolls[frameIndex] + rolls[frameIndex+1] == 10;
}

void Player::updateScore(){
    if(isStrike()){
        score += 20;
        rolls[currentRoll++] = 0;
        frameIndex += 2;
        if(frameIndex >= MAX_ROLLS_ALLOWED){
            this->canPlay = false;
        }
    }
    else{
        if(frameIndex >= MAX_ROLLS_ALLOWED - 1){
            score += rolls[frameIndex];
            this->canPlay = false;
        }
        else if(firstRoll){
            firstRoll = false;
        }
        else{
            if(isSpare()){
                score += 5;
            }
            score += (rolls[frameIndex] + rolls[frameIndex + 1]);
            frameIndex += 2;
            firstRoll = true;
            if(frameIndex >= MAX_ROLLS_ALLOWED - 3){
                this->canPlay = false;
            }
        }
    }

}

void Player::roll(int pins){
    if(this->canPlay == false){
        return;
    }
    rolls[currentRoll++] = pins;
    updateScore();
}

GameSession::GameSession(){
    this->alley = -1;
    this->id = getUniqueId();
    this->players.clear();
}

int GameSession::getUniqueId(){
    static int gameSessionId = 1;
    return gameSessionId++;
}

void GameSession::setAlley(int alley){
    this->alley = alley;
}

int GameSession::getId(){
    return this->id;
}

void GameSession::setPlayers(vector<Player>& players){
    this->players = players;
}

bool GameSession::declareWinner(){
    int maxScore = 0;
    Player* winner = NULL;
    for(Player& p: players){
        if(p.isCanPlay()){
            cout<<"Player "<<p.getName()<<" hasn't completed yet. The current score: "<<p.getScore()<<endl;
            cout<<"Match is in progress"<<endl;
            return false;
        }
        if(p.getScore() > maxScore){
            maxScore = p.getScore();
            winner = &p;
        }
    }
    if(winner){
        cout<<"The winner is: "<<winner->getName()<<" with a score of "<<winner->getScore()<<endl;
    }
    Game::makeActive(this->alley);
    return true;
}

void GameSession::makeRoll(Player& player, int pins){
    for(Player &p: players){
        if(p.getName() == player.getName()){
            p.roll(pins);
        }
    }
}

vector<int> Game::alleys = {1, 2, 3, 4};

int Game::createSession(vector<Player>& players){
    if(Game::alleys.size() == 0){
        cout<<"All alleys are occupied!"<<endl;
        return 0;
    }
    GameSession gameSession;
    gameSession.setPlayers(players);
    gameSession.setAlley(Game::alleys.back());
    Game::alleys.pop_back();
    gameSessions[gameSession.getId()] = gameSession;
    return gameSession.getId();
}

void Game::makeActive(int alley){
    Game::alleys.push_back(alley);
}

void Game::declareWinner(int index){
    bool winnerFlag = gameSessions[index].declareWinner();
    if(!winnerFlag){
        cout<<"No Winners for this game yet"<<endl;
    }
}

void Game::roll(int index, Player player, int pins){
    GameSession* gameSession = &gameSessions[index];
    gameSession->makeRoll(player, pins);
}

int main(){
    Player p1("Thor");
    Player p2("Loki");
    Player p3("Hela");
    Player p4("Odin");

    vector<Player> vec;
    vec.push_back(p1);
    vec.push_back(p2);
    vec.push_back(p3);
    vec.push_back(p4);

    Game g;
    int index = g.createSession(vec);

    vector<int> s1;
    vector<int> s2;
    vector<int> s3;
    vector<int> s4;
    int score;
    for(int i=0; i<20; i++){
    	score = abs(rand()%10);
    	s1.push_back(score);
        g.roll(index, p1, score);
    	score = abs(rand()%10);
    	s2.push_back(score);
        g.roll(index, p2, score);
    	score = abs(rand()%10);
    	s3.push_back(score);
        g.roll(index, p3, score);
    	score = abs(rand()%10);
    	s4.push_back(score);
        g.roll(index, p4, score);
    }

    cout<<"Player 1: ";
    for(int i: s1){
    	cout<<i<<" ";
    }
    cout<<endl;

    cout<<"Player 2: ";
    for(int i: s2){
    	cout<<i<<" ";
    }
    cout<<endl;

    cout<<"Player 3: ";
    for(int i: s3){
    	cout<<i<<" ";
    }
    cout<<endl;

    cout<<"Player 4: ";
    for(int i: s4){
    	cout<<i<<" ";
    }
    cout<<endl;

    g.createSession(vec);
    g.createSession(vec);
    g.createSession(vec);
    g.declareWinner(index);
    g.createSession(vec);
    return 0;
}