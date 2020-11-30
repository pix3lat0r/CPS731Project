package com.example.cps731project;

import java.util.ArrayList;
import java.util.List;

public class RecipeModel {
    String name;
    ArrayList<String> ingredients;
    ArrayList<String> instructions;

    public RecipeModel(){

    }

    public RecipeModel(String name, ArrayList<String> ingredients, ArrayList<String> instructions){
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

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }
}
