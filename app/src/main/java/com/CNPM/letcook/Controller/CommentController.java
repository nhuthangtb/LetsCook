package com.CNPM.letcook.Controller;

import com.CNPM.letcook.Model.CommentModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommentController {
    DatabaseReference nodeComment = FirebaseDatabase.getInstance().getReference().child("comments");
    CommentModel commentModel;
    public void addComment(String content,String dishID ){
            commentModel = new CommentModel();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference nodeDishID = nodeComment.child(dishID);
            String commentID = nodeDishID.push().getKey();
            DatabaseReference nodeCommentID = nodeDishID.child(commentID);
            commentModel.setCmt_content(content);
            commentModel.setUser_id(user.getUid());
            nodeCommentID.setValue(commentModel);


    }

}
