package com.example.gilian.bars_coop;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.gilian.bars_coop.R;

/**
 * Created by JérémieRoth on 27/08/2017.
 */

public class AddEstablishmentDialog extends Dialog {
    TextView nameEstablishment;
    Button cancelButton;
    Button okButton;

    public AddEstablishmentDialog(Context context){
        super(context);
        this.setContentView(R.layout.add_esthablishment_dialog);
        nameEstablishment = (TextView) findViewById(R.id.name_Bar);
        cancelButton = (Button) findViewById(R.id.cancel_add_establishment);
        okButton = (Button) findViewById(R.id.ok_add_establishment);

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.add_esthablishment_dialog);
//
//    }

    public TextView getNameEstablishment() {
        return nameEstablishment;
    }

    public void setNameEstablishment(TextView nameEstablishment) {
        this.nameEstablishment = nameEstablishment;
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
}
