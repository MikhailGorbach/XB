package com.example.firebasepicture.Menu.MenuFragmentSource;

//Информация об записи в firebase
public class Model {
    String photo;
    String price;
    String name;
    String category;
    String article;
    String link;
    String description;
    String width;
    String depth;
    String height;
    String material;
    String country;
    String weight;
    String volume;
    String assembling;
    String delivery;
    String colour;
    String company;
    String ogrn;


    public Model(String photo, String price, String name, String category, String article, String link, String description, String width, String depth, String height, String material, String country, String weight, String volume, String assembling, String delivery, String colour, String company, String ogrn) {
        this.photo = photo;
        this.price = price;
        this.name = name;
        this.category = category;
        this.article = article;
        this.link = link;
        this.description = description;
        this.width = width;
        this.depth = depth;
        this.height = height;
        this.material = material;
        this.country = country;
        this.weight = weight;
        this.volume = volume;
        this.assembling = assembling;
        this.delivery = delivery;
        this.colour = colour;
        this.company = company;
        this.ogrn = ogrn;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getAssembling() {
        return assembling;
    }

    public void setAssembling(String assembling) {
        this.assembling = assembling;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    @Override
    public String toString() {
        return "Model{" +
                "photo='" + photo + '\'' +
                ", price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", article='" + article + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", width='" + width + '\'' +
                ", depth='" + depth + '\'' +
                ", height='" + height + '\'' +
                ", material='" + material + '\'' +
                ", country='" + country + '\'' +
                ", weight='" + weight + '\'' +
                ", volume='" + volume + '\'' +
                ", assembling='" + assembling + '\'' +
                ", delivery='" + delivery + '\'' +
                ", colour='" + colour + '\'' +
                ", company='" + company + '\'' +
                ", ogrn='" + ogrn + '\'' +
                '}';
    }
}
