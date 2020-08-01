package com.example.android_final_test.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android_final_test.R;
import com.example.android_final_test.model.Private;
import com.example.android_final_test.model.Request;
import com.example.android_final_test.viewHolder.OrderViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderStatus extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Request> requests;
    FirebaseFirestore db;
    CollectionReference request;

    int notificationId = 1;
    final String CHANNEL_ID = "101";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        recyclerView = findViewById(R.id.recyler_order_status);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        requests = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        request = db.collection("Requests");
        loadRequests(Private.currentUser.getPhone());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    if(checkArrivedOrder()) {
                        System.out.println(checkArrivedOrder());
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(OrderStatus.this, CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle("Alarm")
                                .setContentText("Hello, Your order is arrived")
                                .setColor(Color.RED)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .setAutoCancel(true);

                        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(OrderStatus.this);
                        notificationManagerCompat.notify(notificationId, builder.build());
                        break;
                    }
                }
            }
        }).start();
    }

    private void loadRequests(String phone) {
        Query query = request.whereEqualTo("phone", phone);
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getData());
                                Request request = new Request(
                                        document.getString("phone"),
                                        document.getString("name"),
                                        document.getString("address"),
                                        document.getString("status"));
                                requests.add(request);
                            }
                            OrderViewHolder orderViewHolder = new OrderViewHolder(getBaseContext(), requests);
                            recyclerView.setAdapter(orderViewHolder);
                        } else {
                            Toast.makeText(OrderStatus.this, "Get failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private boolean checkArrivedOrder(){
        for(Request request : requests){
            if(request.getStatus().equals("2"))
                return true;
        }
        return false;
    }
}
