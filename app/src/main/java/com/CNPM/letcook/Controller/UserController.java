package com.CNPM.letcook.Controller;

import android.support.annotation.NonNull;
import android.util.Log;

import com.CNPM.letcook.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserController {
    private UserModel userModel;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUser;
    private DatabaseReference mDatabase;
    private boolean check = false;

    public UserController() {
        userModel = new UserModel();
        mAuth = FirebaseAuth.getInstance();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public void InfoUserController(UserModel userModel, String userID) {
        userModel.addInfo(userModel, userID);
    }

    public void handdleClickLike(final boolean check, final String idPost) {


        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataUser = dataSnapshot.child(mAuth.getCurrentUser().getUid());
                final UserModel userModel = dataUser.getValue(UserModel.class);
                ArrayList<String> posts = userModel.getLiked();
                if (check) {
//                    ArrayList<String> posts = userModel.getLiked();
                    posts.add(idPost);
                    userModel.setLiked(posts);

                } else {
                    posts.remove(idPost);
                    userModel.setLiked(posts);
                }
                mDatabaseUser.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {


                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mDatabaseUser.child(mAuth.getCurrentUser().getUid()).setValue(userModel);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        return posts.contains(idPost);
    }
//
//    public UserModel getCurrentUser(FirebaseUser user) {
//
//        return user;
//    }



}
