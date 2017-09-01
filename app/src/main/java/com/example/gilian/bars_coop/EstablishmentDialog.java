package com.example.gilian.bars_coop;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.gilian.bars_coop.Entity.Comment;
import com.example.gilian.bars_coop.Entity.Establishment;
import com.example.gilian.bars_coop.Entity.Event;

import java.util.List;

/**
 * Created by JérémieRoth on 27/08/2017.
 */

public class EstablishmentDialog extends Dialog {
    Activity activity;
    TextView barName;
    GridView eventGrid;
    GridView commentGrid;
    Button addevent;
    Button addComment;
    List<Comment> commentList;
    List<Event> eventList;


    //public EstablishmentDialog(@NonNull Context context, Establishment establishment, List<Comment> commentList, List<Event> eventList, MapActivity activity) {
    public EstablishmentDialog(Establishment establishment, List<Comment> commentList, List<Event> eventList, MapActivity activity) {
        super(activity);
        this.setContentView(R.layout.establishment_dialog);
        barName = (TextView) findViewById(R.id.name_establishment);
        eventGrid = (GridView) findViewById(R.id.event_grid);
        commentGrid = (GridView) findViewById(R.id.comment_grid);
        addevent = (Button) findViewById(R.id.add_event);
        addComment = (Button) findViewById(R.id.add_comment);
        this.commentList = commentList;
        this.eventList = eventList;
        barName.setText(establishment.getName());
        this.activity=activity;
        EventAdapter eventAdapter = new EventAdapter(activity, R.layout.item_event, eventList);
        CommentAdapter commentAdapter = new CommentAdapter(activity, R.layout.item_comment, commentList);
        eventGrid.setAdapter(eventAdapter);
        commentGrid.setAdapter(commentAdapter);

    }

    public void updateEvent(Event event){
        eventList.add(event);
        EventAdapter eventAdapter = new EventAdapter(activity, R.layout.item_event, eventList);
        eventGrid.setAdapter(eventAdapter);

    }
    public void updateComment(Comment comment){
        commentList.add(comment);
        CommentAdapter commentAdapter = new CommentAdapter(activity, R.layout.item_comment, commentList);
        commentGrid.setAdapter(commentAdapter);
    }

    public TextView getBarName() {
        return barName;
    }

    public void setBarName(TextView barName) {
        this.barName = barName;
    }

    public GridView getCommentGrid() {
        return commentGrid;
    }

    public void setCommentGrid(GridView commentGrid) {
        this.commentGrid = commentGrid;
    }

    public Button getAddComment() {
        return addComment;
    }

    public void setAddComment(Button addComment) {
        this.addComment = addComment;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public GridView getEventGrid() {
        return eventGrid;
    }

    public void setEventGrid(GridView eventGrid) {
        this.eventGrid = eventGrid;
    }

    public Button getAddevent() {
        return addevent;
    }

    public void setAddevent(Button addevent) {
        this.addevent = addevent;
    }
}
