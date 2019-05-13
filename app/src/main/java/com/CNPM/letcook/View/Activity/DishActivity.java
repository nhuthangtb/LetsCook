package com.CNPM.letcook.View.Activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.CNPM.letcook.Adapters.AdapterMaking;
import com.CNPM.letcook.Adapters.CommentAdapter;
import com.CNPM.letcook.Controller.CommentController;
import com.CNPM.letcook.Controller.Interface.HomePageInterface;
import com.CNPM.letcook.Model.DishModel;
import com.CNPM.letcook.R;
import com.CNPM.letcook.View.VerticalSpaceItemDecoration;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class DishActivity extends AppCompatActivity implements HomePageInterface {
    TextView txtDishName, txtDesc, txtTitleIngre, txtIngredients, txtTitleMaking, txtTitleComment;
    EditText edComment;
    ImageButton btnSend;
    ImageView imgDish;
    DishModel dishModel;
    RecyclerView recyclerViewComment, recyclerViewMaking;
    CommentAdapter commentAdapter;
    AdapterMaking adapterMaking;
    CommentController commentController;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dish);

        dishModel = getIntent().getParcelableExtra("dish");
        txtDishName = findViewById(R.id.txtDishName);
        txtDesc = findViewById(R.id.txtDesc);
        txtTitleIngre = findViewById(R.id.txtTitleIngre);
        txtIngredients = findViewById(R.id.txtIngredients);
        txtTitleMaking = findViewById(R.id.txtTitleMaking);
        txtTitleComment = findViewById(R.id.txtTitleComment);
        imgDish = findViewById(R.id.imgDish);
        recyclerViewComment = findViewById(R.id.recycleViewComment);
        recyclerViewMaking = findViewById(R.id.recycleViewMaking);
        edComment = findViewById(R.id.edComment);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thêm comment
                String comment = edComment.getText().toString();
                commentController = new CommentController();
                commentController.addComment(comment,dishModel.getDish_id(),getApplicationContext());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Hiển thị thông tin món ăn
        getListDishModel(dishModel);
        txtDishName.setText(dishModel.getDish_name());
        txtTitleComment.setText(R.string.title_comment);
        txtDesc.setText(dishModel.getDesc());
        txtTitleMaking.setText(R.string.title_Making);

        adapterMaking = new AdapterMaking(dishModel.getMaking());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMaking.addItemDecoration(new VerticalSpaceItemDecoration(10));

        recyclerViewMaking.setLayoutManager(layoutManager);
        recyclerViewMaking.setAdapter(adapterMaking);
        txtTitleIngre.setText(R.string.title_ingredients);
        List<String> Ingredients = dishModel.getIngredients();
        String  ingre = "";
        for (String item : Ingredients) {
            ingre += "-" + item + ";\n";
        }
        txtIngredients.setText(ingre);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);
        recyclerViewComment.setLayoutManager(layoutManager1);
        commentAdapter = new CommentAdapter(this, R.layout.layout_custom_comment, dishModel.getCommentModels());
        recyclerViewComment.setAdapter(commentAdapter);
        commentAdapter.notifyDataSetChanged();



    }


    @Override
    public void getListDishModel(final DishModel dishModel) {
        final List<Bitmap> bitmaps = new ArrayList<>();
        for (String linkImg : dishModel.getDish_pic()) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child("Picture/").child(linkImg);
            long ONE_MEGABYTE = 1024 * 1024;
            storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmaps.add(bitmap);
                    dishModel.setBitmapList(bitmaps);

                }
            });


        }
    }

}

