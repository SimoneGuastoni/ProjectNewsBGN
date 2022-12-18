package com.example.projectnewsbgn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectnewsbgn.Models.News;
import com.example.projectnewsbgn.R;
import com.example.projectnewsbgn.Models.CustomViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    /*<NewsRecyclerViewAdapter.NewsViewHolder>*/
    private Context context;
    private List<News> newsList;
    /*private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onNewsClick(News news);
        void onDeleteButtonPressed(int position);
        void onFavButtonPressed(int adapterPosition);
        void onShareButtonPressed(int adapterPosition);
    }*/

    public NewsRecyclerViewAdapter(Context context,List<News> newsList) {
        this.context = context;
        this.newsList = newsList;

    }

    /*@NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_view_home, parent, false);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_list_view_home,parent,false);
        return new NewsViewHolder(view);
    }*/

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_list_view_home,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.text_title.setText(newsList.get(position).getTitle());
        holder.text_source.setText(newsList.get(position).getSource().getSystemId());

        if(newsList.get(position).getUrlToImage() != null){
            Picasso.get().load(newsList.get(position).getUrlToImage()).into(holder.img_headline);
        }
    }

    /*@Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bind(newsList.get(position));
        holder.textViewTitle.setText(newsList.get(position).getTitle());
        holder.textViewAuthor.setText(newsList.get(position).getSource().getSystemId());

        if(newsList.get(position).getUrlToImage() != null){
            Picasso.get().load(newsList.get(position).getUrlToImage()).into(holder.newsPic);
        }
    }*/

    @Override
    public int getItemCount() {
        if (newsList != null) {
            return newsList.size();
        } else
            return 0;
    }


    /*public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView textViewTitle;
        private final TextView textViewAuthor;
        private final TextView textViewDate;
        private final TextView textViewDescription;
        private final ImageButton btnDelete;
        private final ImageButton btnFav;
        private final ImageButton btnShare;
        private final ImageView newsPic;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.title);
            textViewAuthor = itemView.findViewById(R.id.author);
            textViewDate = itemView.findViewById(R.id.date);
            textViewDescription = itemView.findViewById(R.id.description);
            btnDelete = itemView.findViewById(R.id.btnExample);
            btnFav = itemView.findViewById(R.id.btnFavourite);
            btnShare = itemView.findViewById(R.id.btnShare);
            newsPic = itemView.findViewById(R.id.profilePic);
            /* mi permette quando clicco un oggetto di far eseguire il metodo sotto descritto di onClick
            newsPic.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
            btnShare.setOnClickListener(this);
            btnFav.setOnClickListener(this);
        }

        public void bind(News news) {
            textViewTitle.setText(news.getTitle());
            textViewAuthor.setText(news.getAuthor());
            textViewDescription.setText(news.getDescription());
            textViewDate.setText(news.getPublishedAt());
        }

        @Override
        public void onClick(View view) {

        }*/

       /* public void onClick(View v){
            /* se premo ul pulsante mi entra nell'if altrimenti se premo la cella della recycler view entra nell'else
            if(v.getId() == R.id.btnExample) {
                Snackbar.make(v,"click",Snackbar.LENGTH_SHORT).show();
                newsList.remove(getAdapterPosition());
                /* indica il metodo di rimozione, ci sono anche quelli per aggiungere o modificare
                notifyItemRemoved(getAdapterPosition());
                onItemClickListener.onDeleteButtonPressed(getAdapterPosition());
            }
            else if (v.getId() == R.id.btnFavourite){
                onItemClickListener.onFavButtonPressed(getAdapterPosition());
            }
            else if (v.getId() == R.id.btnShare) {
                onItemClickListener.onShareButtonPressed(getAdapterPosition());
            }
            else{
                /* metodo ceh ci restituisce la posizione dell'elemtno cliccato
                onItemClickListener.onNewsClick(newsList.get(getAdapterPosition()));
            }

        }
    }*/
}