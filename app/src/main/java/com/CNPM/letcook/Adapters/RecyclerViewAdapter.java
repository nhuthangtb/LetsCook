package com.CNPM.letcook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.CNPM.letcook.Controller.CommentController;
import com.CNPM.letcook.Controller.DishController;
import com.CNPM.letcook.Controller.UserController;
import com.CNPM.letcook.Model.DishModel;
import com.CNPM.letcook.Model.UserModel;
import com.CNPM.letcook.R;
import com.CNPM.letcook.View.Activity.DishActivity;
import com.CNPM.letcook.View.Fragment.HomePageFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private List<DishModel> dishModelList;
    private int resource;
    private Context context;
    private CommentController commentController;
    private UserController userController;
    private DatabaseReference mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("users");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    public RecyclerViewAdapter(Context context, List<DishModel> dishModelList, int resource) {
        this.dishModelList = dishModelList;
        this.resource = resource;
        this.context = context;


    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder recyclerViewHolder, int i) {
        final DishModel dishModel = dishModelList.get(i);
        recyclerViewHolder.txtDishName.setText(dishModel.getDish_name());
        recyclerViewHolder.txtDescription.setText(dishModel.getDesc());

        final UserModel userModel = dishModel.getUserModel();


        recyclerViewHolder.txtUserName.setText(userModel.getName());


        //xử lý người dùng ấn like và save
//        boolean check ;
//        check = recyclerViewHolder.checkBoxLike.isChecked();
//        boolean check = userController.isLike("dish_id_5");
//        if(check)recyclerViewHolder.checkBoxLike.setChecked(true);
////        else recyclerViewHolder.checkBoxLike.setChecked(false);
////        recyclerViewHolder.checkBoxLike.setChecked(true);
//        Log.d("check",userController.isLike(dishModel.getDish_id())+"");


        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataUser = dataSnapshot.child(mAuth.getCurrentUser().getUid());
                UserModel userModelCurrent = dataUser.getValue(UserModel.class);
                if (userModelCurrent.getLiked().contains(dishModel.getDish_id()))
                    recyclerViewHolder.checkBoxLike.setChecked(true);
                else recyclerViewHolder.checkBoxLike.setChecked(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        recyclerViewHolder.checkBoxLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userController = new UserController();
                userController.handdleClickLike(recyclerViewHolder.checkBoxLike.isChecked(), dishModel.getDish_id());

            }
        });


        // set sự kiện bình luận tại hompage
        recyclerViewHolder.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String comment = recyclerViewHolder.edComment.getText().toString();
                if (!comment.isEmpty()) {
                    commentController = new CommentController();
                    commentController.addComment(comment, dishModel.getDish_id());

                } else {
                    recyclerViewHolder.edComment.setError("Comment trống nè hihi!!!");
                    recyclerViewHolder.edComment.requestFocus();
                }
            }
        });

        //set avatar
        setImgComment(recyclerViewHolder.img_profile, userModel.getPic_profile());
        if (dishModel.getBitmapList().size() > 0) {
            recyclerViewHolder.imgDish.setImageBitmap(dishModel.getBitmapList().get(0));
        }
        recyclerViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDish = new Intent(context, DishActivity.class);
                iDish.putExtra("dish", dishModel);
                context.startActivity(iDish);
            }
        });
    }


    private void setImgComment(final CircleImageView circleImageView, String linkImg) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Users").child(linkImg);
        long ONE_MEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener((new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return dishModelList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserName, txtDishName, txtDescription, txtComment;
        ImageView imgDish;
        CircleImageView img_profile;
        CardView cardView;
        ImageButton btnSend;
        EditText edComment;
        CheckBox checkBoxLike, checkBoxSave;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtDishName = itemView.findViewById(R.id.txtDishName);
            img_profile = itemView.findViewById(R.id.img_profile);
            txtDescription = itemView.findViewById(R.id.txtDesciption);
            imgDish = itemView.findViewById(R.id.imgDish);
            cardView = itemView.findViewById(R.id.cardView);
            checkBoxLike = itemView.findViewById(R.id.checkBoxLike);
            btnSend = itemView.findViewById(R.id.btnSend);
            edComment = itemView.findViewById(R.id.edComment);
            txtComment = itemView.findViewById(R.id.txtComment);
            checkBoxSave = itemView.findViewById(R.id.checkBoxSave);
        }
    }

}
