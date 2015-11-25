package ADD_ON_System;

class Cell{  
    int heuristicCost = 0; //Heuristic cost
    int finalCost = 0; //G+H
    int i, j;
    Cell parent; 
    
    Cell(int i, int j){
        this.i = i;
        this.j = j; 
    }
}