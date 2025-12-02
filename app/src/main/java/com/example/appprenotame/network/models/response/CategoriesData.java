package com.example.appprenotame.network.models.response;

import java.util.List;

import lombok.Getter;


@Getter
public class CategoriesData {
    private List<Category> categorie;

    public CategoriesData(List<Category> categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "CategoriesData{" +
                "categorie=" + categorie +
                '}';
    }
}
