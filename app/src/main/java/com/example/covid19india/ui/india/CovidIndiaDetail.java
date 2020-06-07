package com.example.covid19india.ui.india;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.covid19india.R;

public class CovidIndiaDetail extends Activity {

    TextView tvDetailStateName,tvDetailStateCases,tvDetailStateDeath,tvDetailStateRecovered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_state_detail);


        tvDetailStateName = findViewById(R.id.tvDetailStateName);
        tvDetailStateCases = findViewById(R.id.tvDetailStateCases);
        tvDetailStateDeath = findViewById(R.id.tvDetailStateDeath);
        tvDetailStateRecovered = findViewById(R.id.tvDetailStateRecovered);

        CovidIndia covidIndia = getIntent().getParcelableExtra("EXTRA_COVID_DATA");

        tvDetailStateName.setText(covidIndia.getmCovidState());
        tvDetailStateDeath.setText(covidIndia.getmDeaths());
        tvDetailStateCases.setText(covidIndia.getmCases());
        tvDetailStateRecovered.setText(covidIndia.getmRecovered());

    }
}
