package com.CNPM.letcook.Controller;

import android.support.annotation.NonNull;

import com.CNPM.letcook.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserController {
    private UserModel userModel;
    private FirebaseAuth firebaseAuth;
    public UserController(){
        userModel = new UserModel();
    }
    public void InfoUserController(UserModel userModel, String userID){
        userModel.addInfo(userModel,userID);
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

//    public void Register(final String email, String password){
//        firebaseAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
////                            progressDialog.dismiss();
//                            userModel.setName(email);
//                            userModel.setPic_profile("pic_profile.png");
//                            String uid = task.getResult().getUser().getUid();
//                            InfoUserController(getUserModel(), uid);
////                            Toast.makeText(RegisterActivity.CONTEXT_IGNORE_SECURITY, getString(R.string.register_successful), Toast.LENGTH_SHORT)
////                                    .show();
//                        }
//                    }
//                });
//
//    }

    public void addComment(){

    }

}
