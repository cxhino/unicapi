package com.uca.unicapi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListPopupWindow;

import com.tumblr.remember.Remember;

import java.util.List;

import javax.annotation.Nullable;

import com.uca.unicapi.Api.Api;
import com.uca.unicapi.adapters.UniversityAdapter;
import com.uca.unicapi.adapters.UniversityFromDatabaseAdapter;
import com.uca.unicapi.models.UniversityModel;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListData extends AppCompatActivity {

    private static final String IS_FIRST_TIME = "is_first_time";

    private Button buttonBack;
    private Button buttonCreate;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_content);
        initViews();
        configureRecyclerView();
        //fetchUniversities();
        //swipeRefreshLayout.setRefreshing(false);



        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListData.this, AgregarUni.class);
                startActivity(intent);
                finish();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isConnected(ListData.this)) {

                    getFromDataBase();
                } else {

                    fetchUniversities();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });
        if(!isConnected(ListData.this)) buildDialog(ListData.this).show();

        else{

        }

        if (isFirstTime()) {
            //getFromDataBase();
            fetchUniversities();
            storeFirstTime();
        } else {
            getFromDataBase();
            //fetchUniversities();
        }




        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListData.this, MainActivity.class));
                swipeRefreshLayout.setRefreshing(false);
                finish();
            }
        });


    }

    private boolean isConnected(ListData listData) {
        ConnectivityManager cm = (ConnectivityManager) listData.getSystemService(Context.CONNECTIVITY_SERVICE);
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

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Sin conexion a Internet");
        builder.setMessage("¿Desea trabajar sin conexion?");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        return builder;
    }

    private void initViews() {
        buttonBack = findViewById(R.id.buttonBack);
        mRecyclerView = findViewById(R.id.recycler_view);
        buttonCreate = findViewById(R.id.buttonCreate);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);



        swipeRefreshLayout.setRefreshing(false);


    }

    private void configureRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void fetchUniversities() {
        Call<List<UniversityModel>> call = Api.instance().getUniversities();
        call.enqueue(new Callback<List<UniversityModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<UniversityModel>> call, Response<List<UniversityModel>> response) {
                if (response.body() != null) {
                    UniversityAdapter universityAdapter = new UniversityAdapter(response.body());
                    mRecyclerView.setAdapter(universityAdapter);
                    sync(response.body());

                    //getFromDataBase();

                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UniversityModel>> call, @NonNull Throwable t) {
                Log.i("Debug: ", t.getMessage());
            }
        });
    }

    private void storeFirstTime() {
        Remember.putBoolean(IS_FIRST_TIME, true);
    }

    private boolean isFirstTime() {
        return Remember.getBoolean(IS_FIRST_TIME, false);
    }

    /*private void fetchUniversities() {
        Call<List<UniversityModel>> call = Api.instance().getUniversities();
        call.enqueue(new Callback<List<UniversityModel>>() {
            @Override
            public void onResponse(Call<List<UniversityModel>> call, Response<List<UniversityModel>> response) {
                //mAdapter = new ProductsAdapter(response.body());
                //mRecyclerView.setAdapter(mAdapter);

                sync(response.body());
                getFromDataBase();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<UniversityModel>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }*/

    private void sync(List<UniversityModel> universityModels) {
        for(UniversityModel universityModel : universityModels) {
            store(universityModel);
        }
    }

    private void store(UniversityModel universityModelFromApi) {
        String a= String.valueOf(universityModelFromApi.getId_uni());
        if (exist(a)==false) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            UniversityModel universityModel = realm.createObject(UniversityModel.class); // Create a new object

            universityModel.setId_uni(universityModelFromApi.getId_uni());
            universityModel.setUniname(universityModelFromApi.getUniname());
            universityModel.setUniaddress(universityModelFromApi.getUniaddress());
            universityModel.setUniphone(universityModelFromApi.getUniphone());
            universityModel.setUnidescription(universityModelFromApi.getUnidescription());
            universityModel.setUnidepartment(universityModelFromApi.getUnidepartment());

            realm.commitTransaction();
        }
    }

    private void getFromDataBase() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<UniversityModel> query = realm.where(UniversityModel.class);
        RealmResults<UniversityModel> results = query.findAll();

        mAdapter = new UniversityAdapter(results);
        mRecyclerView.setAdapter(mAdapter);
    }






    private boolean exist(String id){

        Boolean exist=false;
        Realm realm = Realm.getDefaultInstance();

        RealmQuery<UniversityModel> query = realm.where(UniversityModel.class);

        RealmResults<UniversityModel> results = query.findAll();

        for (int i=0; i<results.size(); i++)
        {
            if (id.equals(results.get(i).getId_uni()))
            {
                exist=true;
            }
        }
        return exist;
    }

}
