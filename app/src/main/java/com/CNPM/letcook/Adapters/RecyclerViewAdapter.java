package com.CNPM.letcook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.CNPM.letcook.Model.DishModel;
import com.CNPM.letcook.Model.UserModel;
import com.CNPM.letcook.R;
import com.CNPM.letcook.View.DishActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    List<DishModel> dishModelList;
    int resource;
    Context context;
    List<String> likedList, savedList;

    public RecyclerViewAdapter(Context context, List<DishModel> dishModelList, int resource) {
        this.dishModelList = dishModelList;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder recyclerViewHolder, int i) {
        final DishModel dishModel = dishModelList.get(i);
        recyclerViewHolder.txtDishName.setText(dishModel.getDish_name());
        recyclerViewHolder.txtDescription.setText(dishModel.getDesc());

        UserModel userModel = dishModel.getUserModel();


        recyclerViewHolder.txtUserName.setText(userModel.getName());



        setImgComment(recyclerViewHolder.img_profile,userModel.getPic_profile());
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
    private void setImgComment(final CircleImageView circleImageView, String linkImg){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Users").child(linkImg);
        long ONE_MEGABYTE = 1024*1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener((new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return dishModelList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserName, txtDishName, txtDescription;
        ImageView imgDish;
        CircleImageView img_profile;
        CardView cardView;
        ImageButton btnLike;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtDishName = itemView.findViewById(R.id.txtDishName);
            img_profile = itemView.findViewById(R.id.img_profile);
            txtDescription = itemView.findViewById(R.id.txtDesciption);
            imgDish = itemView.findViewById(R.id.imgDish);
            cardView = itemView.findViewById(R.id.cardView);
            btnLike = itemView.findViewById(R.id.btnLike);
        }
    }
}
