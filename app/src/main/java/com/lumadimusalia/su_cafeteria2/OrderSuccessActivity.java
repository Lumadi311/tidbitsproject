package com.lumadimusalia.su_cafeteria2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.lumadimusalia.su_cafeteria2.bean.Order;

public class OrderSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        Order order = FirebaseDatabase.order;
        TextView price = findViewById(R.id.total_price);
        TextView items = findViewById(R.id.no_of_items);

        price.setText("Ksh. "+ order.getTotalPrice());
        items.setText(""+ order.getItems().size() +" items ordered");

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

            Toast.makeText(this, "Logout pressed", Toast.LENGTH_LONG).show();

            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
