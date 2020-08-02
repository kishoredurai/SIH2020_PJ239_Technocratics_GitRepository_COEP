package com.example.greymarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Complaint extends AppCompatActivity {
    final int Request = 1;
    private EditText phone,r_number,country,state,date;
    private Button Msgsend;
    String number = "7339656952";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        phone = findViewById(R.id.phonenumber);
        Msgsend = findViewById(R.id.btMsgsend);
        r_number = findViewById(R.id.r_number);
        country = findViewById(R.id.country);
        state = findViewById(R.id.state);
        date= findViewById(R.id.date);

        Msgsend.setEnabled(false);
        if(checkPermission(Manifest.permission.SEND_SMS))
            Msgsend.setEnabled(true);
        else
        {
            ActivityCompat.requestPermissions(Complaint.this,new String[]{Manifest.permission.SEND_SMS}, Request);
        }
    }
    public void onSend (View view)
    {
         String a,b,c,d,e;
         a=phone.getText().toString();
         b=state.getText().toString();
         c=country.getText().toString();
         d=r_number.getText().toString();
         e=date.getText().toString();
         String Message = "Phone : "+a+"\n"+"State : "+b+"\n"+"Country : "+c+"\n"+"Mobile no./CL : "+d+"\n"+"Date : "+e;
         if(a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty() || e.isEmpty())
         {
             Toast.makeText(Complaint.this,"Fill all the fields",Toast.LENGTH_SHORT).show();
         }
         else {
             if (checkPermission(Manifest.permission.SEND_SMS)) {
                 SmsManager smsManager = SmsManager.getDefault();
                 smsManager.sendTextMessage(number, null, Message, null, null);
                 Toast.makeText(Complaint.this, "Complaint filed successfully", Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(this, MainActivity.class);
                 startActivity(intent);
             } else {
                 Toast.makeText(Complaint.this, "Permission denied", Toast.LENGTH_SHORT).show();
             }
         }


    }
    public boolean checkPermission(String permission)
    {
        int check = ContextCompat.checkSelfPermission(this,permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}