package com.niken.catalogmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.niken.catalogmovie.adapter.MovieAdapterCard;
import com.niken.catalogmovie.model.MovieModel;
import com.niken.catalogmovie.model.Result;
import com.niken.catalogmovie.rest.APICall;
import com.niken.catalogmovie.rest.APIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.niken.catalogmovie.controller.AppConfig.API_KEY;
import static com.niken.catalogmovie.controller.AppConfig.INTENT_SEARCH;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MovieAdapterCard adapter;
    List<Result> results;

    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv_category);

        LinearLayoutManager llmData = new LinearLayoutManager(MainActivity.this.getApplicationContext());
        recyclerView.setLayoutManager(llmData);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint(getString(R.string.searchTxt));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovies(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchMovies(final String key){
        APICall api = APIClient.getRetrofit().create(APICall.class);
        Call<MovieModel> call = api.getSearch(API_KEY, key);

        results = new ArrayList<>();
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                MovieModel movie = response.body();
                adapter = new MovieAdapterCard(results);
                adapter.setData(movie.getResults());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong"
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }

}
