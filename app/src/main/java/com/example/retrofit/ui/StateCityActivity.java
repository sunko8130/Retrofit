package com.example.retrofit.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofit.R;
import com.example.retrofit.adapter.StateAdapter;
import com.example.retrofit.apiService.Api;
import com.example.retrofit.model.StateResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StateCityActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private List<StateResponse> stateList;
    private Spinner stateSpinner, citySpinner;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_city);
        stateSpinner = findViewById(R.id.state_spinner);
        citySpinner = findViewById(R.id.city_spinner);
        btnSubmit = findViewById(R.id.btnClick);

        //displayLoader
        displayLoader();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.androiddeft.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);

        final Call<List<StateResponse>> stateCall = api.getStates();
        stateCall.enqueue(new Callback<List<StateResponse>>() {
            @Override
            public void onResponse(Call<List<StateResponse>> call, Response<List<StateResponse>> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    stateList = response.body();
                    if (stateList != null) {
                        final StateAdapter stateAdapter = new StateAdapter(StateCityActivity.this, R.layout.state_list, stateList);
                        stateSpinner.setAdapter(stateAdapter);
                        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                StateResponse state = stateAdapter.getItem(position);
                                if (state != null) {
                                    List<String> cities = state.getCities();
                                    ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(StateCityActivity.this, R.layout.city_list, R.id.citySpinnerText, cities);
                                    citySpinner.setAdapter(cityAdapter);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<List<StateResponse>> call, Throwable t) {
                pDialog.dismiss();
                Log.e("fail", t.getMessage());
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StateResponse state = (StateResponse) stateSpinner.getSelectedItem();
                String city = (String) citySpinner.getSelectedItem();
                Toast.makeText(StateCityActivity.this, "Selected state: " + state.getState() + " City: " + city, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void displayLoader() {
        pDialog = new ProgressDialog(StateCityActivity.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }
}
