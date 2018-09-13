package com.example.suhaib.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by suhaib on 9/8/18.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
    private Context mContext;
    private List<Movie> movieList;

    public Adapter(Context mContext, List<Movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }
    public void setMovieList(List<Movie> movieList){
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @Override
    public Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter.MyViewHolder holder, int position) {
        holder.title.setText(movieList.get(holder.getAdapterPosition()).getOriginalTitle());
        String vote = Double.toString(movieList.get(holder.getAdapterPosition()).getVoteAverage());
        holder.userrating.setText(vote);

        Glide.with(mContext)
                .load(movieList.get(position).getPoster_path())
                .placeholder(R.drawable.load).into(holder.cardImage);

    }

    @Override
    public int getItemCount() {
        if(movieList != null) {
            return movieList.size();
        }
            return  0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView userrating;
        public ImageView cardImage;


        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movietitle);
            userrating = itemView.findViewById(R.id.userrating);
            cardImage = itemView.findViewById(R.id.card_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Movie movie =movieList.get(pos);
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra("original_title",movieList.get(pos).getOriginalTitle());
                        intent.putExtra("poster_path",movieList.get(pos).getPoster_path());
                        intent.putExtra("overview",movieList.get(pos).getOverview());
                        intent.putExtra("vote_average",Double.toString(movieList.get(pos).getVoteAverage()));
                        intent.putExtra("release_date",movieList.get(pos).getReleaseData());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(view.getContext(),movie.getOriginalTitle(),
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }



}
