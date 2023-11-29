package com.example.first_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;


import com.example.first_project.R;
import com.example.first_project.adapter.BusinessAdapter;
import com.example.first_project.entity.Business;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {
    // Retrofit을 사용하여 웹 서비스와 통신하기 위한 API 정의
    public interface JsonPlaceHolderApi {
        @GET("api/find-Business-list") // API의 실제 엔드 포인트 { 스프링 부트로 띄우기 }
        Call<List<Business>> getBusiness();
    }

    private RecyclerView recyclerView;
    private BusinessAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrofit 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.109.15:8080") // API의 기본 URL로 설정하기 [학교주소]
//                .baseUrl("http://192.168.219.102:8080")//집 주소
                .addConverterFactory(GsonConverterFactory.create()) // Gson 변환기 설정
                .build();

        // Retrofit을 사용하여 웹 서비스와 통신할 API를 생성
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //Json 데이터로 받아오기
        Call<List<Business>> call = jsonPlaceHolderApi.getBusiness();

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrofit을 사용하여 비동기적으로 데이터를 가져오고 처리
        call.enqueue(new Callback<List<Business>>() {
            @Override
            public void onResponse(Call<List<Business>> call, Response<List<Business>> response) {
                // 성공한 경우
                if (response.isSuccessful()) {
                    // Business 데이터를 가져옴
                    List<Business> data = response.body();

                    // RecyclerView 어댑터 설정
                    adapter = new BusinessAdapter(data);

                    adapter.setClickListener(new BusinessAdapter.ClickListener() {
                        @Override
                        public void onItemClick(String businessName) {
                            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                            intent.putExtra("businessName", businessName);
                            System.out.println(businessName);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Business>> call, Throwable t) {
                // 실패한 경우 처리 (예: 네트워크 오류)

                // 여기에서는 아무 작업도 수행하지 않음
            }
        });
    }
}
