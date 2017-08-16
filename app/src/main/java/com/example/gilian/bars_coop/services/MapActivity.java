package com.example.gilian.bars_coop.services;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gilian.bars_coop.Entity.User;
import com.example.gilian.bars_coop.R;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        TextView text = (TextView) findViewById(R.id.hello);
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        User user = (User) extra.getParcelable("user");
        text.setText("bonjour "+user.getUsername());
    }
}
