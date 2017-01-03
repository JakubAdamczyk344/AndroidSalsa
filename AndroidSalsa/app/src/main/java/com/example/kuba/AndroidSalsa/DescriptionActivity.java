package com.example.kuba.AndroidSalsa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity {

    /*private String day;
    private String hour;
    private String city;
    private String cityBlock;
    private String description;
    private String name;
    private String toPay;
    private String adress;
    private String administratorName;
    private String telephoneNumber;
    private String email;
    private String keywords;
    private TextView dayTextView;
    private TextView hourTextView;
    private TextView cityTextView;
    private TextView cityBlockTextView;
    private TextView descriptionTextView;
    private TextView nameTextView;
    private TextView toPayTextView;
    private TextView adressTextView;
    private TextView administratorNameTextView;
    private TextView telephoneNumberTextView;
    private TextView emailTextView;
    private TextView keywordsTextView;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Bundle bundle = getIntent().getExtras();
        String day = bundle.getString("day");
        String hour = bundle.getString("hour");
        String city = bundle.getString("city");
        String cityBlock = bundle.getString("cityBlock");
        String description = bundle.getString("description");
        String name = bundle.getString("name");
        String toPay = bundle.getString("toPay");
        String adress = bundle.getString("adress");
        String administratorName = bundle.getString("administratorName");
        String telephoneNumber = bundle.getString("telephoneNumber");
        String email = bundle.getString("email");
        String keywords = bundle.getString("keywords");

        TextView dayTextView = (TextView) findViewById(R.id.dayTextView);
        TextView hourTextView = (TextView) findViewById(R.id.hourTextView);
        TextView cityTextView = (TextView) findViewById(R.id.cityTextView);
        TextView cityBlockTextView = (TextView) findViewById(R.id.cityBlockTextView);
        TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        TextView toPayTextView = (TextView) findViewById(R.id.toPayTextView);
        TextView adressTextView = (TextView) findViewById(R.id.adressTextView);
        TextView administratorNameTextView = (TextView) findViewById(R.id.administratorNameTextView);
        TextView telephoneNumberTextView = (TextView) findViewById(R.id.telephoneNumberTextView);
        TextView emailTextView = (TextView) findViewById(R.id.emailTextView);
        TextView keywordsTextView = (TextView) findViewById(R.id.keywordsTextView);

        dayTextView.setText(day);
        hourTextView.setText(hour);
        cityTextView.setText(city);
        cityBlockTextView.setText(cityBlock);
        descriptionTextView.setText(description);
        nameTextView.setText(name);
        toPayTextView.setText(toPay);
        adressTextView.setText(adress);
        administratorNameTextView.setText(administratorName);
        telephoneNumberTextView.setText(telephoneNumber);
        emailTextView.setText(email);
        keywordsTextView.setText(keywords);
    }
}
