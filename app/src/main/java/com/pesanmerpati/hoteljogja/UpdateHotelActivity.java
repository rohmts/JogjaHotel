package com.pesanmerpati.hoteljogja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pesanmerpati.hoteljogja.Model.Hotel;

import java.io.InputStream;

public class UpdateHotelActivity extends AppCompatActivity {

    EditText mEditTextHotelName, mEditTextPhone;
    Button mButtonUpdate;

    DatabaseReference reference;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hotel);

        mEditTextHotelName = findViewById(R.id.editText_update_hotel_name);
        mEditTextPhone = findViewById(R.id.editText_update_phone);
        mButtonUpdate = findViewById(R.id.button_update);

        Intent intent = getIntent();
        id = intent.getStringExtra(MainActivity.HOTEL_ID);
        String name = intent.getStringExtra(MainActivity.HOTEL_NAME);
        String phone = intent.getStringExtra(MainActivity.HOTEL_PHONE);

        mEditTextHotelName.setText(name);
        mEditTextPhone.setText(phone);

        Toast.makeText(UpdateHotelActivity.this, ""+id, Toast.LENGTH_SHORT).show();

        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {

                String name = mEditTextHotelName.getText().toString().trim();
                String phone = mEditTextPhone.getText().toString().trim();
                updateHotel(name,phone,id);
            }
        });
    }

    private void updateHotel(String name, String phone, String id) {
        reference = FirebaseDatabase.getInstance().getReference("hotels").child(id);
        Hotel hotel = new Hotel(name, phone, id);
        reference.setValue(hotel);
        Toast.makeText(UpdateHotelActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
    }
}
