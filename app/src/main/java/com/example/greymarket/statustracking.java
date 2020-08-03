package com.example.greymarket;


import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class statustracking extends AppCompatActivity {

    Button button;
    TextView inputField, resultField;
    String inputNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statustracking);
        inputField = findViewById(R.id.input);
        resultField = findViewById(R.id.result);
        button = findViewById(R.id.search);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputNumber = inputField.getText().toString();
                new Async().execute();
            }
        });

    }
    class Async extends AsyncTask<Void, Void, Void> {



        String records = "",error="";

        @Override

        protected Void doInBackground(Void... voids) {

            try

            {

                Class.forName("com.mysql.jdbc.Driver");

                Connection connection = DriverManager.getConnection("jdbc:mysql://121.200.55.42:4061/test", "root", "root");

                Statement statement = connection.createStatement();
                String sql = "Select * from complaint where phone = "+inputNumber;

                ResultSet resultSet = statement.executeQuery(sql);

                while(resultSet.next()) {

                    records += resultSet.getString(8);
                }

            }

            catch(Exception e)

            {

                error = e.toString();

            }

            return null;

        }



        @Override

        protected void onPostExecute(Void aVoid) {

            resultField.setText(records);

            if(error != "")

                resultField.setText(error);

            super.onPostExecute(aVoid);

        }





    }


}

