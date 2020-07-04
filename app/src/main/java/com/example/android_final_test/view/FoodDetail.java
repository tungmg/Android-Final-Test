package com.example.android_final_test.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.android_final_test.FoodList;
import com.example.android_final_test.R;
import com.example.android_final_test.model.Food;
import com.example.android_final_test.viewHolder.FoodViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {

    TextView foodName, foodPrice, foodDes;
    ImageView imageView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton elegantNumberButton;

    String foodId;
    CollectionReference food;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        elegantNumberButton = findViewById(R.id.numberBtn);
        btnCart = findViewById(R.id.btnCart);

        foodName = findViewById(R.id.foodName);
        foodPrice = findViewById(R.id.foodPrice);
        foodDes = findViewById(R.id.food_description);
        imageView = findViewById(R.id.img_food);

        collapsingToolbarLayout = findViewById(R.id.collapsing);

        if(getIntent() != null){
            foodId = getIntent().getStringExtra("FoodId");
        }

        if(!foodId.isEmpty() && foodId != null) {
            System.out.println(foodId);
            loadFoodDetail(foodId);
        }
    }

    private void loadFoodDetail(final String foodId) {

        db = FirebaseFirestore.getInstance();
        food = db.collection("Food");
        final DocumentReference documentReference = food.document(foodId);
        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()){
                                Food food = new Food(documentSnapshot.getId(),
                                        documentSnapshot.getString("Name"),
                                        documentSnapshot.getString("Description"),
                                        documentSnapshot.getString("Image"),
                                        (Long)documentSnapshot.get("Price"),
                                        (Long)documentSnapshot.get("Discount"),
                                        documentSnapshot.getString("CategoryId"));
                                Picasso.with(getBaseContext()).load(food.getImage()).into(imageView);
                                collapsingToolbarLayout.setTitle(food.getName());
                                foodPrice.setText(Long.toString(food.getPrice()));
                                foodDes.setText(food.getDescription());
                            }
                        } else {
                            Toast.makeText(FoodDetail.this, "Get failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
