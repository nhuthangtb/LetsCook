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



    public void addComment(){

    }

}
