package com.CNPM.letcook.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CommentModel implements Parcelable {
    int like;
    UserModel userModel;
    String cmt_content;

    public String getCmt_content() {
        return cmt_content;
    }

    public void setCmt_content(String cmt_content) {
        this.cmt_content = cmt_content;
    }

    String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public CommentModel() {

    }

    protected CommentModel(Parcel in) {
        like = in.readInt();
        cmt_content = in.readString();
        userModel = in.readParcelable(UserModel.class.getClassLoader());
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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
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
        dest.writeInt(like);
        dest.writeString(cmt_content);
        dest.writeParcelable(userModel,flags);
    }
}
