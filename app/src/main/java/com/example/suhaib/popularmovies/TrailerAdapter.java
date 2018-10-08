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

/**
 * Created by suhaib on 10/8/18.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {
    private Context mContext;
    private List<Trailer> trailerList;

    public void setTrailerList(List<Trailer> trailerList){
        this.trailerList = trailerList;
        notifyDataSetChanged();
    }

    public TrailerAdapter(Context context, List<Trailer> trailerList) {
        mContext = context;
        this.trailerList = trailerList;

    }

    @Override
    public TrailerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_card, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.MyViewHolder holder, int position) {
        holder.titel.setText(trailerList.get(position).getName());


    }

    @Override
    public int getItemCount() {
        if(trailerList != null) {
            return trailerList.size();
        }
        return  0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView titel;
        public ImageView thumbnail;


        public MyViewHolder(View itemView) {

            super(itemView);
            titel = itemView.findViewById(R.id.title1);
            thumbnail = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Trailer clickedDataItem = trailerList.get(pos);
                        String videoID = clickedDataItem.getKey();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+videoID));
                        intent.putExtra("VIDEO_ID", videoID);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(),"You clicked "+clickedDataItem.getName()
                                ,Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}
