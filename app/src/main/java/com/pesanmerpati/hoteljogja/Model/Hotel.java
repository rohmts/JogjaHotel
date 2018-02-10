package com.pesanmerpati.hoteljogja.Model;

/**
 * Created by Rohmats on 2/10/2018.
 */

public class Hotel {

    private String hotelName;
    private String phone;
    String id;
    String address;

    public Hotel() {
    }

    public Hotel(String hotelName, String phone, String id) {
        this.hotelName = hotelName;
        this.phone = phone;
        this.id = id;
    }

    public Hotel(String hotelName, String phone, String id, String address) {
        this.hotelName = hotelName;
        this.phone = phone;
        this.id = id;
        this.address = address;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }
}
