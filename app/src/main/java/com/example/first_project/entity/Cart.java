package com.example.first_project.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart implements Serializable {

    private int totalPrice;

    private List<Menu> orderList;

    private String storeName;

    public Cart() {
        this.totalPrice = 0;
        this.orderList = new ArrayList<>();
    }

    public int getTotalPrice() {
        return totalPrice;
    }
    public List<Menu> getOrderList() {
        return orderList;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderList(List<Menu> orderList) {
        this.orderList = orderList;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public void addToCart(Menu menu) {
        // 장바구니에 이미 같은 메뉴가 있는지 확인
        for (Menu existingMenu : orderList) {
            if (Objects.equals(existingMenu.getId(), menu.getId())) {
                totalPrice += menu.getPrice();
                existingMenu.setQuantity(existingMenu.getQuantity() + 1);

                return;
            }
        }

        // 장바구니에 없는 경우 새로운 메뉴를 추가
        menu.setQuantity(1);
        totalPrice += menu.getPrice();
        orderList.add(menu);
    }

}
