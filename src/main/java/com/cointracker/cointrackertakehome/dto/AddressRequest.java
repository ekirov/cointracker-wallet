package com.cointracker.cointrackertakehome.dto;

public class AddressRequest {
    private String address;

    // Default constructor for JSON deserialization
    public AddressRequest() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
