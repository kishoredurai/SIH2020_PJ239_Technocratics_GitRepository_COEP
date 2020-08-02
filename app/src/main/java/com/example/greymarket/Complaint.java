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
            "Afghanistan","Albania","Algeria","Andorra","Angola","Antigua and Barbuda","Argentina","Armenia","Australia","Austria","Austrian Empire","Azerbaijan","Baden","Bahamas","Bahrain","Bangladesh","Barbados","Bavaria","Belarus","Belgium","Belize","Benin (Dahomey)","Bolivia","Bosnia and Herzegovina","Botswana","Brazil","Brunei","Brunswick and Lüneburg","Bulgaria","Burkina Faso (Upper Volta)","Burma","Burundi","Cabo Verde","Cambodia","Cameroon","Canada","Cayman Islands","Central African Republic","Central American Federation","Chad","Chile","China","Colombia","Comoros","Congo Free State","Costa Rica","Cote d’Ivoire","Croatia","Cuba","Cyprus","Czechia","Czechoslovakia","Democratic Republic of the Congo","Denmark","Djibouti","Dominica","Dominican Republic","Duchy of Parma","East Germany","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Eswatini","Ethiopia","Federal Government of Germany(1840-49)","Fiji","Finland","France","Gabon","Gambia","Georgia","Germany","Ghana","Grand Duchy of Tuscany","Greece","Grenada","Guatemela","Guinea","Guinea-Bissau","Guyana","Haiti","Hanover","Hanseatic Republics","Hawaii","Holy see","Honduras","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Israel","Italy","Jamaica","Japan","Jordan","Kazakhstan","Kenya","Kingdom of Serbia/Yugoslavia","Kiribati","Korea","Kosovo","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Lew Chew (Loochoo)","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Mauritania","Mauritius","Mecklenburg-Schwerin","Mecklenburg-Strelitz","Mexico","Micronesia","Moldova","Monaco","Mongolia","Montenegro","Morocco","Mozambique","Namibia","Nassau","Nauru","Nepal","Netherlands","New Zealand","Nicaragua","Niger","Nigeria","North German Confederation","North German Union","North Macedonia","Norway","Oldenburg","Oman","Orange Free State","Pakistan","Palau","Panama","Papal States","Papua New Guinea","Paraguay","Peru","Philippines","Piedmont-Sardinia","Poland","Portugal","Qatar","Republic of Genoa","Republic of Korea (South Korea)","Republic of the Congo","Romania","Russia","Rwanda","Saint Kitts and Nevis","Saint Lucia","Saint Vincent and the Grenadines","Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Schaumburg-Lippe","Senegal","Serbia","Seychelles","Sierra Leone","vSingapore","Slovakia","Slovenia","Solomon Islands","Somalia","South Africa","South Sudan","Spain","Sri Lanka","Sudan","Suriname","Sweden","Switzerland","Syria","Tajikistan","Tanzania","Texas","Thailand","Timor-Leste","Togo","Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan","Tuvalu","Two Sicilies","Uganda","Ukraine","Union of Soviet Socialist Republics","United Arab Emirates","United Kingdom","Uruguay","Uzbekistan","Vanuatu","Venezuela","Vietnam","Württemberg","Yemen","Zambia","Zimbabwe"
    };
    private static  final String[] STATES = new String[]{
            "Andhra pradesh","Arunachal pradesh","Assam","Bihar","Chattisgarh","Goa","Gujarat","Haryana","Himachal pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya pradesh","Maharastra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttar pradesh","Uttarkhand","West Bengal"
    };
    final int Request = 1;
    private EditText phone,r_number;
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
         String a,b,c,d,e;
         a=phone.getText().toString();
         b=state.getText().toString();
         c=country.getText().toString();
         d=r_number.getText().toString();
         String Message = "Phone : "+a+"\n"+"State : "+b+"\n"+"Country : "+c+"\n"+"Mobile no./CL : "+d+"\n"+"Date : "+currentDateString+"\n"+"Time : "+time;
         if(a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty() || currentDateString.isEmpty() || time.isEmpty())
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