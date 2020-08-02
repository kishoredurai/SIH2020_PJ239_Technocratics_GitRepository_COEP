package com.example.greymarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private EditText num,sendto;
    private Button send;
    String sendto_;
    String otpstring = getRandomNumberString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num = findViewById(R.id.etMobile);
        sendto = findViewById(R.id.etEmail);
        send = findViewById(R.id.btSend);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num_ = num.getText().toString();
                sendto_ = sendto.getText().toString();
                sendMail();
                Intent intent = new Intent(MainActivity.this, Verify.class);
                intent.putExtra("OTP",otpstring);
                intent.putExtra("PHONE",num_);
                intent.putExtra("EMAILID",sendto_);
                startActivity(intent);
                if (num_.isEmpty())
                    Toast.makeText(MainActivity.this, "Enter your phone number", Toast.LENGTH_SHORT).show();
                else
                {
                    try {
                        String apiKey = "apikey=" + "WN7d/kHz+p4-K3NpD4scSqrvy1HgcTiousPsq5TpXU";
                        String message = "&message=" + "Your OTP is " + otpstring ;
                        String sender = "&sender=" + "TXTLCL";
                        String numbers = "&numbers=" + num.getText().toString();

                        HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
                        String data = apiKey + numbers + message + sender;
                        conn.setDoOutput(true);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                        conn.getOutputStream().write(data.getBytes("UTF-8"));
                        final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        final StringBuffer stringBuffer = new StringBuffer();
                        String line;
                        while ((line = rd.readLine()) != null) {
                            Toast.makeText(MainActivity.this, "OTP sent through sms", Toast.LENGTH_SHORT).show();

                        }
                        rd.close();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this,"Error in sending OTP through sms", Toast.LENGTH_SHORT).show();
                    }
                    if(sendto_ != null && !sendto_.isEmpty())
                    {
                        sendMail();
                    }

                }

            }
        });
        StrictMode.ThreadPolicy st=new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(st);
    }
    private void sendMail() {
        String mail = sendto.getText().toString().trim();
        String message = "Your OTP: "+ otpstring;
        String subject = "PJ239_SIH2020".trim();
        Mailapi mailapi = new Mailapi(this,mail,subject,message);
        mailapi.execute();

    }

    public static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}
