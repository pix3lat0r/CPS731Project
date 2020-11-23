package com.example.cps731project;

import java.util.List;

public class RecipeModel {
    String name;
    List<String> ingredients;
    String instructions;

    public RecipeModel(){

    }

    public RecipeModel(String name, List<String> ingredients, String instructions){
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}
