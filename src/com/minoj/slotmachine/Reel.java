package com.minoj.slotmachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Reel implements Comparable<Reel>{

    private List<Symbol> symbols; // store the list of arrays
    private boolean stop; // store if the reel is spinning or not
    private int value; // value of each

    public Reel() {
        this.symbols = new ArrayList<Symbol>();

        Symbol cherry = new Symbol("images/cherry.png",2);

        Symbol lemon = new Symbol("images/lemon.png",3);

        Symbol plum = new Symbol("images/plum.png",4);

        Symbol melon = new Symbol("images/melon.png",5);

        Symbol bell = new Symbol("images/bell.png",6);

        Symbol seven = new Symbol("images/redseven.png",7);

        symbols.add(cherry);
        symbols.add(lemon);
        symbols.add(plum);
        symbols.add(melon);
        symbols.add(bell);
        symbols.add(seven);

        stop = false;
        value = 0;
    }

    /**
     * The List of Symbols are shuffled and then returned
     * @return List containing the Symbols
     */
    public List<Symbol> spin() {
        Collections.shuffle(this.symbols);
        return symbols;
    }

    public Symbol getSymbol(int value) {
        for(int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getValue() == value) {
                return symbols.get(i);
            }
        }
        return null;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public int compareTo(Reel o) {
        if(o.getValue() > this.value || o.getValue() < this.value) {
            return 1;
        }
        return 0;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isStop() {
        return stop;
    }
}
