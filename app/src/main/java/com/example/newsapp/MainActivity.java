package com.example.newsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategorClickInterface{

    //71562c50f9874e65abea68f86e082f57

    private RecyclerView newsRV,categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModal>categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRV = findViewById(R.id.idRVNews);
        categoryRV = findViewById(R.id.idRVCategories);
        loadingPB = findViewById(R.id.idPBLoading);
        articlesArrayList = new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(articlesArrayList, this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalArrayList, this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategories();
        getNews("ALL");
        newsRVAdapter.notifyDataSetChanged();

    }

    private void getCategories(){
        categoryRVModalArrayList.add(new CategoryRVModal("All","https://gumlet.assettype.com/quintype-website%2F2018-08%2F973e3cef-6730-4e80-af93-6851ec9d7ef0%2F6277209256_198cdbea86_o.jpg?rect=0%2C0%2C1279%2C719&auto=format%2Ccompress&fit=max&w=768&dpr=1.3"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology","https://images.ctfassets.net/hrltx12pl8hq/4BVORZTBMh4qTmr1PLtKmY/dd22c783d52421da894b2f8a462fe82c/free-technology-images.jpg?fit=fill&w=840&h=350"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science","https://acxngcvroo.cloudimg.io/v7/https://www.scienceinschool.org/wp-content/uploads/2021/09/card_teach.jpg"));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports","https://mongooseagency.com/files/3415/9620/1413/Return_of_Sports.jpg"));
        categoryRVModalArrayList.add(new CategoryRVModal("General","https://ak.picdn.net/shutterstock/videos/862252/thumb/1.jpg"));
        categoryRVModalArrayList.add(new CategoryRVModal("Business","https://static.dw.com/image/56167112_303.jpg"));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment","https://cdn.searchenginejournal.com/wp-content/uploads/2021/04/content-marketing-entertainment-industry-60743a08d4e48-1520x800.png"));
        categoryRVModalArrayList.add(new CategoryRVModal("Health","https://www.expatica.com/app/uploads/sites/6/2016/05/Health-Insurance-1920x1080.jpg"));
        categoryRVAdapter.notifyDataSetChanged();
    }

    private void getNews(String category){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category=" + category + "&apikey=71562c50f9874e65abea68f86e082f57";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=71562c50f9874e65abea68f86e082f57";
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;
        if(category.equals("All")){
            call = retrofitAPI.getAllNews(url);
        }else{
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModal.getArticles();
                for(int i=0; i<articles.size(); i++) {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(), articles.get(i).getDescription(), articles.get(i).getUrlToImage(),
                            articles.get(i).getUrl(), articles.get(i).getContent()));

                }
                newsRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to get news" , Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModalArrayList.get(position).getCategory();
        getNews(category);
    }
}