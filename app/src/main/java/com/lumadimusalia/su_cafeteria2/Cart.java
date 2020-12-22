package com.lumadimusalia.su_cafeteria2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lumadimusalia.su_cafeteria2.bean.FoodItem;
import com.lumadimusalia.su_cafeteria2.bean.Order;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    private Button checkout;
    private RecyclerView itemsInCartRecyclerView;
    private TextView textView;
    private ArrayList<FoodItem> itemsInCart = new ArrayList<>();
    private int totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        itemsInCartRecyclerView = findViewById(R.id.cart_items);
        checkout = findViewById(R.id.check_out);
        textView = findViewById(R.id.cart_total);

        //todo: Fetch from firebase
        itemsInCart = FirebaseDatabase.foodItems;

        for (int i = 0; i < itemsInCart.size(); i++)
            totalPrice += itemsInCart.get(i).getQty() *  itemsInCart.get(i).getPrice();

        textView.setText("Table Number: No.");
        textView.setText("Cart Total: Ksh. " + totalPrice);


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //push to FireBase as an order
                //generate order id


                Order order = new Order();
                order.setItems(itemsInCart);
                order.setOrderCode(getUniqueId());
                order.setPaid(true);

                FirebaseDatabase.order = order;


                startActivity(new Intent(Cart.this, OrderSuccessActivity.class));
            }
        });

        itemsInCartRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        itemsInCartRecyclerView.setLayoutManager(layoutManager);
        ItemsAdapter mAdapter = new ItemsAdapter(this, itemsInCart);
        itemsInCartRecyclerView.setAdapter(mAdapter);


    }

    private String getUniqueId(){
        long time = System.currentTimeMillis();
        String uniqid;
        SecureRandom sec = new SecureRandom();
        byte[] sbuf = sec.generateSeed(8);
        ByteBuffer bb = ByteBuffer.wrap(sbuf);

        uniqid = String.format("%s%08x%05x", "", time / 1000, time);
        uniqid += "." + String.format("%.8s", "" + bb.getLong() * -1);
        return uniqid;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logout) {

            //todo: implement logout logic

            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "Logout pressed", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
