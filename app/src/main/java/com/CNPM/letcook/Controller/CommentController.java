package com.CNPM.letcook.Controller;

import com.CNPM.letcook.Model.CommentModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommentController {
    private DatabaseReference nodeComment;
    private CommentModel commentModel;
    private FirebaseUser user;

    public CommentController() {
        nodeComment = FirebaseDatabase.getInstance().getReference().child("comments");
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void addComment(String content, String dishID ){
            commentModel = new CommentModel();

            DatabaseReference nodeDishID = nodeComment.child(dishID);
            String commentID = nodeDishID.push().getKey();
            DatabaseReference nodeCommentID = nodeDishID.child(commentID);
            commentModel.setCmt_content(content);
            commentModel.setUser_id(user.getUid());
            nodeCommentID.setValue(commentModel);

    }

}
