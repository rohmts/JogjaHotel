package com.pesanmerpati.hoteljogja;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pesanmerpati.hoteljogja.Model.Hotel;

import java.util.List;

/**
 * Created by Rohmats on 2/10/2018.
 */

public class HotelList extends ArrayAdapter<Hotel> {

    private Activity context;
    private List<Hotel> hotelList;


    public HotelList(Activity context, List<Hotel> hotelList) {
        super(context, R.layout.layout_list_hotel, hotelList);
        this.context = context;
        this.hotelList = hotelList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_list_hotel, null, true);
        TextView mTextViewHotelName = view.findViewById(R.id.textViewHotelName);
        TextView mTextViewPhone= view.findViewById(R.id.textViewHotelPhone);

        Hotel hotel = hotelList.get(position);

        mTextViewHotelName.setText(hotel.getHotelName());
        mTextViewPhone.setText(hotel.getPhone());
        return view;

    }
}
