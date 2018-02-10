package com.pesanmerpati.hoteljogja;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pesanmerpati.hoteljogja.Model.Hotel;
import com.pesanmerpati.hoteljogja.Model.Log;

import java.net.FileNameMap;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mEditTextHotelName, mEditTextPhone, mEditTextAddress;
    Button mButtonAdd, mButtonClear;

    FirebaseDatabase database;
    DatabaseReference referenceHotel;
    DatabaseReference referenceLog;

    ListView listViewHotels;
    List<Hotel> hotelList;

    String id;

    public static final String HOTEL_ID = "id";
    public static final String HOTEL_NAME = "hotelName";
    public static final String HOTEL_PHONE = "phone";
    public static final String HOTEL_ADDRESS = "address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextHotelName = findViewById(R.id.editText_hotel_name);
        mEditTextPhone = findViewById(R.id.editText_hotel_phone);
        mEditTextAddress = findViewById(R.id.editText_address);
        mButtonAdd = findViewById(R.id.button_add);
        mButtonClear = findViewById(R.id.button_clear);

        mButtonAdd.setOnClickListener(this);
        mButtonClear.setOnClickListener(this);

        listViewHotels = findViewById(R.id.listView_hotels);
        hotelList = new ArrayList<>();

        /**
         * Initial Firebase
         */

        database = FirebaseDatabase.getInstance();
        referenceHotel = database.getReference().child("hotels");
        referenceLog = database.getReference().child("Log");


        listViewHotels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Hotel hotel = hotelList.get(i);
                Intent intentUpdate = new Intent(MainActivity.this, UpdateHotelActivity.class);
                intentUpdate.putExtra(HOTEL_NAME, hotel.getHotelName());
                intentUpdate.putExtra(HOTEL_PHONE, hotel.getPhone());
                intentUpdate.putExtra(HOTEL_ID, hotel.getId());
                startActivity(intentUpdate);
            }
        });

        listViewHotels.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Hotel hotel = hotelList.get(i);
                id = hotel.getId();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Apakah akan dihapus?")
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteHotel(id);
                            }
                        });
                final AlertDialog alertDialog1 = alertDialog.create();
                alertDialog.show();

                return true;
            }
        });
    }

    private void deleteHotel(String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("hotels").child(id);
        reference.removeValue();
        Toast.makeText(MainActivity.this, "Hotel is deleted!", Toast.LENGTH_SHORT).show();    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add:
                addHotel();
                break;
            case R.id.button_clear:
                mEditTextHotelName.setText("");
                mEditTextPhone.setText("");
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        referenceHotel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hotelList.clear();
                for (DataSnapshot hotelSnapshot : dataSnapshot.getChildren()) {
                    Hotel hotels = hotelSnapshot.getValue(Hotel.class);
                    hotelList.add(hotels);
                }

                HotelList hotelListAdapter = new HotelList(MainActivity.this, hotelList);
                listViewHotels.setAdapter(hotelListAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addHotel() {
        String hotelName = mEditTextHotelName.getText().toString().trim();
        String phone = mEditTextPhone.getText().toString().trim();
        String address = mEditTextAddress.getText().toString().trim();

        if (!TextUtils.isEmpty(hotelName) && !TextUtils.isEmpty(phone)) {
            String id = referenceHotel.push().getKey();
//            Hotel hotel = new Hotel(hotelName, phone, id);
            Hotel hotel = new Hotel(hotelName, phone, id, address);
            referenceHotel.child(id).setValue(hotel);

            //log
            String date = "10 Februari 2018";
            Log log = new Log(date);
            referenceLog.child(id).setValue(log);

            Toast.makeText(MainActivity.this, "Hotel successfully added!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
