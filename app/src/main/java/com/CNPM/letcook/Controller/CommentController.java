package com.CNPM.letcook.Controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.CNPM.letcook.Model.CommentModel;
import com.CNPM.letcook.View.Activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    public void addComment(String content, String dishID, final Context context ){
            commentModel = new CommentModel();

            DatabaseReference nodeDishID = nodeComment.child(dishID);
            String commentID = nodeDishID.push().getKey();
            DatabaseReference nodeCommentID = nodeDishID.child(commentID);
            commentModel.setCmt_content(content);
            commentModel.setUser_id(user.getUid());
            nodeCommentID.setValue(commentModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(context,"Bình luận thành ",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(context,"đã gửi",Toast.LENGTH_LONG).show();
                    }
                }
            });

    }

}
