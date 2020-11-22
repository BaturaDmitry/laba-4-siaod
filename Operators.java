package com.company;


public class Operators {
    public char symbol;
    public int priority;
    public Operators(char symbol, int priority){
        this.symbol = symbol;
        this.priority = priority;
    }

    public char getSymbol(){
        return symbol;
    }

    public int getPriority(){
        return priority;
    }
}