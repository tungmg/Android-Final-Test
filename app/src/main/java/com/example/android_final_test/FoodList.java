package com.example.android_final_test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_final_test.model.Category;
import com.example.android_final_test.model.Food;
import com.example.android_final_test.model.Private;
import com.example.android_final_test.view.FoodDetail;
import com.example.android_final_test.viewHolder.FoodViewHolder;
import com.example.android_final_test.viewHolder.MenuViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FoodList extends AppCompatActivity implements FoodViewHolder.ItemClickListener{

    private AppBarConfiguration mAppBarConfiguration;
    CollectionReference food;
    TextView textName;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;
    ArrayList<Food> foods;
    DrawerLayout drawer;
    String categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        db = FirebaseFirestore.getInstance();
        food = db.collection("Food");

        foods = new ArrayList<>();

        recyclerView = findViewById(R.id.recyler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() != null){
            categoryId = getIntent().getStringExtra("CategoryId");
        }

        if(!categoryId.isEmpty() && categoryId != null) {
            System.out.println(categoryId);
            loadFood(categoryId);
        }

    }

    private void loadFood(String categoryId) {
//        db = FirebaseFirestore.getInstance();
        Query query = food.whereEqualTo("CategoryId", categoryId);
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getData());
                                Food food = new Food(document.getId(),
                                        document.getString("Name"),
                                        document.getString("Description"),
                                        document.getString("Image"),
                                        (Long)document.get("Price"),
                                        (Long)document.get("Discount"),
                                        document.getString("CategoryId"));
                                foods.add(food);
                            }
                            FoodViewHolder foodViewHolder = new FoodViewHolder(getBaseContext(), foods, FoodList.this);
                            recyclerView.setAdapter(foodViewHolder);
                        } else {
                            Toast.makeText(FoodList.this, "Get failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(int position) {
        foods.get(position);
        System.out.println(foods.get(position).getName());
        Intent intent = new Intent(this, FoodDetail.class);
        intent.putExtra("FoodId", foods.get(position).getId());
        startActivity(intent);
    }

}
