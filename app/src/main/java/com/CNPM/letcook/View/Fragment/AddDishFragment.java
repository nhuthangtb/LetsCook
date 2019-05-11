package com.CNPM.letcook.View.Fragment;

import android.content.Context;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;


public class AddDishFragment extends Fragment implements View.OnClickListener {
    ImageButton btnAddImg;
    ImageView imgDelete, imgDelMaking;
    EditText editDishname, editDescDish, edAddMaking, edAddIngredient;
    Button btnAddDish;
    TextView txtMaking, txtAddIngre, txtAddMaking;
    List<String> Ingredients = new ArrayList<>();
    List<String> Makings = new ArrayList<>();
    LinearLayout layoutIgre, layout, viewAddMaking, viewMaking, test;
    private DishModel dishModel;
    FirebaseUser user ;
    private DishController dishController = new DishController(getContext());

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.layout_add_dish, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();
        test = getView().findViewById(R.id.test);
        txtAddMaking = getView().findViewById(R.id.txtAddMaking);
        txtMaking = getView().findViewById(R.id.txtMaking);
        btnAddImg = getView().findViewById(R.id.btnAddAvatar);
        editDishname = getView().findViewById(R.id.editDishName);
        editDescDish = getView().findViewById(R.id.editDescDish);
        txtAddIngre = getView().findViewById(R.id.txtAddIngre);
        btnAddDish = getView().findViewById(R.id.btnAddDish);
        layoutIgre = getView().findViewById(R.id.layoutIgre);
        layout = getView().findViewById(R.id.layout);
        viewAddMaking = getView().findViewById(R.id.viewAddMaking);
        viewMaking = getView().findViewById(R.id.viewMaking);
        imgDelete = getView().findViewById(R.id.img_delete);
        imgDelMaking = getView().findViewById(R.id.imgDelMaking);
        edAddMaking = getView().findViewById(R.id.edAddMaking);
        txtAddMaking.setOnClickListener(this);
        txtAddIngre.setOnClickListener(this);
        btnAddDish.setOnClickListener(this);

    }

    private void clear () {
        Ingredients.removeAll(Ingredients);
//        layoutIgre.removeAllViews();
        Makings.removeAll(Makings);
        viewAddMaking.removeViewInLayout(viewAddMaking);
        editDescDish.setText("");
        edAddMaking.setText("");
//        editDishname.setText("");
//        edAddIngredient.setText("");
    }


    private void getTextIngredient () {
        for (int i = 0; i < layoutIgre.getChildCount(); i++) {
            View child = layoutIgre.getChildAt(i);
            if (child instanceof LinearLayout) {
                for (int j = 0; j < ((LinearLayout) child).getChildCount(); j++) {
                    View child1 = ((LinearLayout) child).getChildAt(j);
                    if ((child1 instanceof EditText)) {
                        String ingredient = ((EditText) child1).getText().toString();
                        if(!ingredient.isEmpty())
                            Ingredients.add(ingredient);
                    }
                }
            }
        }
    }

    private void getTextMaking () {
        for (int i = 0; i < viewAddMaking.getChildCount(); i++) {
            View child = viewAddMaking.getChildAt(i);
            if (child instanceof LinearLayout) {
                for(int j = 0; j < ((LinearLayout) child).getChildCount(); j++){
                    View child2 = ((LinearLayout) child).getChildAt(j);
                    if(child2 instanceof  LinearLayout){
                        for(int k =0; k<((LinearLayout) child2).getChildCount(); k++){
                            View child3 = ((LinearLayout) child2).getChildAt(k);
                            if(child3 instanceof EditText){
                                String making = ((EditText)child3).getText().toString();
                                if(!making.isEmpty()) Makings.add(making);
                            }
                        }
                    }
                }
            }
        }
    }

    private void addDish () {
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
        dishModel.setMaking(Makings);
        dishModel.setLikes(0);
        dishController.addDish(dishModel);

        clear();
        Log.d("mang cach lam", Makings + "");
        Log.d("mang nguyen lieu", Ingredients + "");

    }


    public void onAddFieldIngredient (View v){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field_add_ingredient, null);
        layoutIgre.addView(rowView, layoutIgre.getChildCount());
        ImageView imgDelete = rowView.findViewById(R.id.img_delete);
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((LinearLayout) rowView.getParent()).removeView(rowView);
            }
        });
    }

    public void onAddFieldMaking (View v){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field_add_making, null);
        viewAddMaking.addView(rowView, viewAddMaking.getChildCount());
        TextView txtNumMaking = rowView.findViewById(R.id.txtNumMaking);
        txtNumMaking.setText(viewAddMaking.getChildCount() + "");
        ImageView imgDelMaking = rowView.findViewById(R.id.imgDelMaking);
        if (viewAddMaking.getChildCount() > 1) {
            imgDelMaking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((LinearLayout) rowView.getParent()).removeView(rowView);
                }
            });
        }
    }

    @Override
    public void onClick (View v){
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

        }
    }
}


