package com.example.foodapplication.Model;

import java.util.ArrayList;

public class CategoriesResponse {

        ArrayList< Categories > categories = new ArrayList < Categories>();


        public ArrayList<Categories> getCategories() {
            return categories;
        }

        public void setCategories(ArrayList<Categories> categories) {
            this.categories = categories;
        }
    }

