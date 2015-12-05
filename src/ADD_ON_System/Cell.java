package ADD_ON_System;

public class Cell{  
    int heuristicCost = 0; //Heuristic cost
    int finalCost = 0; //G+H
    public int x, y;
    Cell parent; 
    
    public Cell(int x, int y){
        this.x = x;
        this.y = y; 
    }
}