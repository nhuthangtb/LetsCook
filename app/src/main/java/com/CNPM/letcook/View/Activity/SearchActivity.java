package com.CNPM.letcook.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.CNPM.letcook.Adapters.AdapterSearch;
import com.CNPM.letcook.Model.DishModel;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private TextView txtDishName, txtDesc;
    private ImageView imgDish;
    private RecyclerView recyclerView;
    private AdapterSearch adapter;

    DatabaseReference dbDish;


}
