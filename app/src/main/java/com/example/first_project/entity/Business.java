package com.example.first_project.entity;


public class Business{
    private Long id;

    private String businessId; // 사업자번호

    private String businessOwnerName; //사업주 명

    private String businessName; // 사업장명

    private String businessAddress; // 사업자 주소


    public Business(String businessName, String businessAddress) {
        this.businessName = businessName;
        this.businessAddress = businessAddress;
    }

    public Long getId() {
        return id;
    }


    public String getBusinessId() {
        return businessId;
    }

    public String getBusinessOwnerName() {
        return businessOwnerName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

}
