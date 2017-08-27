package com.example.gilian.bars_coop.services.Exemples;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.gilian.bars_coop.Entity.Establishment;
import com.example.gilian.bars_coop.R;

/**
 * Created by JérémieRoth on 27/08/2017.
 */

public class EstablishmentDialog extends Dialog {
    TextView barName;
    GridView drinkGrid;
    GridView commentGrid;
    Button addDrink;
    Button addComment;

    public EstablishmentDialog(@NonNull Context context, Establishment establishment) {
        super(context);
        this.setContentView(R.layout.establishment_dialog);
        barName = (TextView) findViewById(R.id.name_establishment);
        //drinkGrid = (GridView) findViewById(R.id.drink_grid);
        //commentGrid = (GridView) findViewById(R.id.comment_grid);
        addDrink = (Button) findViewById(R.id.add_drink);
        //addComment = (Button) findViewById(R.id.add_comment);

        barName.setText(establishment.getName());
    }

    public TextView getBarName() {
        return barName;
    }

    public void setBarName(TextView barName) {
        this.barName = barName;
    }

    public GridView getDrinkGrid() {
        return drinkGrid;
    }

    public void setDrinkGrid(GridView drinkGrid) {
        this.drinkGrid = drinkGrid;
    }

    public GridView getCommentGrid() {
        return commentGrid;
    }

    public void setCommentGrid(GridView commentGrid) {
        this.commentGrid = commentGrid;
    }

    public Button getAddDrink() {
        return addDrink;
    }

    public void setAddDrink(Button addDrink) {
        this.addDrink = addDrink;
    }

    public Button getAddComment() {
        return addComment;
    }

    public void setAddComment(Button addComment) {
        this.addComment = addComment;
    }
}
