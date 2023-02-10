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

import com.example.projectnewsbgn.Listener.SearchListener;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsSearchAdapter extends RecyclerView.Adapter<NewsSearchAdapter.CustomViewHolderSmall> {
    private final Context context;
    private final List<News> newsList;
    private final SearchListener listener;

    public NewsSearchAdapter(Context context, List<News> newsList, SearchListener listener) {
        this.context = context;
        this.newsList = newsList;
        this.listener = listener;
    }


//TODO Sistemare riciclaggio della vista anche quì
    public void onBindViewHolder(@NonNull CustomViewHolderSmall holder, @SuppressLint("RecyclerView") int position) {
        holder.text_title.setText(newsList.get(position).getTitle());
        holder.text_source.setText(newsList.get(position).getSource().getName());
        holder.text_date.setText(newsList.get(position).getPublishedAt());
        holder.linearLayout.setOnClickListener(v -> listener.OnNewsClicked(newsList.get(position)));

        if(newsList.get(position).getUrlToImage() != null){
            Picasso.get().load(newsList.get(position).getUrlToImage()).into(holder.img_headline);
        }
    }

    @NonNull
    @Override
    public CustomViewHolderSmall onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.custom_list_news_small, parent, false);

        return new CustomViewHolderSmall(view);    }

    @Override
    public int getItemCount() {
        if (newsList != null) {
            return newsList.size();
        } else
            return 0;
    }

    public class CustomViewHolderSmall extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView text_title;
        public TextView text_source;
        public TextView text_date;
        public ImageView img_headline;
        public LinearLayout linearLayout;
        public ImageButton btnFav,btnShare;

        public CustomViewHolderSmall(@NonNull View itemView) {
            super(itemView);

            text_title = itemView.findViewById(R.id.title);
            text_source = itemView.findViewById(R.id.author);
            text_date = itemView.findViewById(R.id.date);
            img_headline = itemView.findViewById(R.id.newsPic);
            btnFav = itemView.findViewById(R.id.btnFavourite);
            btnShare = itemView.findViewById(R.id.btnShare);
            linearLayout = itemView.findViewById(R.id.containerListNewsSmall);

            btnFav.setOnClickListener(v -> {
                News newsClicked = newsList.get(getAdapterPosition());
                changeFavIcon(newsList.get(getAdapterPosition()).getFavourite());
                listener.onFavButtonPressed(newsClicked);
            });

            btnShare.setOnClickListener(v -> listener.onShareButtonPressed(newsList.get(getAdapterPosition())));

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
