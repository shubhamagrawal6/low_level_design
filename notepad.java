import java.io.*;
import java.util.*;

class Notepad{
    private ArrayList<String> allContent = new ArrayList<String>();
    private Stack<ArrayList<String>> undoStack = new Stack<ArrayList<String>>();
    private Stack<ArrayList<String>> redoStack = new Stack<ArrayList<String>>();
    private ArrayList<String> buffer = new ArrayList<String>();
    
    public Notepad(String text){
        String[] temp = text.split("\n");
        for(String s: temp){
            allContent.add(s);
        }
    }

    public void display(){
        for(String s: allContent){
            System.out.println(s);
        }
    }

    public boolean display(int n, int m){
        if(n > allContent.size()){
            System.out.println("The value of n is invalid");
            return false;
        }
        
        if(m > allContent.size()){
            System.out.println("The value of m is invalid");
            return false;
        }
        
        if(n > m){
            System.out.println("The value of n exceeds m");
            return false;
        }

        for(int i=n-1; i<m; i++){
            System.out.println(allContent.get(i));
        }

        return true;
    }

    public boolean insert(int n, String text){
        if(n > allContent.size()){
            System.out.println("The value of n is invalid");
            return false;
        }

        ArrayList<String> ins = new ArrayList<String>(allContent);
        undoStack.push(ins);

        String temp = allContent.get(n-1) + text;
        allContent.set(n-1, temp);

        return true;
    }

    public boolean Delete(int n){
        if(n > allContent.size()){
            System.out.println("The value of n is invalid");
            return false;
        }

        ArrayList<String> ins = new ArrayList<String>(allContent);
        undoStack.push(ins);
        allContent.remove(n-1);

        return true;
    }

    public boolean Delete(int n, int m){
        if(n > allContent.size()){
            System.out.println("The value of n is invalid");
            return false;
        }
        
        if(m > allContent.size()){
            System.out.println("The value of m is invalid");
            return false;
        }
        
        if(n > m){
            System.out.println("The value of n exceeds m");
            return false;
        }

        ArrayList<String> ins = new ArrayList<String>(allContent);
        undoStack.push(ins);
        allContent.subList(n-1, m).clear();

        return true;
    }

    public boolean copy(int n, int m){
        if(n > allContent.size()){
            System.out.println("The value of n is invalid");
            return false;
        }
        
        if(m > allContent.size()){
            System.out.println("The value of m is invalid");
            return false;
        }
        
        if(n > m){
            System.out.println("The value of n exceeds m");
            return false;
        }

        for(int i=n-1; i<m; i++){
            buffer.add(allContent.get(i));
        }

        return true;
    }

    public boolean paste(int n){
        if(n > allContent.size()){
            System.out.println("The value of n is invalid");
            return false;
        }

        ArrayList<String> ins = new ArrayList<String>(allContent);
        undoStack.push(ins);
        allContent.addAll(n, buffer);

        return true;
    }

    public boolean undo(){
        if(undoStack.empty()){
            System.out.println("Nothing to undo");
            return false;
        }
        
        ArrayList<String> ins = new ArrayList<String>(allContent);
        redoStack.push(ins);
        this.allContent = undoStack.pop();
        
        return true;
    }

    public boolean redo(){
        if(redoStack.empty()){
            System.out.println("Nothing to redo");
            return false;
        }
    
        ArrayList<String> ins = new ArrayList<String>(allContent);
        undoStack.push(ins);
        this.allContent = redoStack.pop();
        
        return true;
    }
}

public class notepad{
    public static void main(String[] args){
        Notepad np = new Notepad("This is a sample text for checking\nThis is another sample text\nYet another sample text for custom tests");
        np.display();
        
        System.out.println("**************************** 0 ***********************************");
        System.out.println("** Displaying content: only first two lines **");
        np.display(1,2);
        
        System.out.println("**************************** 1 ***********************************");
        System.out.println("** Inserting yeah to the first line **");
        np.insert(1, ", Yeah");
        np.display();
        
        System.out.println("**************************** 2 ***********************************");
        System.out.println("** Undoing last move **");
        np.undo();
        np.display();
        
        System.out.println("**************************** 3 ***********************************");
        System.out.println("** Redoing last move **");
        np.redo();
        np.display();
        
        System.out.println("**************************** 4 ***********************************");
        System.out.println("** Redoing last move **");
        np.redo();
        
        System.out.println("**************************** 5 ***********************************");
        System.out.println("** Deleting first line **");
        np.Delete(1);
        np.display();
        
        System.out.println("**************************** 6 ***********************************");
        System.out.println("** Undoing last move **");
        np.undo();
        np.display();
        
        System.out.println("**************************** 7 ***********************************");
        System.out.println("** Undoing last move **");
        np.undo();
        np.display();
        
        System.out.println("**************************** 8 ***********************************");
        System.out.println("** After deletion of lines 1 to 2 **");
        np.Delete(1,2);
        np.display();
        
        System.out.println("**************************** 9 ***********************************");
        System.out.println("** Undoing last move **");
        np.undo();
        np.display();
        
        System.out.println("**************************** 10 ***********************************");
        System.out.println("** Copying lines 1 to 2 and pasting them on 3rd line **");
        np.copy(1,2);
        np.paste(3);
        np.display();
        
        System.out.println("**************************** 11 ***********************************");
        System.out.println("** Undoing last move **");
        np.undo();
        np.display();
        
        System.out.println("**************************** 12 ***********************************");
        System.out.println("** Redoing last move **");
        np.redo();
        np.display();
    }
}