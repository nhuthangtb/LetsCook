package com.CNPM.letcook.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.CNPM.letcook.Controller.DishController;
import com.CNPM.letcook.Model.UserModel;
import com.CNPM.letcook.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomePageFragment extends Fragment {
    RecyclerView recyclerView;

    DishController dishController;
    NestedScrollView nestedScrollView;
    private static FirebaseUser user;
    private static DatabaseReference mDatabaseUser;
    private DatabaseReference mDatabase;



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
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


}
