package com.CNPM.letcook.View.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.CNPM.letcook.Controller.DishController;
import com.CNPM.letcook.Model.DishModel;
import com.CNPM.letcook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;


import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class AddDishFragment extends Fragment implements View.OnClickListener {

    int GALLERY_REQUEST_CODE = 1;


    ImageView imgAddImage;
    EditText editDishname, editDescDish, edAddMaking, edAddIngredient;
    Button btnAddDish, btnDelMaking, btnDelIngredient;
    TextView txtMaking, txtAddIngre, txtAddMaking;
    List<String> Ingredients = new ArrayList<>();
    List<String> Makings = new ArrayList<>();
    LinearLayout layoutIgre, layout, viewAddMaking, viewMaking;
    private DishModel dishModel;
    FirebaseUser user;
    Uri uri;
    List<Uri> imgDish = new ArrayList<>();

//    private DishController dishController = new DishController(getContext());

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.layout_add_dish, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();
//        test = getView().findViewById(R.id.test);

        txtAddMaking = getView().findViewById(R.id.txtAddMaking);
        txtMaking = getView().findViewById(R.id.txtMaking);
        imgAddImage = getView().findViewById(R.id.imgAddImage);
        editDishname = getView().findViewById(R.id.editDishName);
        editDescDish = getView().findViewById(R.id.editDescDish);
        txtAddIngre = getView().findViewById(R.id.txtAddIngre);
        btnAddDish = getView().findViewById(R.id.btnAddDish);
        layoutIgre = getView().findViewById(R.id.layoutIgre);
        layout = getView().findViewById(R.id.layout);
        viewAddMaking = getView().findViewById(R.id.viewAddMaking);
        viewMaking = getView().findViewById(R.id.viewMaking);
        btnDelIngredient = getView().findViewById(R.id.btnDelIngredient);
        btnDelMaking = getView().findViewById(R.id.btnDelMaking);
        edAddMaking = getView().findViewById(R.id.edAddMaking);
        edAddIngredient = getView().findViewById(R.id.edAddIngredient);

        txtAddMaking.setOnClickListener(this);
        txtAddIngre.setOnClickListener(this);
        btnAddDish.setOnClickListener(this);
        imgAddImage.setOnClickListener(this);
    }

    private void clear() {
        Ingredients.removeAll(Ingredients);
//        layoutIgre.removeAllViews();
        Makings.removeAll(Makings);
        viewAddMaking.removeViewInLayout(viewAddMaking);
        editDescDish.setText("");
        edAddMaking.setText("");
        editDishname.setText("");
        edAddIngredient.setText("");
    }


    private void getTextIngredient() {
        for (int i = 0; i < layoutIgre.getChildCount(); i++) {
            View child = layoutIgre.getChildAt(i);
            if (child instanceof LinearLayout) {
                for (int j = 0; j < ((LinearLayout) child).getChildCount(); j++) {
                    View child1 = ((LinearLayout) child).getChildAt(j);
                    if ((child1 instanceof EditText)) {
                        String ingredient = ((EditText) child1).getText().toString();
                        if (!ingredient.isEmpty())
                            Ingredients.add(ingredient);
                    }
                }
            }
        }
    }

    private void getTextMaking() {
        for (int i = 0; i < viewAddMaking.getChildCount(); i++) {
            View child = viewAddMaking.getChildAt(i);
            if (child instanceof LinearLayout) {
                for (int j = 0; j < ((LinearLayout) child).getChildCount(); j++) {
                    View child2 = ((LinearLayout) child).getChildAt(j);
                    if (child2 instanceof LinearLayout) {
                        for (int k = 0; k < ((LinearLayout) child2).getChildCount(); k++) {
                            View child3 = ((LinearLayout) child2).getChildAt(k);
                            if (child3 instanceof EditText) {
                                String making = ((EditText) child3).getText().toString();
                                if (!making.isEmpty()) Makings.add(making);
                            }
                        }
                    }
                }
            }
        }
    }

    private void addDish() {
        dishModel = new DishModel();
        String dishName = editDishname.getText().toString();
        String descDish = editDescDish.getText().toString();
        DishModel dishModel = new DishModel();
        dishModel.setDesc(descDish);
        dishModel.setDish_name(dishName);
        getTextIngredient();
        dishModel.setIngredients(Ingredients);
        dishModel.setUser_id(user.getUid());
        getTextMaking();
        Log.d("mang cach lam chua xoa", Makings + "");
        dishModel.setMaking(Makings);
        dishModel.setLikes(0);
        addDish(dishModel);

        clear();
        Log.d("mang cach lam", Makings + "");
        Log.d("mang nguyen lieu", Ingredients + "");

    }


    public void onAddFieldIngredient(View v) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field_add_ingredient, null);
        layoutIgre.addView(rowView, layoutIgre.getChildCount());
        Button btnDelIngredient = rowView.findViewById(R.id.btnDelIngredient);
        btnDelIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((LinearLayout) rowView.getParent()).removeView(rowView);
            }
        });
    }

    public void onAddFieldMaking(View v) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field_add_making, null);
        viewAddMaking.addView(rowView, viewAddMaking.getChildCount());
        TextView txtNumMaking = rowView.findViewById(R.id.txtNumMaking);
        txtNumMaking.setText(viewAddMaking.getChildCount() + 1 + "");


        Button btnDelMaking = rowView.findViewById(R.id.btnDelMaking);
        if (viewAddMaking.getChildCount() > 0) {
            btnDelMaking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((LinearLayout) rowView.getParent()).removeView(rowView);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.txtAddIngre:
                onAddFieldIngredient(v);
                break;
            case R.id.txtAddMaking:
                onAddFieldMaking(v);
                break;


            case R.id.btnAddDish: {
                addDish();
                break;
            }
            case R.id.imgAddImage:
                pickFromGallery(GALLERY_REQUEST_CODE);
                break;

        }
    }


    private void pickFromGallery(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn hình "), requestCode);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                uri = data.getData();
                imgAddImage.setImageURI(uri);
                imgDish.add(uri);
            }
        }
    }


    public void addDish(DishModel dishModel) {
        DatabaseReference nodeDish = FirebaseDatabase.getInstance().getReference().child("dish");
        DatabaseReference nodePictureDish = FirebaseDatabase.getInstance().getReference().child("picture_dish");

        String dish_id = nodeDish.push().getKey();

        nodeDish.child(dish_id).setValue(dishModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        FirebaseStorage.getInstance().getReference().child("Picture/" + uri.getLastPathSegment()).putFile(uri);
        nodePictureDish.child(dish_id).child("pic_id").setValue(uri.getLastPathSegment());

    }
}


