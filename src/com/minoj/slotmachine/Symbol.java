package com.minoj.slotmachine;

public class Symbol implements ISymbol {

    private String image; // path of the image of the symbol
    private int value; // value of the symbol

    public Symbol(String image, int value) {
        this.image = image;
        this.value = value;
    }

    /**
     * Retrieve the Image of the Symbol
     * @return Path of the Image of the Symbol
     */
    @Override
    public String getImage() {
        return this.image;
    }

    /**
     * Set the Image of the Symbol
     * @param image Path of the Image of the Symbol
     */
    @Override
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Retrieve the value of the Symbol
     * @return Value of the Symbol
     */
    @Override
    public int getValue() {
        return this.value;
    }

    /**
     * Assign the value of the Symbol
     * @param value Value of the Symbol
     */
    @Override
    public void setValue(int value) {
        this.value = value;
    }

}
