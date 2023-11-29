// BusinessAdapter.java
package com.example.first_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.first_project.R;
import com.example.first_project.entity.Business;

import java.util.List;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder> {
    private List<Business> businessList;
    private ClickListener clickListener;

    // 비즈니스 목록을 사용하여 어댑터를 초기화하는 생성자
    public BusinessAdapter(List<Business> businessList) {
        this.businessList = businessList;
    }

    // 레이아웃 매니저에 의해 호출되어 각 비즈니스 항목에 대한 뷰를 생성
    @NonNull
    @Override
    public BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 각 비즈니스 아이템에 대한 레이아웃을 인플레이트하고 뷰홀더를 반환
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_item, parent, false);
        return new BusinessViewHolder(itemView);
    }

    // 레이아웃 매니저에 의해 호출되어 뷰의 내용을 교체
    @Override
    public void onBindViewHolder(@NonNull BusinessViewHolder holder, int position) {
        // 특정 위치의 비즈니스를 가져와 뷰홀더의 텍스트뷰에 설정
        Business business = businessList.get(position);
        holder.textViewNameHeader.setText(business.getBusinessName());
        holder.textViewNameBody.setText(business.getBusinessAddress());
    }

    // 어댑터에 있는 비즈니스 항목 수를 반환
    @Override
    public int getItemCount() {
        return businessList.size();
    }

    // 비즈니스 아이템 클릭 이벤트를 처리하는 리스너 인터페이스
    public interface ClickListener {
        void onItemClick(String businessName);
    }

    // 클릭 리스너를 설정하는 메소드
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    // 비즈니스 뷰홀더 클래스
    public class BusinessViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNameHeader;
        public TextView textViewNameBody;

        // 뷰홀더 생성자
        public BusinessViewHolder(@NonNull View itemView) {
            super(itemView);
            // 텍스트뷰를 뷰홀더에 연결
            textViewNameHeader = itemView.findViewById(R.id.textViewNameHeader);
            textViewNameBody = itemView.findViewById(R.id.textViewNameBody);

            // 아이템뷰 클릭 시 동작을 처리하는 클릭 리스너 설정
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        // 클릭된 위치를 가져와서 해당 비즈니스 이름을 전달
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            clickListener.onItemClick(businessList.get(position).getBusinessName());
                        }
                    }
                }
            });
        }
    }
}
