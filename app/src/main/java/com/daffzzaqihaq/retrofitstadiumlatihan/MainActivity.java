package com.daffzzaqihaq.retrofitstadiumlatihan;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.daffzzaqihaq.retrofitstadiumlatihan.Adapter.StadiumAdapter;
import com.daffzzaqihaq.retrofitstadiumlatihan.Api.ApiClient;
import com.daffzzaqihaq.retrofitstadiumlatihan.Api.ApiInterface;
import com.daffzzaqihaq.retrofitstadiumlatihan.Model.Constans;
import com.daffzzaqihaq.retrofitstadiumlatihan.Model.DataResponse;
import com.daffzzaqihaq.retrofitstadiumlatihan.Model.StadiumResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvUser)
    RecyclerView rvUser;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private List<DataResponse> dataResponsesList;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dataResponsesList = new ArrayList<>();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        showProgress();

        getData();
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    private void getData() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<StadiumResponse> call = apiInterface.getStadium(Constans.s, Constans.c);
        call.enqueue(new Callback<StadiumResponse>() {
            @Override
            public void onResponse(Call<StadiumResponse> call, Response<StadiumResponse> response) {
                progressDialog.dismiss();
                swipeRefresh.setRefreshing(false);

                StadiumResponse stadiumResponse = response.body();
                dataResponsesList = stadiumResponse.getTeams();

                rvUser.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rvUser.setAdapter(new StadiumAdapter(MainActivity.this, dataResponsesList));

            }

            @Override
            public void onFailure(Call<StadiumResponse> call, Throwable t) {
                progressDialog.dismiss();
                swipeRefresh.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
