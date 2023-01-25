package com.example.projectnewsbgn.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectnewsbgn.Listener.HomeListener;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsHomeAdapter extends RecyclerView.Adapter<NewsHomeAdapter.CustomViewHolderHome>{
    private Context context;
    private List<News> newsList;
    private HomeListener listener;

    public NewsHomeAdapter(Context context, List<News> newsList, HomeListener listener) {
        this.context = context;
        this.newsList = newsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolderHome onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolderHome(LayoutInflater.from(context)
                .inflate(R.layout.custom_list_news_home,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolderHome holder,
                                 @SuppressLint("RecyclerView") int position) {

        holder.text_title.setText(newsList.get(position).getTitle());
        holder.text_source.setText(newsList.get(position).getSource().getName());
        holder.text_date.setText(newsList.get(position).getPublishedAt());
        holder.text_description.setText(newsList.get(position).getDescription());

        holder.container.setOnClickListener(v ->
                listener.OnNewsClicked(newsList.get(position)));

        if(newsList.get(position).getUrlToImage() != null){
            Picasso.get().load(newsList.get(position).getUrlToImage()).into(holder.img_headline);
        }

    }

    @Override
    public int getItemCount() {
        if (newsList != null) {
            return newsList.size();
        } else
            return 0;
    }

    public class CustomViewHolderHome extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView text_title;
        public TextView text_source;
        public TextView text_date;
        public TextView text_description;
        public ImageView img_headline;
        public LinearLayout linearLayout;
        public ImageButton btnFav, btnShare;
        public MaterialCardView container;

        public CustomViewHolderHome(@NonNull View itemView) {
            super(itemView);

            text_title = itemView.findViewById(R.id.title);
            text_source = itemView.findViewById(R.id.author);
            text_date = itemView.findViewById(R.id.date);
            text_description = itemView.findViewById(R.id.description);
            img_headline = itemView.findViewById(R.id.newsPic);
            btnFav = itemView.findViewById(R.id.btnFavourite);
            btnShare = itemView.findViewById(R.id.btnShare);
            linearLayout = itemView.findViewById(R.id.containerListNewsSmall);
            container = itemView.findViewById(R.id.containerListNews);

            btnFav.setOnClickListener(v -> {
                changeFavIcon(newsList.get(getAdapterPosition()).getFavourite());
                listener.onFavButtonPressed(newsList.get(getAdapterPosition()));
            });
        }

        private void changeFavIcon(boolean favourite) {
            if(favourite){
                btnFav.setImageDrawable(AppCompatResources.getDrawable
                        (context,R.drawable.ic_baseline_favorite_border_24));
            }
            else{
                btnFav.setImageDrawable(AppCompatResources.getDrawable
                        (context,R.drawable.ic_baseline_favorite_24));
            }
        }

        @Override
        public void onClick(View v) {
        }
    }
}
