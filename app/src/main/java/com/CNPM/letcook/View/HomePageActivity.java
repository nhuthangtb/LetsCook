package com.CNPM.letcook.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.CNPM.letcook.Controller.HomePageController;
import com.CNPM.letcook.R;


public class HomePageActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    HomePageController homePageController;
    NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_homepage);
        recyclerView = findViewById(R.id.recycleView);
        nestedScrollView = findViewById(R.id.NestedScroll);

    }

    @Override
    protected void onStart() {
        super.onStart();
        homePageController = new HomePageController(this);
        homePageController.getListDishController(this, recyclerView, nestedScrollView);
    }
}
