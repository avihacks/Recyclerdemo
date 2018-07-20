package com.Andro.recyclerviewdemo;

import android.support.design.widget.Snackbar;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Andro.recyclerviewdemo.Helper.ItemTouchHelperAdapter;
import com.Andro.recyclerviewdemo.Helper.OnStartDragListener;

import java.util.Collections;
import java.util.List;

/**
 * Created by @vi on 4/7/17.
 */

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.MyViewHolder>  implements ItemTouchHelperAdapter {

    private List<Movie> moviesList;
    private final OnStartDragListener mDragStartListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, year, genre;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            genre = (TextView) itemView.findViewById(R.id.genre);
            year = (TextView) itemView.findViewById(R.id.year);

        }
    }

    public MoviesRecyclerAdapter(List<Movie> moviesList,OnStartDragListener dragStartListener) {
        this.moviesList = moviesList;
        mDragStartListener = dragStartListener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());

        holder.year.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
     
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(moviesList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(final int position, final RecyclerView recyclerView) {
        final Movie tempRemoved = moviesList.get(position);
        moviesList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount() - position);
        notifyDataSetChanged();

        Snackbar snackbar = Snackbar
                .make(recyclerView,  " item removed", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        moviesList.add(position, tempRemoved);
                        notifyItemInserted(position);
                        recyclerView.scrollToPosition(position);
                        //updateCardList();
                        Snackbar snackBar = Snackbar.make(recyclerView,  " item restored!", Snackbar.LENGTH_LONG);
                        snackBar.show();
                    }
                });

        snackbar.show();
    }

}
