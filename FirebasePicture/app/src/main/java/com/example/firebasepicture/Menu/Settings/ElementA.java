package com.example.firebasepicture.Menu.Settings;

public class ElementA {

    String category;
    String number;
    String text;
    public int num;

    public ElementA(){

    }

    public ElementA(String category, String number, String text) {
        this.category = category;
        this.number = number;
        this.text = text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
