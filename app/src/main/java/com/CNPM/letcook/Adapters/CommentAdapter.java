package com.CNPM.letcook.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.CNPM.letcook.Model.CommentModel;
import com.CNPM.letcook.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    Context context;
    int layout;
    List<CommentModel> commentModels;

    public CommentAdapter(Context context, int layout, List<CommentModel> commentModels) {
        this.context = context;
        this.layout = layout;
        this.commentModels = commentModels;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img_profile;
        TextView txtUserName, txtContentComment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_profile = itemView.findViewById(R.id.img_profile);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtContentComment = itemView.findViewById(R.id.txtContentComment);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CommentModel commentModel = commentModels.get(i);
        viewHolder.txtUserName.setText(commentModel.getUserModel().getName());
        viewHolder.txtContentComment.setText(commentModel.getCmt_content());
        setImgComment(viewHolder.img_profile,commentModel.getUserModel().getPic_profile());
    }


    @Override
    public int getItemCount() {
        int comments = commentModels.size();
        if(comments > 5){
            return 5;
        }
        else return commentModels.size();
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

}
