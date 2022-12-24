package com.example.projectnewsbgn.Models;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectnewsbgn.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    public TextView text_title;
    public TextView text_source;
    public TextView text_date;
    public TextView text_description;
    public ImageView img_headline;
    public LinearLayout linearLayout;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        text_title = itemView.findViewById(R.id.title);
        text_source = itemView.findViewById(R.id.author);
        text_date = itemView.findViewById(R.id.date);
        text_description = itemView.findViewById(R.id.description);
        img_headline = itemView.findViewById(R.id.newsPic);
        linearLayout = itemView.findViewById(R.id.containerListNews);
        
        itemView.findViewById(R.id.btnFavourite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Add to fav", Toast.LENGTH_SHORT).show();
            }
        });

        itemView.findViewById(R.id.btnShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Share", Toast.LENGTH_SHORT).show();
            }
        });

        itemView.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Delete", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
