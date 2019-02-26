package com.daffzzaqihaq.retrofitstadiumlatihan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.imgAvatar)
    ImageView imgAvatar;
    @BindView(R.id.txtNamaStadium)
    TextView txtNamaStadium;
    @BindView(R.id.txtDetailStadium)
    TextView txtDetailStadium;

    private Bundle bundle;
    private ApiInterface apiInterface;
    private int id;
    private List<DataResponse> dataResponseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();

        if (bundle != null){
            id = Integer.valueOf(bundle.getString(Constans.id));
        }

        dataResponseList = new ArrayList<>();

        getData();
    }

    private void getData() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<StadiumResponse> call = apiInterface.getDetail(id);

        call.enqueue(new Callback<StadiumResponse>() {
            @Override
            public void onResponse(Call<StadiumResponse> call, Response<StadiumResponse> response) {
                StadiumResponse stadiumResponse = response.body();
                dataResponseList = stadiumResponse.getTeams();
                Log.e("Debug Sukses ea", response.message());

                Glide.with(DetailActivity.this).load(dataResponseList.get(0).getStrStadiumThumb()).into(imgAvatar);
                txtNamaStadium.setText(dataResponseList.get(0).getStrStadium());
                txtDetailStadium.setText(dataResponseList.get(0).getStrStadiumDescription());

                setTitle(dataResponseList.get(0).getStrStadium());
            }

            @Override
            public void onFailure(Call<StadiumResponse> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("debug Gagal", t.getMessage());

            }
        });
    }
}
