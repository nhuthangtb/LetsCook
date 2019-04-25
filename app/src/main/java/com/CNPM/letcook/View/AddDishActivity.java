package com.CNPM.letcook.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.CNPM.letcook.Model.DishModel;
import com.CNPM.letcook.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;


public class AddDishActivity extends AppCompatActivity {
    ImageButton btnAddImg;
    EditText editDishname, editDescDish, edAddMaking, edAddIngredient;
    Button btnAddDish, btnAddMaking, btnAddIgre;
    TextView txtMaking;
    List<String> Ingredients = new ArrayList<>();
    List<String> Makings = new ArrayList<>();
    LinearLayout layoutIgre, layout, viewAddMaking, viewMaking;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_dish);
        edAddIngredient = findViewById(R.id.edAddIngredient);
        edAddMaking = findViewById(R.id.edAddMaking);
        txtMaking = findViewById(R.id.txtMaking);
        btnAddImg = findViewById(R.id.btnAddAvatar);
        editDishname = findViewById(R.id.editDishName);
        editDescDish = findViewById(R.id.editDescDish);
        btnAddIgre = findViewById(R.id.btnAddIgre);
        btnAddDish = findViewById(R.id.btnAddDish);
        btnAddMaking = findViewById(R.id.btnAddMaking);
        layoutIgre = findViewById(R.id.layoutIgre);
        layout = findViewById(R.id.layout);
        viewAddMaking = findViewById(R.id.viewAddMaking);
        viewMaking = findViewById(R.id.viewMaking);

        btnAddMaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createViewAddMaking(viewAddMaking);

            }
        });
        btnAddIgre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createViewAddIngredient(layoutIgre);
                edAddIngredient.setText("");
            }
        });
        btnAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < layoutIgre.getChildCount(); i++) {
                    View child = layoutIgre.getChildAt(i);
                    if (child instanceof LinearLayout) {
                        for (int j = 0; j < ((LinearLayout) child).getChildCount(); j++) {
                            View child1 = ((LinearLayout) child).getChildAt(j);
                            if (!(child1 instanceof Button)) {
                                TextView textView = (TextView) child1;
                                Ingredients.add(textView.getText().toString());
                            }
                        }
                    }
                }
                for (int i = 0; i < viewAddMaking.getChildCount(); i++) {
                    View child = viewAddMaking.getChildAt(i);
                    if (child instanceof LinearLayout) {
                        for (int j = 0; j < ((LinearLayout) child).getChildCount(); j++) {
                            View child1 = ((LinearLayout) child).getChildAt(j);
                            if (!(child1 instanceof Button)) {
                                TextView textView = (TextView) child1;
                                Makings.add(textView.getText().toString());
                            }
                        }
                    }
                }
                if (edAddIngredient.getText().toString().equals("")
                        || editDishname.getText().toString().equals("")
                        || editDescDish.getText().toString().equals(""))
                    Toast.makeText(AddDishActivity.this, "Điền đầy đủ thông tin món ăn", Toast.LENGTH_SHORT).show();
                else {
                    addDish();
                    clear();
                }
            }
        });

    }

    private void clear() {
        Ingredients.removeAll(Ingredients);
        layoutIgre.removeAllViews();
        Makings.removeAll(Makings);
        viewAddMaking.removeAllViews();
        editDescDish.setText("");
        edAddMaking.setText("");
        editDishname.setText("");
        edAddIngredient.setText("");
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    private void addDish() {
        String dishName = editDishname.getText().toString();
        String descDish = editDescDish.getText().toString();
        DatabaseReference nodeDish = FirebaseDatabase.getInstance().getReference().child("dish");
        String dish_id = nodeDish.push().getKey();
        DishModel dishModel = new DishModel();
        dishModel.setDesc(descDish);
        dishModel.setDish_name(dishName);
        dishModel.setIngredients(Ingredients);
        dishModel.setMaking(Makings);
        dishModel.setLikes(0);
        nodeDish.child(dish_id).setValue(dishModel);
    }
    private void createViewAddIngredient(ViewGroup viewGroup) {

        final LinearLayout linearLayout = new LinearLayout(layout.getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        viewGroup.addView(linearLayout);
        final TextView textView = new TextView(linearLayout.getContext());
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT + 815, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params1);
        textView.setText(edAddIngredient.getText().toString());
        Button btn = new Button(linearLayout.getContext());
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btn.setLayoutParams(params2);
        btn.setText("Xóa");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < Ingredients.size(); i++) {
                    if (Ingredients.get(i).equals(textView.getText().toString())) {
                        Ingredients.remove(i);
                        break;
                    }
                }
                layoutIgre.removeView(linearLayout);
            }
        });
        linearLayout.addView(textView);
        linearLayout.addView(btn);

    }

    private void createViewAddMaking(ViewGroup viewGroup) {
        if (Makings.size() > 0) {
            for (int i = 0; i < Makings.size(); i++) {
                if (edAddMaking.getText().toString().trim().equals(Makings.get(i))) {
                    Toast.makeText(AddDishActivity.this, "Cách làm đã có", Toast.LENGTH_SHORT).show();

                }
            }
        }
        final LinearLayout linearLayout = new LinearLayout(viewMaking.getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        viewGroup.addView(linearLayout);
        final TextView textView = new TextView(linearLayout.getContext());
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT + 815, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params1);
        textView.setText(edAddMaking.getText().toString());
        Button btn = new Button(linearLayout.getContext());
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT + 100, ViewGroup.LayoutParams.WRAP_CONTENT + 100);
        btn.setLayoutParams(params2);
        btn.setText("Xóa");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < Makings.size(); i++) {
                    if (Makings.get(i).equals(textView.getText().toString())) {
                        Makings.remove(i);
                        break;
                    }
                }
                viewAddMaking.removeView(linearLayout);
            }
        });
        linearLayout.addView(textView);
        linearLayout.addView(btn);

    }


}


