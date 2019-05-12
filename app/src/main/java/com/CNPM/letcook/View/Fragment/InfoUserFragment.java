package com.CNPM.letcook.View.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.CNPM.letcook.Model.UserModel;
import com.CNPM.letcook.R;
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

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoUserFragment extends Fragment {

    CircleImageView img_profile;
    TextView txtUserName;
    Button btnEditInfo;
    UserModel userModel;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference nodeRoot = FirebaseDatabase.getInstance().getReference();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return  inflater.inflate(R.layout.layout_user,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        img_profile = getView().findViewById(R.id.img_profile);

        btnEditInfo = getView().findViewById(R.id.btnEditInfo);
        txtUserName = getView().findViewById(R.id.txtUserName);
        //displayInfo();
        //String userName = nodeUser.child("name").getValue();
        //txtUserName.setText(userName);
    }

    public void displayInfo(){

//        DatabaseReference nodePicProfile = nodeUser.child(user.getUid()).child("pic_profile");
        DatabaseReference nodeUser = nodeRoot.child("users");
        nodeUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        String linkImg = nodePicProfile.getKey();
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Users").child(linkImg);
//        long ONE_MEGABYTE = 1024*1024;
//        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener((new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                img_profile.setImageBitmap(bitmap);
//            }
//        }));
//
//
    }
}
