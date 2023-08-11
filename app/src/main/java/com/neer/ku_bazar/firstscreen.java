package com.neer.ku_bazar;

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

    private TextView appname;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstscreen);

        appname= findViewById(R.id.appname);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.main_font);
        appname.setTypeface(typeface);

        YoYo.with(Techniques.FadeInUp)
                .duration(5000)
                .playOn(findViewById(R.id.appname));


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
