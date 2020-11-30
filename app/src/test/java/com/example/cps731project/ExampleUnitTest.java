package com.example.cps731project;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 *
 */

@RunWith(JUnit4.class)
public class ExampleUnitTest {

    private IngredientsModel ingredientTest;
    private UserID id;
    private RecipeModel recipeTest;

    @Before
    public void setUp(){
        ingredientTest = new IngredientsModel();
        id = new UserID();
        recipeTest = new RecipeModel();
    }

    @Test
    public void getRecipeName(){
        recipeTest.setName("French Toast");
        String recipeName = recipeTest.getName();
        assertEquals(recipeName, "French Toast");

    }
    @Test
    public void getIngredientName() {
        ingredientTest.setName("banana");
        String ingredName = ingredientTest.getName();

        assertEquals(ingredName, "banana");
    }

    @Test
    public void getUserEmail(){
        id.setUserID("test@gmail.com");
        String email = id.getUser_id();
        assertEquals(email, "test@gmail.com");
    }
}