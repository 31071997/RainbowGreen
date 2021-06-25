package com.example.rainbowgreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends FirebaseRecyclerAdapter<Model, Adapter.ListViewHolde> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Adapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ListViewHolde holder, int position, @NonNull Model model) {
        Glide.with(holder.imageView.getContext()).load(model.getUrl()).into(holder.imageView);
        holder.textView.setText(model.getText());
    }

    @NonNull
    @Override
    public ListViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle, parent, false);
        return new ListViewHolde(view);
    }

    class ListViewHolde extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public ListViewHolde(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.orchid1);
            textView = (TextView)itemView.findViewById(R.id.orchid_test);
        }
    }
}
