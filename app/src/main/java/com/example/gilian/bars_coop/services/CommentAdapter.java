package com.example.gilian.bars_coop.services;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gilian.bars_coop.Entity.Comment;
import com.example.gilian.bars_coop.R;

import java.util.List;

/**
 * Created by JérémieRoth on 29/08/2017.
 */

public class CommentAdapter extends ArrayAdapter<Comment>{
    public List<Comment> commentList;
    private Activity activity;
    private int layout;

    public CommentAdapter(Activity activity, int layout, List<Comment> commentList ){
        super(activity, layout, commentList);
        this.commentList = commentList;
        this.activity = activity;
        this.layout = layout;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view= convertView;
        if(convertView == null){
            LayoutInflater inflater = (this.activity).getLayoutInflater();
            view = inflater.inflate(this.layout, parent , false);
        }

        TextView name = (TextView) view.findViewById(R.id.name_comment);
        TextView commentTxt = (TextView) view.findViewById(R.id.body_comment);
        name.setText(commentList.get(position).getUser().getUsername());
        commentTxt.setText(commentList.get(position).getComment());
        return view;
    }
}
