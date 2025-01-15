package com.example.gamereview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class GamesFragment extends Fragment {

    private RecyclerView recyclerView;
    private GamesAdapter adapter;
    private List<Game> gamesList;
    private static final String TAG = "GameFragment";
    private IGDBApiService apiService;

    public GamesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_games, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_games);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        gamesList = new ArrayList<>();
//        adapter = new GamesAdapter(gamesList);
//        recyclerView.setAdapter(adapter);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.igdb.com/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(IGDBApiService.class);

        // Call method to fetch games
        fetchGames();

        return view;
    }

    private void fetchGames() {
        // Create the query model with the required parameters
        String query = "fields name, platforms.name, rating; sort rating desc; limit 10;";

        // Make the API call with the query object
        Call<List<Game>> call = apiService.getGames(query);

        // Print the request body for debugging
        Log.d(TAG, "Request Body: " + new Gson().toJson(query));

        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful()) {
                    List<Game> games = response.body();
                    Log.d(TAG, "Received " + games.size() + " games.");
                    GamesAdapter gamesAdapter = new GamesAdapter(games);
                    recyclerView.setAdapter(gamesAdapter);
                } else {
                    Log.e(TAG, "Error: " + response.code());
                    Toast.makeText(getContext(), "Failed to load games", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.e(TAG, "Request failed: " + t.getMessage());
                Toast.makeText(getContext(), "Failed to load games", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
