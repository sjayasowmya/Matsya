package com.prapanca.matsya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TermsConditions extends AppCompatActivity {
    private Button buttonnagree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);
        buttonnagree=(Button)findViewById(R.id.btnagree);
        buttonnagree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(TermsConditions.this, MainActivity.class);
                // myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);
            }
        });
    }
}
