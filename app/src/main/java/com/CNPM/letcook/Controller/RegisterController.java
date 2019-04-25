package com.CNPM.letcook.Controller;

import com.CNPM.letcook.Model.UserModel;

public class RegisterController {
    UserModel userModel;
    public RegisterController(){
        userModel = new UserModel();
    }
    public void InfoUserController(UserModel userModel, String userID){
        userModel.addInfo(userModel,userID);
    }

}
