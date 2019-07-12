package ir.sass.sampleprojects.retrofit;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import ir.sass.sampleprojects.R;
import ir.sass.sampleprojects.firebase.SQLiteDatabaseHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity
{
    ProgressBar progressBar;
    SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        setTitle("RetrofitActivity");

        progressBar = findViewById(R.id.progressBar);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        progressBar.setVisibility(View.GONE);

        pullToRefresh.setRefreshing(true);
        makeList();

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                pullToRefresh.setRefreshing(true);
                makeList();
            }
        });
    }

    public void makeList()
    {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Model>> call = service.getAllPhotos();
        call.enqueue(new Callback<List<Model>>()
        {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response)
            {
                progressBar.setVisibility(View.GONE);
                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemViewCacheSize(20);
                RetriftAdapter adapter = new RetriftAdapter(getApplicationContext(),response.body());
                adapter.setHasStableIds(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                pullToRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t)
            {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                pullToRefresh.setRefreshing(false);
            }
        });
    }

}
