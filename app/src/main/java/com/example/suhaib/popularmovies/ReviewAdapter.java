package com.example.suhaib.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private Context mContext;
    private List<Review> reviewList;

    public void setReviewList(List<Review> reviewList){
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }

    public ReviewAdapter(Context context, List<Review> reviews) {
        mContext = context;
        this.reviewList = reviewList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reviews_card, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.author.setText(reviewList.get(position).getAuthor());
        holder.content.setText(reviewList.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        if(reviewList != null) {
            return reviewList.size();
        }
        return  0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView author;
        public TextView content;


        public ViewHolder(View itemView) {

            super(itemView);
            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.content);

        }
    }
}
