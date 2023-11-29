package com.example.first_project.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.first_project.R;
import com.example.first_project.adapter.MenuAdapter;
import com.example.first_project.entity.Cart;
import com.example.first_project.entity.Menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MenuActivity extends AppCompatActivity {

    public interface JsonPlaceHolderApi {
        @GET("api/find-menu-list")
        Call<List<Menu>> getMenuList(@Query("storeName") String storeName);
    }

    private GridView gridView;
    private MenuAdapter menuAdapter;

    private Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cart = new Cart();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        String businessName = getIntent().getStringExtra("businessName");

        TextView textView = findViewById(R.id.Name);
        textView.setText(businessName);

        Button cartLocationBtn = (Button) findViewById(R.id.cart_view_btn);

        // 기본 설정
        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.219.102:8080")//집 주소
                .baseUrl("http://172.16.109.15:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 리다이렉트 API 인터페이스 만드는 부분
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        // 데이터 호출 부분
        Call<List<Menu>> call = jsonPlaceHolderApi.getMenuList(businessName);

        // Asynchronous call to handle response
        call.enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                if (response.isSuccessful()) {
                    List<Menu> data = response.body();

                    gridView = findViewById(R.id.grid_view);

                    // MenuAdapter 인스턴스를 생성하고 설정
                    menuAdapter = new MenuAdapter(MenuActivity.this, data);

                    TextView priceView = (TextView) findViewById(R.id.price_view);


                    // 어댑터에 클릭 리스너 설정
                    menuAdapter.setOnItemClickListener(new MenuAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Menu menu) {
                            // Handle item click here
                            AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                            builder.setMessage(menu.getName() + "을(를) 장바구니에 담으시겠습니까?")
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //확인 버튼을 눌렀을 때의 동작
                                            Toast.makeText(MenuActivity.this, "상품이 장바구니에 담겼습니다.", Toast.LENGTH_SHORT).show();

                                            cart.addToCart(menu);
                                            
                                            priceView.setText("현재 주문 가격 " + cart.getTotalPrice() + "원");

                                        }
                                    })
                                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //취소 버튼을 눌렀을 때의 동작
                                            dialog.dismiss(); // 다이얼로그를 닫습니다.
                                        }
                                    });
                            // AlertDialog
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    });

                    gridView.setAdapter(menuAdapter);
                }

            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                // Handle failure, e.g., network error
            }
        });
        cartLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String businessName = getIntent().getStringExtra("businessName");

                Intent intent = new Intent(MenuActivity.this, CartActivity.class);
                intent.putExtra("totalPrice",cart.getTotalPrice());
                intent.putExtra("orderList",(Serializable) cart.getOrderList());
                intent.putExtra("storeName",businessName);

                //카트 객체 태워서 보내기
                startActivity(intent);
                //해당 페이지로 이동
                finish();
                //페이지를 종료
            }
        });
    }
}
