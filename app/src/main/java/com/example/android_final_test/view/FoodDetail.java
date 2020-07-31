package com.example.android_final_test.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.android_final_test.Database.Database;
import com.example.android_final_test.FoodList;
import com.example.android_final_test.Home;
import com.example.android_final_test.R;
import com.example.android_final_test.model.Food;
import com.example.android_final_test.model.Order;
import com.example.android_final_test.viewHolder.FoodViewHolder;
import com.example.android_final_test.viewModel.ViewModelCartCount;
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

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FoodDetail extends AppCompatActivity implements View.OnClickListener{

    TextView foodName, foodPrice, foodDes;
    ImageView imageView;
    FloatingActionButton btnCart;
//    Button btnGotoCart;
    ElegantNumberButton elegantNumberButton;
    Integer count = 0;
    String foodId;
    CollectionReference food;
    FirebaseFirestore db;

    Food currentFood;
//    ViewModelCartCount numberOfOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        elegantNumberButton = findViewById(R.id.numberBtn);

//        btnGotoCart = findViewById(R.id.goToCart);
        btnCart = findViewById(R.id.btnCart);
        foodName = findViewById(R.id.foodName);
        foodPrice = findViewById(R.id.foodPrice);
        foodDes = findViewById(R.id.food_description);
        imageView = findViewById(R.id.img_food);

//        cartCount();

        btnCart.setOnClickListener(this);
//        btnGotoCart.setOnClickListener(this);

        if(getIntent() != null){
            foodId = getIntent().getStringExtra("FoodId");
        }

        if(!foodId.isEmpty() && foodId != null) {
            System.out.println(foodId);
            loadFoodDetail(foodId);
        }

//        numberOfOrder = new ViewModelProvider(this).get(ViewModelCartCount.class);
//        LiveData<Integer> cartCount = numberOfOrder.init(cartCount());
//        cartCount.observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                btnGotoCart.setText(integer.toString());
//            }
//        });
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
                                currentFood = new Food(documentSnapshot.getId(),
                                        documentSnapshot.getString("Name"),
                                        documentSnapshot.getString("Description"),
                                        documentSnapshot.getString("Image"),
                                        (Long)documentSnapshot.get("Price"),
                                        (Long)documentSnapshot.get("Discount"),
                                        documentSnapshot.getString("CategoryId"));
                                Picasso.with(getBaseContext()).load(currentFood.getImage()).into(imageView);
                                foodName.setText(currentFood.getName());
                                Locale locale = new Locale("vi", "VN");
                                NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                                foodPrice.setText(fmt.format(currentFood.getPrice()));
                                foodDes.setText(currentFood.getDescription());
                            }
                        } else {
                            Toast.makeText(FoodDetail.this, "Get failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if(v == btnCart){
            new Database(this).addToCart(new Order(
                    foodId,
                    currentFood.getName(),
                    elegantNumberButton.getNumber(),
                    Long.toString(currentFood.getPrice()),
                    Long.toString(currentFood.getDiscount())
            ));
            Intent intentMenu = new Intent(FoodDetail.this, Home.class);
            startActivity(intentMenu);
//            numberOfOrder.addCart();
            Toast.makeText(FoodDetail.this, "Added to Cart", Toast.LENGTH_LONG).show();
        }
    }

//    public Integer cartCount(){
//        Integer count = 0;
//        List<Order> cart = new Database(this).getCarts();
//        int total = 0;
//        for(Order order : cart){
//            total += (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
//            count ++;
//        }
//        return count;
//    }
}
