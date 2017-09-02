package com.example.gilian.bars_coop;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by JérémieRoth on 01/09/2017.
 */

public class AddCommentDialog extends Dialog{
    TextView commentTxt;
    Button cancelButton;
    Button okButton;
    RatingBar ratingBar;

    public AddCommentDialog(@NonNull Context context) {
        super(context);
        this.setContentView(R.layout.add_comment_dialog);
        commentTxt = (EditText) findViewById(R.id.comment_text);
        cancelButton = (Button) findViewById(R.id.cancel_add_comment);
        okButton = (Button) findViewById(R.id.ok_add_comment);
        ratingBar = (RatingBar) findViewById(R.id.comment_rating_bar);
    }

    public TextView getCommentTxt() {
        return commentTxt;
    }

    public void setCommentTxt(TextView commentTxt) {
        this.commentTxt = commentTxt;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
    }

    public Button getOkButton() {
        return okButton;
    }

    public void setOkButton(Button okButton) {
        this.okButton = okButton;
    }

    public RatingBar getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(RatingBar ratingBar) {
        this.ratingBar = ratingBar;
    }
}
