package com.example.projectnewsbgn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

public class NewsFavAdapter extends RecyclerView.Adapter<NewsFavAdapter.CustomViewHolderFav> {
    private Context context;
    private List<News> newsList;
    private SelectListener listener;

    public NewsFavAdapter(Context context, List<News> newsList, SelectListener listener) {
        this.context = context;
        this.newsList = newsList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public CustomViewHolderFav onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.custom_list_news_fav, parent, false);

        return new CustomViewHolderFav(view);}

    public void onBindViewHolder(@NonNull CustomViewHolderFav holder, int position) {
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


    @Override
    public int getItemCount() {
        if (newsList != null) {
            return newsList.size();
        } else
            return 0;
    }

    public class CustomViewHolderFav extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView text_title;
        public TextView text_source;
        public TextView text_date;
        public TextView text_description;
        public ImageView img_headline;
        public LinearLayout linearLayout;
        public ImageButton btnShare,btnDelete;
        public CheckBox cbDelete;

        public CustomViewHolderFav(@NonNull View itemView) {
            super(itemView);

            text_title = itemView.findViewById(R.id.titleFav);
            text_source = itemView.findViewById(R.id.authorFav);
            text_date = itemView.findViewById(R.id.dateFav);
            /*text_description = itemView.findViewById(R.id.description);*/
            img_headline = itemView.findViewById(R.id.newsPicFav);
            btnShare = itemView.findViewById(R.id.btnShareFav);
            cbDelete = itemView.findViewById(R.id.cbDelete);
            btnDelete = itemView.findViewById(R.id.btnDeleteFav);
            linearLayout = itemView.findViewById(R.id.containerListNewsFav);

            btnDelete.setOnClickListener(v -> {
                News newsClicked = newsList.get(getAdapterPosition());
                newsList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                listener.onDeleteButtonPressed(newsClicked);
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}
