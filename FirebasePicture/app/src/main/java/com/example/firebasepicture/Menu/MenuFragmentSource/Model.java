package com.example.firebasepicture.Menu.MenuFragmentSource;

//Информация об записи в firebase
public class Model {
    String photo;
    String price;
    String name;
    String categories;

    public Model(){
    }

    public Model(String pic, String title, String price, String name, String categories) {
        this.photo = pic;
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
        return photo;
    }

    public void setPic(String pic) {
        this.photo = pic;
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
                "pic='" + photo + '\'' +
                ", price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", categories='" + categories + '\'' +
                '}';
    }

}
