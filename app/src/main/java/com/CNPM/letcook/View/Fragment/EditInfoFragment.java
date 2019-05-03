package com.CNPM.letcook.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.CNPM.letcook.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditInfoFragment extends Fragment {
    Button btnAddAvatar, btnChangeName;
    EditText edName;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference nodeRoot = FirebaseDatabase.getInstance().getReference();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return  inflater.inflate(R.layout.edit_info_user,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
//        setContentView(R.layout.edit_info_user);
        btnAddAvatar = getView().findViewById(R.id.btnAddAvatar);
        edName = getView().findViewById(R.id.edName);
        btnChangeName = getView().findViewById(R.id.btnChangeName);
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
