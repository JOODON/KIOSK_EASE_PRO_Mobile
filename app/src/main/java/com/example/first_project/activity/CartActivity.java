package com.example.first_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.first_project.R;
import com.example.first_project.adapter.CartAdapter;
import com.example.first_project.entity.Business;
import com.example.first_project.entity.Cart;
import com.example.first_project.entity.Menu;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class CartActivity extends AppCompatActivity {

    Cart cart;
    public interface JsonPlaceHolderApi {
        @POST("api/save-order-data") // API의 실제 엔드 포인트 { 스프링 부트로 띄우기 }
        Call<List<Menu>> sendOrderData(@Body Cart cart);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ArrayList<Menu> receivedMenuList = (ArrayList<Menu>) getIntent().getSerializableExtra("orderList");

        // 리스트뷰 초기화
        ListView menuListView = findViewById(R.id.cart_list_view);
        CartAdapter cartAdapter = new CartAdapter(this, receivedMenuList);
        menuListView.setAdapter(cartAdapter);

        Button paymentBtn = (Button) findViewById(R.id.payment_btn);

        int totalPrice = getIntent().getIntExtra("totalPrice", 0);
        paymentBtn.setText(totalPrice + "원 결제하기");

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataToServer();

                Intent intent = new Intent(CartActivity.this, CompleteActivity.class);
                intent.putExtra("result","주문이 성공적으로 접수되었습니다.");
                startActivity(intent);
                finish();
                //여기서 실행
            }
        });
    }
    private void sendDataToServer() {
        // Retrofit 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.109.15:8080")//학교 주소
//                .baseUrl("http://192.168.219.102:8080")//집 주소
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Retrofit 인터페이스 생성
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        cart = new Cart();

        // 전송할 주문 리스트
        ArrayList<Menu> orderList = (ArrayList<Menu>) getIntent().getSerializableExtra("orderList");


        cart.setOrderList(orderList);
        cart.setTotalPrice(getIntent().getIntExtra("totalPrice", 0));
        cart.setStoreName(getIntent().getStringExtra("storeName"));
        // Retrofit을 사용하여 서버에 주문 데이터 전송
        Call<List<Menu>> call = jsonPlaceHolderApi.sendOrderData(cart);
        call.enqueue(new Callback<List<Menu>>() {


            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(CartActivity.this, CompleteActivity.class);
                    intent.putExtra("result","주문이 성공적으로 접수되었습니다.");

                    //해당 페이지로 이동
                } else {
                    Intent intent = new Intent(CartActivity.this, CompleteActivity.class);
                    intent.putExtra("result","주문이 실패하였습니다.");
                }
            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                // 네트워크 오류 등으로 인한 실패 시 처리 코드 작성
                // 주문 처리에 실패했을 때의 동작을 구현
            }
        });
    }

}
