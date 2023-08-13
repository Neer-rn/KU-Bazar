package com.neer.ku_bazar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView ku,bazar,recent_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ku = findViewById(R.id.ku);
        bazar = findViewById(R.id.text_bazar);
        recent_add = findViewById(R.id.recently_added);

        Typeface t_ku = ResourcesCompat.getFont(this,R.font.rainshow_condensed);
        Typeface t_bazar = ResourcesCompat.getFont(this,R.font.rainshow_embosss);
        Typeface t_recent = ResourcesCompat.getFont(this,R.font.debrosee);
        ku.setTypeface(t_ku);
        bazar.setTypeface(t_bazar);
        recent_add.setTypeface(t_recent);


    }
}