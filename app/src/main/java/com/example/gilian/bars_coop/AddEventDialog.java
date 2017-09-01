package com.example.gilian.bars_coop;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by JérémieRoth on 01/09/2017.
 */

public class AddEventDialog extends Dialog {
    EditText eventTxt;
    Button cancelButton;
    Button okButton;

    public AddEventDialog(@NonNull Context context) {
        super(context);
        this.setContentView(R.layout.add_event_dialog);
        eventTxt = (EditText) findViewById(R.id.event_text);
        cancelButton = (Button) findViewById(R.id.cancel_add_event);
        okButton = (Button) findViewById(R.id.ok_add_event);
    }

    public EditText getEventTxt() {
        return eventTxt;
    }

    public void setEventTxt(EditText eventTxt) {
        this.eventTxt = eventTxt;
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
