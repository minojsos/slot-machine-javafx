package com.minoj.slotmachine;

public interface ISymbol {

    /**
     * Retrieve the image of the Symbol
     * @return String - Path of the Image
     */
    public String getImage();

    /**
     * Set the Image of the Symbol
     * @param image Path of the Image
     */
    public void setImage(String image);

    /**
     * Retrieve the value of the Symbol
     * @return Value of the Symbol
     */
    public int getValue();

    /**
     * Set the value of the Symbol
     * @param value Value of the Symbol
     */
    public void setValue(int value);
}
