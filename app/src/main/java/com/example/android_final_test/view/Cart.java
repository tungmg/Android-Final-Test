package com.example.android_final_test.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_final_test.Database.Database;
import com.example.android_final_test.MainActivity;
import com.example.android_final_test.R;
import com.example.android_final_test.model.Food;
import com.example.android_final_test.model.Order;
import com.example.android_final_test.model.Private;
import com.example.android_final_test.model.Request;
import com.example.android_final_test.viewHolder.CartViewHolder;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;
    CollectionReference requests;
    TextView textViewTotal;
    Button btnOrder;
    FusedLocationProviderClient fusedLocationProviderClient;

    List<Order> cart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        db = FirebaseFirestore.getInstance();
        requests = db.collection("Requests");

        recyclerView = findViewById(R.id.listCart);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        textViewTotal = findViewById(R.id.total);
        btnOrder = findViewById(R.id.btnPlaceOrder);
        btnOrder.setOnClickListener(this);
        loadOrder();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void loadOrder() {
        cart = new Database(this).getCarts();
        CartViewHolder cartViewHolder = new CartViewHolder(getBaseContext(), cart);
        recyclerView.setAdapter(cartViewHolder);
        int total = 0;
        for(Order order : cart){
            total += (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        }
        Locale locale = new Locale("vi", "VN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        textViewTotal.setText(fmt.format(total));
    }

    @Override
    public void onClick(View v) {
        if(v == btnOrder){

            if(ActivityCompat.checkSelfPermission(Cart.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                LocationRequest locationRequest = new LocationRequest();
                locationRequest.setInterval(10000);
                locationRequest.setFastestInterval(3000);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                LocationServices.getFusedLocationProviderClient(Cart.this)
                        .requestLocationUpdates(locationRequest, new LocationCallback(){
                            @Override
                            public void onLocationResult(LocationResult locationResult){
                                super.onLocationResult(locationResult);
                                LocationServices.getFusedLocationProviderClient(Cart.this)
                                        .removeLocationUpdates(this);
                                Geocoder geocoder = new Geocoder(Cart.this, Locale.getDefault());
                                if(locationResult != null && locationResult.getLocations().size() > 0){
                                    int lastest = locationResult.getLocations().size() - 1;
                                    double latitude = locationResult.getLocations().get(lastest).getLatitude();
                                    double longtitude = locationResult.getLocations().get(lastest).getLongitude();
                                    try {
                                        List<Address> addresses = geocoder.getFromLocation(latitude, longtitude, 1);
                                        addAddress(addresses.get(0).getAddressLine(0));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }, Looper.getMainLooper());
            }
            else{
                ActivityCompat.requestPermissions(Cart.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                addAddress("");
            }
        }
    }

    public void addAddress(String address){
        AlertDialog.Builder alert = new AlertDialog.Builder(Cart.this);
        alert.setTitle("Enter your address");

        final EditText editText = new EditText(Cart.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        editText.setText(address);
        editText.setLayoutParams(layoutParams);
        alert.setView(editText);
        alert.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request = new Request(
                        Private.currentUser.getPhone(),
                        Private.currentUser.getName(),
                        editText.getText().toString(),
                        textViewTotal.getText().toString(),
                        cart
                );

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference collectionReference = db.collection("Requests");
                collectionReference.document(String.valueOf(System.currentTimeMillis())).set(request)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(Cart.this, "Request submitted", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Cart.this, "Request failed, try again", Toast.LENGTH_LONG).show();
                            }
                        });
                new Database(getBaseContext()).cleanCart();
            }
        });
        alert.setNegativeButton("RETURN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}
