package com.neer.ku_bazar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class firstscreen extends AppCompatActivity {

    private TextView t_ku,t_bazar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstscreen);

        t_ku= findViewById(R.id.ku);
        t_bazar= findViewById(R.id.bazar);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.main_font);
        t_ku.setTypeface(typeface);
        t_bazar.setTypeface(typeface);

        YoYo.with(Techniques.FadeInUp)
                .duration(5000)
                .playOn(findViewById(R.id.ku));

        YoYo.with(Techniques.FadeInUp)
                .duration(5000)
                .playOn(findViewById(R.id.bazar));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent main = new Intent(firstscreen.this,login.class);
                startActivity(main);

                finish();
            }
        },5000);
    }


}
