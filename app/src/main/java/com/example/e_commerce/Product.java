package com.example.e_commerce;

public class Product {
    String Name;
    int Price;
    int Quantity;
    int  CategoryID;
    String Description;

    public Product(String s, int price, int quan, int catID,String des) {
        this.Name=s;
        this.Price=price;
        this.Quantity=quan;
        this.CategoryID=catID;
        this.Description=des;
    }




}
