package com.daffzzaqihaq.retrofitstadiumlatihan.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StadiumResponse {

    @SerializedName("teams")
    private List<DataResponse> teams;

    public List<DataResponse> getTeams() {
        return teams;
    }

    public void setTeams(List<DataResponse> teams) {
        this.teams = teams;
    }
}

