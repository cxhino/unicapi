package com.uca.unicapi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.uca.unicapi.Api.Api;
import com.uca.unicapi.adapters.UniversityFromDatabaseAdapter;
import com.uca.unicapi.models.UniversityModel;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";


    private Button buttonSelectAll;

    private Button buttonDelete;
    private Button buttonUpdate;
    private EditText idUni;
    private EditText nameUni;
    private EditText addressUni;
    private EditText phoneUni;
    private EditText descriptionUni;
    private Spinner department;
    String text;
    ArrayAdapter<String> adapter;
    String dep[] = {"Matagalpa", "Managua", "Granada", "Rivas", "León", "Estelí","Rio San Juan", "Nueva Segovia", "Chontales", "Jinotega", "Madriz", "Masaya", "Carazo", "RAAN", "RAAS"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    initViews();
    buttonSelectAll.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, ListData.class);
            startActivity(intent);
            finish();
        }
    });
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, dep);
        department.setAdapter(adapter);


    buttonUpdate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            updateData();
        }
    });

    buttonDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            deleteData();
        }
    });

    department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            switch (i)
            {
                case 0:
                    text = "Matagalpa";
                    break;
                case 1:
                    text = "Managua";
                    break;
                case 2:
                    text = "Granada";
                    break;
                case 3:
                    text = "Rivas";
                    break;
                case 4:
                    text = "León";
                    break;
                case 5:
                    text = "Estelí";
                    break;
                case 6:
                    text = "Rio San Juan";
                    break;
                case 7:
                    text = "Nueva Segovia";
                    break;
                case 8:
                    text = "Chontales";
                    break;
                case 9:
                    text = "Jinotega";
                    break;
                case 10:
                    text = "Madriz";
                    break;
                case 11:
                    text = "Masaya";
                    break;
                case 12:
                    text = "Carazo";
                    break;
                case 13:
                    text = "RAAN";
                    break;
                case 14:
                    text = "RAAS";
                    break;

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    });


    }


    /**
     * To get reference of the view elements
     */

    private void initViews() {
        buttonSelectAll = findViewById(R.id.buttonSelect);

        buttonDelete = findViewById(R.id.buttonDelete);
        buttonUpdate= findViewById(R.id.buttonUpdate);
        idUni = findViewById(R.id.idUni);
        nameUni = findViewById(R.id.nameUni);
        addressUni = findViewById(R.id.addressUni);
        phoneUni = findViewById(R.id.phoneUni);
        descriptionUni = findViewById(R.id.descriptionUni);
        department = findViewById(R.id.departament);
    }

    /**
     * To make an http request
     */


    private void updateData(){
        UniversityModel universityModel = new UniversityModel();
        universityModel.setId_uni(Integer.valueOf(idUni.getText().toString()));
        universityModel.setUniname(nameUni.getText().toString());
        universityModel.setUniaddress(addressUni.getText().toString());
        universityModel.setUniphone(phoneUni.getText().toString());
        universityModel.setUnidescription(descriptionUni.getText().toString());
        universityModel.setUnidepartment(text);


        Call<UniversityModel> call = Api.instance().updateUniversity(universityModel.getId_uni(),universityModel);
        call.enqueue(new Callback<UniversityModel>() {
            @Override
            public void onResponse(Call<UniversityModel> call, Response<UniversityModel> response) {
                if(response != null){ Log.i(TAG, response.body().getUniname());}

            }

            @Override
            public void onFailure(Call<UniversityModel> call, Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    private void deleteData(){
        UniversityModel universityModel = new UniversityModel();
        universityModel.setId_uni(Integer.valueOf(idUni.getText().toString()));
        universityModel.setUniname(nameUni.getText().toString());
        universityModel.setUniaddress(addressUni.getText().toString());
        universityModel.setUniphone(phoneUni.getText().toString());
        universityModel.setUnidescription(descriptionUni.getText().toString());


        Call<UniversityModel> call = Api.instance().deleteUniversity(universityModel.getId_uni());
        call.enqueue(new Callback<UniversityModel>() {
            @Override
            public void onResponse(Call<UniversityModel> call, Response<UniversityModel> response) {
                if(response != null){ Log.i(TAG, response.body().getUniname());}

            }

            @Override
            public void onFailure(Call<UniversityModel> call, Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    private void store(UniversityModel universityModelFromApi) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        UniversityModel universityModel = realm.createObject(UniversityModel.class); // Create a new object

        universityModel.setUniname(universityModelFromApi.getUniname());

        realm.commitTransaction();
    }
    /*private void getFromDataBase() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<UniversityModel> query = realm.where(UniversityModel.class);

        RealmResults<UniversityModel> results Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UniversityModel universityModel = realm.createObject(UniversityModel.class);
                universityModel.setUniname(nameUni.getText().toString());
                universityModel.setUniaddress(addressUni.getText().toString());
                universityModel.setUniphone(phoneUni.getText().toString());
                universityModel.setUnidescription(descriptionUni.getText().toString());
                universityModel.setUnidepartment(text);
            }
        });
        realm.commitTransaction();= query.findAll();

        mAdapter = new UniversityFromDatabaseAdapter(results);

        mRecyclerView.setAdapter(mAdapter);
    }*/







}
