package com.example.gamereview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IGDBApiService {
    @Headers({
            "Client-ID: 7ztn0zvmanlddui3abgsbk3a573u0b",
            "Authorization: Bearer fqveqhco5jf2c8npf0dss05kcft0sr",
    })
    @POST("games")
    Call<List<Game>> getGames(@Body String query);
    }
