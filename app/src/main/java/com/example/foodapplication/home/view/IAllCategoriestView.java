package com.example.foodapplication.home.view;

import com.example.foodapplication.Model.Categories;

import java.util.List;

public interface IAllCategoriestView {
    public void showData(List<Categories> categories);
    public  void ShowErrMsg(String error);
}
