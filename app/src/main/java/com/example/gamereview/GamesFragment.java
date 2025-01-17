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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class GamesFragment extends Fragment {

    private RecyclerView recyclerView;
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

        // Initialize Retrofit with logging for debugging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.igdb.com/v4/")
                .client(client) // Add logging
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(IGDBApiService.class);

        // Fetch games
        fetchGames();

        return view;
    }

    private void fetchGames() {
        String query = "fields name, platforms.name, rating, cover.url; sort rating desc; limit 10;";
        RequestBody requestBody = RequestBody.create(
                query,
                MediaType.parse("application/json")
        );

        Call<List<Game>> call = apiService.getGames(requestBody);

        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Game> games = response.body();
                    Log.d(TAG, "Received " + games.size() + " games.");

                    GamesAdapter adapter = new GamesAdapter(games, game -> {
                        // Add the game to HomePage's selected games list
                        User.getInstance().getSelectedGames().add(game);
                        Toast.makeText(getContext(), game.getName() + " added to your list!", Toast.LENGTH_SHORT).show();
                    });

                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e(TAG, "API Error: " + response.code() + " - " + response.message());
                    Toast.makeText(getContext(), "Failed to load games: " + response.message(), Toast.LENGTH_SHORT).show();
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
