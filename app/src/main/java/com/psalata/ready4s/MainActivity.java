package com.psalata.ready4s;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.psalata.ready4s.api_results.City;
import com.psalata.ready4s.api_results.Results;
import com.psalata.ready4s.database.DBHelper;
import com.psalata.ready4s.view.CustomArrayAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://maps.googleapis.com/";
    public static final String CITY_DETAILS = "CITY_DETAILS";
    public static final String TAG = MainActivity.class.getSimpleName();

    private EditText cityNameEditText;
    private TextView addCityTextView;
    private ListView listView;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DBHelper database = DBHelper.getInstance(MainActivity.this);
        intent = new Intent(MainActivity.this, DetailsActivity.class);

        listView = (ListView) findViewById(R.id.cities_list);
        ArrayAdapter adapter =
                new CustomArrayAdapter(this, R.layout.list_view_item, database.getCitiesNames());
        listView.setAdapter(adapter);

        cityNameEditText = (EditText) findViewById(R.id.enter_city_name);
        addCityTextView = (TextView) findViewById(R.id.add_city);
        addCityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = cityNameEditText.getText().toString();
                if (cityName.equals("")) {
                    Toast.makeText(MainActivity.this,
                            getString(R.string.city_name_required), Toast.LENGTH_SHORT).show();
                } else {
                    View view = getCurrentFocus();
                    if(view != null) {
                        InputMethodManager inputManager = (InputMethodManager)MainActivity.this.
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    cityNameEditText.setText("");
                    database.insertCityName(cityName);
                    ArrayAdapter adapter = new CustomArrayAdapter(MainActivity.this,
                            R.layout.list_view_item, database.getCitiesNames());
                    listView.setAdapter(adapter);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isNetworkAvailable()) {
                    final String cityName = (String) parent.getItemAtPosition(position);
                    downloadData(cityName);
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.check_internet_connection),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

       setupActionBar();

    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater inflater = LayoutInflater.from(this);
            View customView = inflater.inflate(R.layout.custom_action_bar, null);
            actionBar.setCustomView(customView);
            actionBar.setDisplayShowCustomEnabled(true);
        }
    }

    private void downloadData(String cityName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MapsApiService service = retrofit.create(MapsApiService.class);

        service.getCityDetails(cityName).enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Log.d(TAG, "onResponse OK");

                if(response.body().results != null && !response.body().results.isEmpty() ) {
                    City city = new City(response.body().results);
                    intent.putExtra(CITY_DETAILS, city);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.wrong_city_name), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());

                Toast.makeText(MainActivity.this, getString(R.string.operation_failed), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
