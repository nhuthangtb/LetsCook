package com.CNPM.letcook.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.CNPM.letcook.Controller.DishController;
import com.CNPM.letcook.R;


public class HomePageFragment extends Fragment {
    RecyclerView recyclerView;

    DishController dishController;
    NestedScrollView nestedScrollView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return  inflater.inflate(R.layout.layout_homepage,container,false);
    }



    @Override
    public void onStart() {
        super.onStart();
        recyclerView = getView().findViewById(R.id.recycleView);
        nestedScrollView = getView().findViewById(R.id.NestedScroll);
        dishController = new DishController(getContext());
        dishController.getListDishController(getContext(), recyclerView, nestedScrollView);
    }
}
