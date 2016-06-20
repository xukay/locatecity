package com.psalata.ready4s;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.psalata.ready4s.api_results.City;

import java.io.Serializable;

public class DetailsActivity extends AppCompatActivity {

    TextView cityName, communityName, countyName, voivodeshipName, countryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_details);


        cityName = (TextView) findViewById(R.id.city_name);
        communityName = (TextView) findViewById(R.id.community_name);
        countyName = (TextView) findViewById(R.id.county_name);
        voivodeshipName = (TextView) findViewById(R.id.voivodeship_name);
        countryName = (TextView) findViewById(R.id.country_name);

        setDetailsFields(getIntent().getSerializableExtra(MainActivity.CITY_DETAILS));


        setupActionBar();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater inflater = LayoutInflater.from(this);
            View customView = inflater.inflate(R.layout.custom_action_bar, null);
            TextView textView = (TextView) customView.findViewById(R.id.action_bar_text_view);
            textView.setText(getString(R.string.back_button_text));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            actionBar.setCustomView(customView);
            actionBar.setDisplayShowCustomEnabled(true);
        }
    }

    private void setDetailsFields(Serializable sCity) {
        City city = (City) sCity;
        cityName.setText(city.getCityName());
        communityName.setText(city.getCommunityName());
        countyName.setText(city.getCountyName());
        voivodeshipName.setText(city.getVoivodeshipName());
        countryName.setText(city.getCountryName());
    }
}
