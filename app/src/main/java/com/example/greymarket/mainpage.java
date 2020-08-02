package com.example.greymarket;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class mainpage extends AppCompatActivity {
    private ImageView loading;
    private ImageView progressbarImg;
    private ImageView gifImageView;
    private Button button2;
    private Button button3;
    private Button button4;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mainpage);
        final ImageView progressbarImg = (ImageView) findViewById(R.id.progressbarimg); // Final so we can access it from the other thread
        progressbarImg.setVisibility(View.VISIBLE);
        final ImageView loading = (ImageView) findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);

// Final so we can access it from the other thread



// Create a Handler instance on the main thread
        new Handler().postDelayed(new Runnable() {
            public void run() {
                findViewById(R.id.progressbarimg).setVisibility(View.INVISIBLE);
                findViewById(R.id.loading).setVisibility(View.INVISIBLE);

            }
        }, 3000);

            final ImageView gifImageView = (ImageView) findViewById(R.id.gifImageView); // Final so we can access it from the other thread
            gifImageView.setVisibility(View.INVISIBLE);
            final Button button2 = (Button) findViewById(R.id.button2);
            button2.setVisibility(View.INVISIBLE);
        final Button button3 = (Button) findViewById(R.id.button3);
        button3.setVisibility(View.INVISIBLE);
        Button button4 = (Button) findViewById(R.id.button4);
        button4.setVisibility(View.INVISIBLE);

// Final so we can access it from the other thread



// Create a Handler instance on the main thread
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    findViewById(R.id.gifImageView).setVisibility(View.VISIBLE);
                    findViewById(R.id.button2).setVisibility(View.VISIBLE);
                    findViewById(R.id.button3).setVisibility(View.VISIBLE);
                    findViewById(R.id.button4).setVisibility(View.VISIBLE);



                }
            }, 3000);



    button4 = (Button) findViewById(R.id.button4);
      button4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openNewActivity();
        }
    });
}
    public void openNewActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }}
