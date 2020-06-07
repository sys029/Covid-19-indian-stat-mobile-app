package com.example.covid19india.ui.country;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covid19india.R;

public class CovidCountryDetail extends AppCompatActivity {

    TextView tvDetailCountryName,tvDetailTotalCases,tvDetailTodayCases,tvDetailTotalDeaths,tvDetailTodayDeaths,
            tvDetailTotalRecovered,tvDetailTotalActive,tvDetailTotalCritical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_country_detail);


        tvDetailCountryName=findViewById(R.id.tvDetailCountryName);
        tvDetailTodayCases=findViewById(R.id.tvDetailTodayCases);
        tvDetailTotalCases=findViewById(R.id.tvDetailTotalCases);
        tvDetailTotalDeaths=findViewById(R.id.tvDetailTotalDeath);
        tvDetailTodayDeaths=findViewById(R.id.tvDetailTodayDeaths);
        tvDetailTotalRecovered=findViewById(R.id.tvDetailTotalRecovered);
        tvDetailTotalActive=findViewById(R.id.tvDetailTotalActive);
        tvDetailTotalCritical=findViewById(R.id.tvDetailTotalCritical);

        CovidCountry covidCountry=getIntent().getParcelableExtra("EXTRA_COVID");

        tvDetailCountryName.setText(covidCountry.getmCovidCountry());
        tvDetailTodayCases.setText(covidCountry.getmTodayCases());
        tvDetailTotalCases.setText(Integer.toString(covidCountry.getmCases()));
        tvDetailTotalDeaths.setText(covidCountry.getmDeaths());
        tvDetailTodayDeaths.setText(covidCountry.getmTodayDeaths());
        tvDetailTotalRecovered.setText(covidCountry.getmRecovered());
        tvDetailTotalActive.setText(covidCountry.getmActive());
        tvDetailTotalCritical.setText(covidCountry.getmCritical());


    }
}
