package com.CNPM.letcook.Model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.CNPM.letcook.Adapters.CommentAdapter;
import com.CNPM.letcook.Controller.Interface.HomePageInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DishModel implements Parcelable {
    private String diet, dish_name, dish_id, desc, user_id;
    private UserModel userModel;
    private int likes;



    private List<String> ingredients, dish_pic, making,liked, saved;
    private DatabaseReference nodeRoot;
    private List<CommentModel> commentModels;



    protected DishModel(Parcel in) {
        diet = in.readString();
        dish_name = in.readString();
        dish_id = in.readString();
        desc = in.readString();
        user_id = in.readString();
        userModel = in.readParcelable(UserModel.class.getClassLoader());
        likes = in.readInt();
        ingredients = in.createStringArrayList();
        liked = in.createStringArrayList();
        saved = in.createStringArrayList();
        dish_pic = in.createStringArrayList();
        making = in.createStringArrayList();
        commentModels = in.createTypedArrayList(CommentModel.CREATOR);
    }

    public static final Creator<DishModel> CREATOR = new Creator<DishModel>() {
        @Override
        public DishModel createFromParcel(Parcel in) {
            return new DishModel(in);
        }

        @Override
        public DishModel[] newArray(int size) {
            return new DishModel[size];
        }
    };



    public List<String> getLiked() {
        return liked;
    }

    public void setLiked(List<String> liked) {
        this.liked = liked;
    }

    public List<String> getSaved() {
        return saved;
    }

    public void setSaved(List<String> saved) {
        this.saved = saved;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getMaking() {
        return making;
    }

    public void setMaking(List<String> making) {
        this.making = making;
    }

    public List<CommentModel> getCommentModels() {
        return commentModels;
    }

    List<Bitmap> bitmapList;


    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    public String getDish_id() {
        return dish_id;
    }

    public void setDish_id(String dish_id) {
        this.dish_id = dish_id;
    }

    public void setCommentModels(List<CommentModel> commentModels) {
        this.commentModels = commentModels;
    }

    public List<String> getDish_pic() {
        return dish_pic;
    }

    public void setDish_pic(List<String> dish_pic) {
        this.dish_pic = dish_pic;
    }


    public DishModel() {
        nodeRoot = FirebaseDatabase.getInstance().getReference();

    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    private DataSnapshot dataRoot;

    public void getListDish(final HomePageInterface homePageInterface, final int itemNext, final int itemCurrent) {

        ValueEventListener valueEventListener = new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataRoot = dataSnapshot;
                _getListDish(dataSnapshot, homePageInterface, itemNext, itemCurrent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        if (dataRoot != null) {
            _getListDish(dataRoot, homePageInterface, itemNext, itemCurrent);

        } else {
            nodeRoot.addListenerForSingleValueEvent(valueEventListener);
        }

    }



    private void _getListDish(DataSnapshot dataSnapshot, HomePageInterface homePageInterface, int itemNext, int itemCurrent) {


        //Lấy danh sách món ăn
        DataSnapshot dataSnapshotDish = dataSnapshot.child("dish");
        // dùng vòng for duyệt danh sách món ăn
        int i = 0;
        for (DataSnapshot dataDish : dataSnapshotDish.getChildren()) {
            if (i == itemNext) {
                break;
            }
            if (i < itemCurrent) {
                i++;
                continue;
            }
            i++;
            //lấy tên món ăn bằng getKey()
//            DishModel dishModel = dataDish.getValue(DishModel.class);
            DishModel dishModel = dataDish.getValue(DishModel.class);
            if (dishModel != null) {
                dishModel.setDish_id(dataDish.getKey());
            }
            DataSnapshot dataSnapshotUser = dataSnapshot.child("users").child(dishModel.getUser_id());
            dishModel.setUserModel(dataSnapshotUser.getValue(UserModel.class));

            //lấy danh sách hình ảnh quán ăn theo id
            // duyệt node picture_dish
            // từ key lấy được bên trên duyệt node the theo key đã lấy
            DataSnapshot dataSnapshotDishPicture = dataSnapshot.child("picture_dish").child(dataDish.getKey());

            // tạo ra mảng chứa danh sách ảnh món ăn

            List<String> pictureList = new ArrayList<>();

            //duyệt node và lấy địa chỉ ảnh của món ăn
            for (DataSnapshot picture : dataSnapshotDishPicture.getChildren()) {

                pictureList.add(picture.getValue(String.class));
            }
            dishModel.setDish_pic(pictureList);
            Log.d("check", dishModel.getDish_id());
            DataSnapshot snapshotComment = dataSnapshot.child("comments").child(dishModel.getDish_id());
            List<CommentModel> commentModels = new ArrayList<>();
            for (DataSnapshot valueComment : snapshotComment.getChildren()) {
                CommentModel commentModel = valueComment.getValue(CommentModel.class);
                Log.d("check", commentModel.getUser_id());
                UserModel snapshotUser = dataSnapshot.child("users").child(commentModel.getUser_id()).getValue(UserModel.class);
                commentModel.setUserModel(snapshotUser);
                Log.d("kiem", commentModel.getCmt_content() + "");
                commentModels.add(commentModel);
            }
            dishModel.setCommentModels(commentModels);
            homePageInterface.getListDishModel(dishModel);

        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(diet);
        dest.writeString(dish_name);
        dest.writeString(dish_id);
        dest.writeString(desc);
        dest.writeString(user_id);
        dest.writeParcelable(userModel, flags);
        dest.writeInt(likes);
        dest.writeStringList(ingredients);
        dest.writeStringList(liked);
        dest.writeStringList(saved);
        dest.writeStringList(dish_pic);
        dest.writeStringList(making);
        dest.writeTypedList(commentModels);
    }




}
