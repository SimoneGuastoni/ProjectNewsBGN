package com.example.projectnewsbgn.Models;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectnewsbgn.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    public TextView text_title;
    public TextView text_source;
    public ImageView img_headline;
    LinearLayout linearLayout;
    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        text_title = itemView.findViewById(R.id.title);
        text_source = itemView.findViewById(R.id.author);
        img_headline = itemView.findViewById(R.id.newsPic);
        linearLayout = itemView.findViewById(R.id.containerListNews);
    }
}
