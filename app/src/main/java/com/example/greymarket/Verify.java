package com.example.greymarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Verify extends AppCompatActivity {
    private Button verify;
    private EditText otp;
    TextView t;
    String otpstring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        otp = findViewById(R.id.etOtp);
        t = findViewById(R.id.tv);
        t.setText(""+getIntent().getStringExtra("OTP"));
        otpstring = t.getText().toString();
        t.setText("Hurray!! Verify your OTP sent to Mobile: "+getIntent().getStringExtra("PHONE")+" Email ID: "+getIntent().getStringExtra("EMAILID"));
        verify=findViewById(R.id.btVerify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otpfield = otp.getText().toString();

                if (otpfield.isEmpty())
                    Toast.makeText(Verify.this, "OOPS!! You forgot to enter your OTP", Toast.LENGTH_SHORT).show();
                else
                {
                    if(otpfield.equals(otpstring)) {
                        Toast.makeText(Verify.this, "Successfully Verified", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Verify.this, Complaint.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(Verify.this, "Enter the valid OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}