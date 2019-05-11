package com.CNPM.letcook.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.CNPM.letcook.Adapters.RecyclerViewAdapter;
import com.CNPM.letcook.Controller.Interface.HomePageInterface;
import com.CNPM.letcook.Model.DishModel;
import com.CNPM.letcook.R;
import com.CNPM.letcook.View.VerticalSpaceItemDecoration;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DishController {
    private Context context;
    private DishModel dishModel;
    private RecyclerViewAdapter recyclerViewAdapter;

    int item = 3;

    public DishController(Context context) {
        this.context = context;
        dishModel = new DishModel();
    }

    public void getListDishController(Context context,final RecyclerView recyclerView, NestedScrollView nestedScrollView) {
        final List<DishModel> dishModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(context,dishModelList, R.layout.layout_custom_dish);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(10));
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        final HomePageInterface homePageInterface = new HomePageInterface() {
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

                            if (dishModel.getBitmapList().size() == dishModel.getDish_pic().size()) {
                                dishModelList.add(dishModel);
                                recyclerViewAdapter.notifyDataSetChanged();
                            }
                        }
                    });

                }
            }
        };
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
                if (nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1) != null) {
                    if (i1 >= (nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1).getMeasuredHeight()) - nestedScrollView.getMeasuredHeight()) {
                        item += 3;
                        dishModel.getListDish(homePageInterface, item, item - 3);
                    }

                }
            }
        });
        dishModel.getListDish(homePageInterface, item, 0);
    }


    public void addDish(DishModel dishModel){
        DatabaseReference nodeDish = FirebaseDatabase.getInstance().getReference().child("dish");

        String dish_id = nodeDish.push().getKey();
        nodeDish.child(dish_id).setValue(dishModel);
    }

}
