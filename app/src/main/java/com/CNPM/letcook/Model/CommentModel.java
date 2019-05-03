package com.CNPM.letcook.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CommentModel implements Parcelable {

    private UserModel userModel;
    private String cmt_content;
    private String user_id;
    protected CommentModel(Parcel in) {

        userModel = in.readParcelable(UserModel.class.getClassLoader());
        cmt_content = in.readString();
        user_id = in.readString();
    }

    public static final Creator<CommentModel> CREATOR = new Creator<CommentModel>() {
        @Override
        public CommentModel createFromParcel(Parcel in) {
            return new CommentModel(in);
        }

        @Override
        public CommentModel[] newArray(int size) {
            return new CommentModel[size];
        }
    };

    public String getCmt_content() {
        return cmt_content;
    }

    public void setCmt_content(String cmt_content) {
        this.cmt_content = cmt_content;
    }



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public CommentModel() {

    }


    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeParcelable(userModel, flags);
        dest.writeString(cmt_content);
        dest.writeString(user_id);
    }



}
