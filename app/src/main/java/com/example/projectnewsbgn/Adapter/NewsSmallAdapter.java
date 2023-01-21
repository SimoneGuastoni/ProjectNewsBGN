package com.example.projectnewsbgn.Adapter;

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

import com.example.projectnewsbgn.Listener.SelectListener;
import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsSmallAdapter extends RecyclerView.Adapter<NewsSmallAdapter.CustomViewHolderSmall> {
    private Context context;
    private List<News> newsList;
    private SelectListener listener;

    public NewsSmallAdapter(Context context, List<News> newsList, SelectListener listener) {
        this.context = context;
        this.newsList = newsList;
        this.listener = listener;
    }



    public void onBindViewHolder(@NonNull CustomViewHolderSmall holder, int position) {
        /*holder.bind(newsList.get(position));*/
        holder.text_title.setText(newsList.get(position).getTitle());
        holder.text_source.setText(newsList.get(position).getSource().getName());
        holder.text_date.setText(newsList.get(position).getPublishedAt());
        /*holder.text_description.setText(newsList.get(position).getDescription());*/

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnNewsClicked(newsList.get(position));
            }
        });

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
        public TextView text_description;
        public ImageView img_headline;
        public LinearLayout linearLayout;
        public ImageButton btnFav,btnShare,btnDelete;

        public CustomViewHolderSmall(@NonNull View itemView) {
            super(itemView);

            text_title = itemView.findViewById(R.id.title);
            text_source = itemView.findViewById(R.id.author);
            text_date = itemView.findViewById(R.id.date);
            /*text_description = itemView.findViewById(R.id.description);*/
            img_headline = itemView.findViewById(R.id.newsPic);
            btnFav = itemView.findViewById(R.id.btnFavourite);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnShare = itemView.findViewById(R.id.btnShare);
            linearLayout = itemView.findViewById(R.id.containerListNewsSmall);

            btnFav.setOnClickListener(v -> {
                News newsClicked = newsList.get(getAdapterPosition());
                changeFavIcon(newsList.get(getAdapterPosition()).getFavourite());
                listener.onFavButtonPressed(newsClicked);
            });

            btnDelete.setOnClickListener(v -> {
                News newsClicked = newsList.get(getAdapterPosition());
                newsList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                listener.onDeleteButtonPressed(newsClicked);
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
           /* btnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Add to favourites", Toast.LENGTH_SHORT).show();
                }
            });

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Share", Toast.LENGTH_SHORT).show();
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News newsClicked = newsList.get(getAdapterPosition());
                    if(newsClicked.getFavourite()) {
                        inewsRepository.updateNews(newsClicked);
                    }
                    newsList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });
        }

        public void bind(News news) {
            setIconFav(newsList.get(getAdapterPosition()).getFavourite());
        }

        private void setIconFav(boolean favourite) {
            if(favourite){
                btnFav.setImageDrawable(AppCompatResources.getDrawable(context,R.drawable.ic_baseline_favorite_24));
            }
            else
                btnFav.setImageDrawable(AppCompatResources.getDrawable(context,R.drawable.ic_baseline_favorite_border_24));

        }
    }*/
    }
}
