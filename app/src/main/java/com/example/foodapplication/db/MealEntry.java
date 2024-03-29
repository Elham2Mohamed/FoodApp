package com.example.foodapplication.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal_entries")
public class MealEntry {
    public MealEntry() {
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String image;
    private String name;
    private String date;
    private String time;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MealEntry(String image, String name, String date, String time) {
        this.image = image;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
