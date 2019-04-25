package com.CNPM.letcook.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.CNPM.letcook.Controller.RegisterController;
import com.CNPM.letcook.Model.UserModel;
import com.CNPM.letcook.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InfoActivity extends AppCompatActivity {
    Button btnAddAvatar, btnChangeName;
    EditText edName;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference nodeRoot = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_info_user);
        btnAddAvatar = findViewById(R.id.btnAddAvatar);
        edName = findViewById(R.id.edName);
        btnChangeName = findViewById(R.id.btnChangeName);

    }

    @Override
    protected void onStart() {
        super.onStart();

        edName.setText(user.getEmail());
        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUserName(edName.getText().toString());
            }
        });
    }
    public void changeUserName( String name){
        nodeRoot.child("users").child(user.getUid()).child("name").setValue(name);
    }
}
