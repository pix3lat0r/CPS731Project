package com.example.cps731project;

public class IngredientsModel {

    private String name;

    private IngredientsModel(){}

    private IngredientsModel(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
