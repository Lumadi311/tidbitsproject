package com.lumadimusalia.su_cafeteria2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;
import com.lumadimusalia.su_cafeteria2.bean.FoodItem;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ArrayList<FoodItem> foodMenu = new ArrayList<>();
    private ArrayList<FoodItem> foodMenuInCart = new ArrayList<>();
    private RecyclerView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initFood();
        menu = findViewById(R.id.menu_items);

        menu.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        menu.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        ItemsAdapter mAdapter = new ItemsAdapter(this, foodMenu);
        menu.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                addToCart(foodMenu.get(position));

                Toast.makeText(getApplicationContext(), foodMenuInCart.size() + " total items in cart", Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Move to cart
                FirebaseDatabase.foodItems = foodMenuInCart;

                //todo: Replace with firebase


                Intent intent = new Intent(getApplicationContext(), Cart.class);
                startActivity(intent);

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.nav_home) {
                    startActivity(new Intent(getApplicationContext(), CafesActivity.class));
                    return  true;
                }
                if (menuItem.getItemId() == R.id.nav_slideshow) {
                    startActivity(new Intent(getApplicationContext(), Cart.class));
                    return  true;
                }
                return false;
            }
        });


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return false;
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
    }

    private void addToCart(FoodItem item) {

        if (foodMenuInCart.contains(item)) {
            int pos = foodMenuInCart.indexOf(item);
            FoodItem alreadyInCartItem = foodMenuInCart.get(pos);
            alreadyInCartItem.setQty(alreadyInCartItem.getQty() + 1);
            foodMenuInCart.remove(pos);
            foodMenuInCart.add(alreadyInCartItem);
        } else {
            item.setQty(1);
            foodMenuInCart.add(item);
        }
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

    private void initFood() {
        foodMenu.add(new FoodItem("Nut Muffin", 45, "Sweet and savoury!", R.drawable.nutmuffins, "001"));
        foodMenu.add(new FoodItem("Chocolate Muffin", 55, " Bursting with chocolate goodness!", R.drawable.chocolatemuffins, "002"));
        foodMenu.add(new FoodItem("Chicken Samosa", 50, " Everyone's favourite samosa!", R.drawable.chickensamosa, "003"));
        foodMenu.add(new FoodItem("Fruit Juice", 100, " Quench your thirst healthily!", R.drawable.fruitjuice, "004"));
        foodMenu.add(new FoodItem("Hot Beverages", 200, " For those extremely cold mornings", R.drawable.hotbeverages, "005"));
        foodMenu.add(new FoodItem("Meat Pie", 120, " Authentic British Meat Pies", R.drawable.meatpie, "006"));

        //add more items
    }
}
