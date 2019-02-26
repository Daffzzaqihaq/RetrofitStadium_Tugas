package com.daffzzaqihaq.retrofitstadiumlatihan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daffzzaqihaq.retrofitstadiumlatihan.DetailActivity;
import com.daffzzaqihaq.retrofitstadiumlatihan.Model.Constans;
import com.daffzzaqihaq.retrofitstadiumlatihan.Model.DataResponse;
import com.daffzzaqihaq.retrofitstadiumlatihan.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StadiumAdapter extends RecyclerView.Adapter<StadiumAdapter.ViewHolder> {


    private Context context;
    private List<DataResponse> dataResponseList;
    private Bundle bundle;

    public StadiumAdapter(Context context, List<DataResponse> dataResponseList) {
        this.context = context;
        this.dataResponseList = dataResponseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_stadium, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final DataResponse dataResponse = dataResponseList.get(i);
        viewHolder.txtNamaStadium.setText(dataResponse.getStrStadium());

        RequestOptions requestOptions = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
        Glide.with(context).load(dataResponse.getStrStadiumThumb()).apply(requestOptions).into(viewHolder.imgAvatar);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constans.id, dataResponse.getIdTeam());

                context.startActivity(new Intent(context, DetailActivity.class).putExtras(bundle));
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgAvatar)
        ImageView imgAvatar;
        @BindView(R.id.txtNamaStadium)
        TextView txtNamaStadium;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
