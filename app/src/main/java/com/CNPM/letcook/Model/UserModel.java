package com.CNPM.letcook.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserModel implements Parcelable {

    private String name, pic_profile, user_id;
    private ArrayList<String> liked, saved;
    private DatabaseReference dataUserNode;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUser;

    protected UserModel(Parcel in) {
        name = in.readString();
        pic_profile = in.readString();
        user_id = in.readString();
        liked = in.createStringArrayList();
        saved = in.createStringArrayList();

    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public UserModel() {
        dataUserNode = FirebaseDatabase.getInstance().getReference().child("users");
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public ArrayList<String> getLiked() {
        return liked;
    }

    public void setLiked(ArrayList<String> liked) {
        this.liked = liked;
    }

    public ArrayList<String> getSaved() {
        return saved;
    }

    public void setSaved(ArrayList<String> saved) {
        this.saved = saved;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic_profile() {
        return pic_profile;
    }

    public void setPic_profile(String pic_profile) {
        this.pic_profile = pic_profile;
    }

    public void addInfo(UserModel userModel, String userID) {
        dataUserNode.child(userID).setValue(userModel, userID);
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(pic_profile);
        dest.writeString(user_id);
        dest.writeStringList(liked);
        dest.writeStringList(saved);
    }
}
