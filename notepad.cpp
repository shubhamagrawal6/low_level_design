#include <bits/stdc++.h>
using namespace std;

class Notepad{
    vector<string> allContent;
    stack<vector<string>> undoStack;
    stack<vector<string>> redoStack;
    vector<string> buffer;
    vector<string> split(string, char);

    public:
    Notepad(string);
    void display();
    bool display(int, int);
    bool insert(int, string);
    bool Delete(int);
    bool Delete(int, int);
    bool copy(int, int);
    bool paste(int);
    bool undo();
    bool redo();
};

Notepad::Notepad(string text){
    allContent = split(text, '\n');
}

vector<string> Notepad::split(string text, char delim){
    vector<string> result;
    
    auto i = 0;
    auto pos = text.find(delim);
    while(pos != string::npos){
        result.push_back(text.substr(i, pos-i));
        i = ++pos;
        pos = text.find(delim, pos);
    }

    result.push_back(text.substr(i, text.length()));
    return result;
}

void Notepad::display(){
    for(string s: allContent){
        cout<<s<<endl;
    }
}

bool Notepad::display(int n, int m){
    if(n > allContent.size()){
        cout<<"The value of n is invalid"<<endl;
        return false;
    }
    
    if(m > allContent.size()){
        cout<<"The value of m is invalid"<<endl;
        return false;
    }
    
    if(n > m){
        cout<<"The value of n exceeds m"<<endl;
        return false;
    }

    for(auto iter=allContent.begin()+n-1; iter != allContent.begin()+m; iter++){
        cout<<*iter<<endl;
    }

    return true;
}

bool Notepad::insert(int n, string text){
    if(n > allContent.size()){
        cout<<"The value of n is invalid"<<endl;
        return false;
    }

    undoStack.push(allContent);
    
    string *content = &(*(allContent.begin() + n - 1));
    *content += text;
    
    return true;
}

bool Notepad::Delete(int n){
    if(n > allContent.size()){
        cout<<"The value of n is invalid"<<endl;
        return false;
    }

    undoStack.push(allContent);
    allContent.erase(allContent.begin() + n - 1);
    
    return true;
}

bool Notepad::Delete(int n, int m){
    if(n > allContent.size()){
        cout<<"The value of n is invalid"<<endl;
        return false;
    }

    if(m > allContent.size()){
        cout<<"The value of m is invalid"<<endl;
        return false;
    }
    
    if(n > m){
        cout<<"The value of n exceeds m"<<endl;
        return false;
    }

    undoStack.push(allContent);
    allContent.erase(allContent.begin() + n - 1, allContent.begin() + m);
    
    return true;
}

bool Notepad::copy(int n, int m){
    if(n > allContent.size()){
        cout<<"The value of n is invalid"<<endl;
        return false;
    }
    
    if(m > allContent.size()){
        cout<<"The value of m is invalid"<<endl;
        return false;
    }
    
    if(n > m){
        cout<<"The value of n exceeds m"<<endl;
        return false;
    }

    for(auto iter = allContent.begin()+n-1; iter != allContent.begin()+m; iter++){
        buffer.push_back(*iter);
    }

    return true;
}

bool Notepad::paste(int n){
    if(n > allContent.size()){
        cout<<"The value of n is invalid"<<endl;
        return false;
    }

    undoStack.push(allContent);
    allContent.insert(allContent.begin()+n-1, buffer.begin(), buffer.end());
    
    return true;
}

bool Notepad::undo(){
    if(undoStack.empty()){
        cout<<"Nothing to undo"<<endl;
        return false;
    }

    redoStack.push(allContent);
    allContent = undoStack.top();
    undoStack.pop();
    
    return true;
}

bool Notepad::redo(){
    if(redoStack.empty()){
        cout<<"Nothing to redo"<<endl;
        return false;
    }

    undoStack.push(allContent);
    allContent = redoStack.top();
    redoStack.pop();
    
    return true;
}

int main(){
    Notepad notepad("This is a sample text for checking\nThis is another sample text\nYet another sample text for custom tests");
	notepad.display();
	
    cout<<"**************************** 0 ***********************************"<<endl;
	cout<<"** Displaying content: only first two lines **"<<endl;
	notepad.display(1,2);
	
    cout<<"**************************** 1 ***********************************"<<endl;
	cout<<"** Inserting yeah to the first line **"<<endl;
	notepad.insert(1, ", Yeah");
	notepad.display();
	
    cout<<"**************************** 2 ***********************************"<<endl;
	cout<<"** Undoing last move **"<<endl;
	notepad.undo();
	notepad.display();
	
    cout<<"**************************** 3 ***********************************"<<endl;
	cout<<"** Redoing last move **"<<endl;
	notepad.redo();
	notepad.display();
	
    cout<<"**************************** 4 ***********************************"<<endl;
	cout<<"** Redoing last move **"<<endl;
	notepad.redo();
	
    cout<<"**************************** 5 ***********************************"<<endl;
	cout<<"** Deleting first line **"<<endl;
	notepad.Delete(1);
	notepad.display();
	
    cout<<"**************************** 6 ***********************************"<<endl;
	cout<<"** Undoing last move **"<<endl;
	notepad.undo();
	notepad.display();
	
    cout<<"**************************** 7 ***********************************"<<endl;
	cout<<"** Undoing last move **"<<endl;
	notepad.undo();
	notepad.display();
	
    cout<<"**************************** 8 ***********************************"<<endl;
	cout<<"** After deletion of lines 1 to 2 **"<<endl;
	notepad.Delete(1,2);
	notepad.display();
	
    cout<<"**************************** 9 ***********************************"<<endl;
	cout<<"** Undoing last move **"<<endl;
	notepad.undo();
	notepad.display();
	
    cout<<"**************************** 10 ***********************************"<<endl;
	cout<<"** Copying lines 1 to 2 and pasting them on 3rd line **"<<endl;
	notepad.copy(1,2);
	notepad.paste(3);
	notepad.display();
	
    cout<<"**************************** 11 ***********************************"<<endl;
	cout<<"** Undoing last move **"<<endl;
	notepad.undo();
	notepad.display();
	
    cout<<"**************************** 12 ***********************************"<<endl;
	cout<<"** Redoing last move **"<<endl;
	notepad.redo();
	notepad.display();

    return 0;
}