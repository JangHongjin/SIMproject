package ADD_ON_System;

public class Cell{  
    int heuristicCost = 0; //Heuristic cost
    int finalCost = 0; //G+H
    int i, j;
    Cell parent; 
    
    public Cell(int i, int j){
        this.i = i;
        this.j = j; 
    }
}