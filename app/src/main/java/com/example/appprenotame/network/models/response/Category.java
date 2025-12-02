package com.example.appprenotame.network.models.response;


import lombok.Getter;

@Getter
public class Category {
    private String name;
    private int id;



    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
