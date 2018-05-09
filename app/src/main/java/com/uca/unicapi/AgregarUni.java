package com.uca.unicapi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.uca.unicapi.Api.Api;
import com.uca.unicapi.models.UniversityModel;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Salvador on 7/5/2018.
 */

public class AgregarUni extends AppCompatActivity {
    private Button buttonSave;
    private Button buttonBack;
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
        setContentView(R.layout.form_uni);
        initViews();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConnected(AgregarUni.this)) {
                    addDB();
                    startActivity(new Intent(AgregarUni.this,ListData.class));
                }else{
                    sendHttpRequest();
                    startActivity(new Intent(AgregarUni.this,ListData.class));
                }
                finish();
            }

        });

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, dep);
        department.setAdapter(adapter);

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

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AgregarUni.this, ListData.class));
                finish();
            }
        });

    }

    private boolean isConnected(AgregarUni agregarUni){
        ConnectivityManager cm = (ConnectivityManager) agregarUni.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else
                return false;
        } else
            return false;
    }


    private void initViews() {
        buttonBack = findViewById(R.id.buttonBack);
        buttonSave = findViewById(R.id.buttonSave);
        nameUni = findViewById(R.id.nameUni);
        addressUni = findViewById(R.id.addressUni);
        phoneUni = findViewById(R.id.phoneUni);
        descriptionUni = findViewById(R.id.descriptionUni);
        department = findViewById(R.id.departament);
    }

    private void sendHttpRequest() {
        UniversityModel universityModel = new UniversityModel();
        universityModel.setUniname(nameUni.getText().toString());
        universityModel.setUniaddress(addressUni.getText().toString());
        universityModel.setUniphone(phoneUni.getText().toString());
        universityModel.setUnidescription(descriptionUni.getText().toString());
        universityModel.setUnidepartment(text);

        Call<UniversityModel> call = Api.instance().createUniversity(universityModel);
        call.enqueue(new Callback<UniversityModel>() {
            @Override
            public void onResponse(Call<UniversityModel> call, Response<UniversityModel> response) {


            }

            @Override
            public void onFailure(Call<UniversityModel> call, Throwable throwable) {


            }
        });
    }

    private void addDB(){
        /*Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UniversityModel universityModel = realm.createObject(UniversityModel.class);

                universityModel.setUniname(nameUni.getText().toString());
                universityModel.setUniaddress(addressUni.getText().toString());
                universityModel.setUniphone(phoneUni.getText().toString());
                universityModel.setUnidescription(descriptionUni.getText().toString());
                universityModel.setUnidepartment(text);
                UniversityModel realmUni = realm.copyToRealm(universityModel);
            }
        });
        realm.commitTransaction();*/

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        UniversityModel universityModel = realm.createObject(UniversityModel.class);
        universityModel.setUniname(nameUni.getText().toString());
        universityModel.setUniaddress(addressUni.getText().toString());
        universityModel.setUniphone(phoneUni.getText().toString());
        universityModel.setUnidescription(descriptionUni.getText().toString());
        universityModel.setUnidepartment(text);
        realm.commitTransaction();

    }
}
