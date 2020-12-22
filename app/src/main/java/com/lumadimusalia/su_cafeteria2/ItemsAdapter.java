package com.lumadimusalia.su_cafeteria2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lumadimusalia.su_cafeteria2.bean.FoodItem;


import java.util.ArrayList;
import java.util.Locale;

class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    ArrayList<FoodItem> items;
    private Context context;
    MyClickListener clickListener;



    public void setOnItemClickListener(MyClickListener listener) {
        this.clickListener = listener;
    }

    public ItemsAdapter(Context context, ArrayList<FoodItem> foodMenu) {
        items = foodMenu;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {
        FoodItem item = items.get(position);
        holder.image.setImageResource(item.getImage());
        holder.name.setText(String.format("%s: %s", item.getItemCode(), item.getName()));
        holder.description.setText(item.getDescription());
        holder.price.setText(String.format(Locale.ENGLISH, "Ksh. %d x %d", item.getPrice(), item.getQty()));

    }




    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView name, price, description;

         ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ivNutMuffin);
            name = itemView.findViewById(R.id.item_1);
            description = itemView.findViewById(R.id.descrption);
            price = itemView.findViewById(R.id.item_2);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }




}
