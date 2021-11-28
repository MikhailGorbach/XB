package com.example.firebasepicture;

//Информация об записи в firebase
import android.media.Image;
public class Model {
    String article;
    Long articleL;
    String assembling;
    String category;
    String colour;
    String company;
    String country;
    String delivery;
    String depth;
    String description;
    String height;
    String link;
    String material;
    String name;
    String ogrn;
    String photo;
    String price;
    String scaleCompensation;
    String seller;
    String volume;
    String weight;
    String width;

    public Model() {

    }

    public Model(String article, String assembling, String category, String colour, String company, String country, String delivery, String depth, String description, String height, String link, String material, String name, String ogrn, String photo, String price, String scaleCompensation, String seller, String volume, String weight, String width) {
        this.article = article;
        this.assembling = assembling;
        this.category = category;
        this.colour = colour;
        this.company = company;
        this.country = country;
        this.delivery = delivery;
        this.depth = depth;
        this.description = description;
        this.height = height;
        this.link = link;
        this.material = material;
        this.name = name;
        this.ogrn = ogrn;
        this.photo = photo;
        this.price = price;
        this.scaleCompensation = scaleCompensation;
        this.seller = seller;
        this.volume = volume;
        this.weight = weight;
        this.width = width;
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

    public String getScaleCompensation() {
        return scaleCompensation;
    }

    public void setScaleCompensation(String scaleCompensation) {
        this.scaleCompensation = scaleCompensation;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
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