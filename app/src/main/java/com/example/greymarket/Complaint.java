package com.example.greymarket;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class Complaint extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static  final  String[] COUNTRIES = new String[]{
            "Afghanistan:93","Albania:355","Algeria:213","American Samoa:1-684","Andorra:376","Angola:244"
    };
    private static  final String[] STATES = new String[]{
            "Andhra Pradesh:AD","Arunachal Pradesh:AR","Assam:AS" };
    final int Request = 1;
    private EditText phone,r_number,desc;
    private AutoCompleteTextView country,state;
    private Button Msgsend,datepicker,timepicker;
    String number = "7339656952";
    String currentDateString,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        phone = findViewById(R.id.phonenumber);
        Msgsend = findViewById(R.id.btMsgsend);
        r_number = findViewById(R.id.r_number);
        country = findViewById(R.id.country);
        desc=findViewById(R.id.description);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,COUNTRIES);
        country.setAdapter(adapter);
        state = findViewById(R.id.state);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,STATES);
        state.setAdapter(adapter1);
        datepicker= findViewById(R.id.date);
        timepicker = findViewById(R.id.time);
        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"Time Picker");
            }
        });
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
        Msgsend.setEnabled(false);
        if(checkPermission(Manifest.permission.SEND_SMS))
            Msgsend.setEnabled(true);
        else
        {
            ActivityCompat.requestPermissions(Complaint.this,new String[]{Manifest.permission.SEND_SMS}, Request);
        }
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        TextView tvtime = findViewById(R.id.tvtime);
        tvtime.setText(i+" : "+i1);
        time = i+":"+i1;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, i);
        c.set(Calendar.MONTH,i1);
        c.set(Calendar.DAY_OF_MONTH,i2);
        currentDateString = DateFormat.getDateInstance().format(c.getTime());
        TextView tvdate = findViewById(R.id.tvdate);
        tvdate.setText(currentDateString);
    }

    public void onSend (View view)
    {
        Log.e("Debug: ","onSend()");
        String a,b,c,d,e,sc,cc,r=new String();
        a=phone.getText().toString();
        b=state.getText().toString();
        c=country.getText().toString();
        d=r_number.getText().toString();
        String Message = new String();
        if(a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty() || currentDateString.isEmpty() || time.isEmpty())
        {
            Toast.makeText(Complaint.this,"Fill all the fields",Toast.LENGTH_SHORT).show();
        }else{
            e=desc.getText().toString();
            sc="001";
            Log.e("A :",a+" "+a.length());
            Log.e("b",b+" "+b.length());
            String[] countryCode = new String[2];
            sc = b.split(":")[1];
            countryCode = b.split(":");
            sc = countryCode[1];
            r=sc+a.substring(7,10);
            if(e==null || e.isEmpty())
                Message =r+"-"+a+"-"+b+"-"+c+"-"+d+"-"+currentDateString+"-"+time+"-"+"NULL";
            else
                Message =r+"-"+a+"-"+b+"-"+c+"-"+d+"-"+currentDateString+"-"+time+"-"+e;
        }
        if(a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty() || currentDateString.isEmpty() || time.isEmpty())
        {
            Toast.makeText(Complaint.this,"Fill all the fields",Toast.LENGTH_SHORT).show();
        }
        else {
            if (checkPermission(Manifest.permission.SEND_SMS)) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, Message, null, null);
                Toast.makeText(Complaint.this, "Your complaint ID: "+r , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, mainpage.class);
                startActivity(intent);
            } else {
                Toast.makeText(Complaint.this, "Allow sms permission in settings", Toast.LENGTH_SHORT).show();
            }
        }


    }
    public boolean checkPermission(String permission)
    {
        int check = ContextCompat.checkSelfPermission(this,permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}