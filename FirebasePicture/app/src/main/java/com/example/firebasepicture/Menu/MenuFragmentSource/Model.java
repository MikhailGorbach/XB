package com.example.firebasepicture.Menu.MenuFragmentSource;

import androidx.annotation.NonNull;

//Информация об записи в firebase
public class Model {
    String pic;
    String title;
    String price;
    String name;
    String categories;

    public Model(){
    }

    public Model(String pic, String title, String price, String name, String categories) {
        this.pic = pic;
        this.title = title;
        this.price = price;
        this.name = name;
        this.categories = categories;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Model{" +
                "pic='" + pic + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", categories='" + categories + '\'' +
                '}';
    }

}
