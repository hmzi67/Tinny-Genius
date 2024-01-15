package com.github.hmzi.tinnygenius.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.hmzi.tinnygenius.Classes.SpeakClass;
import com.github.hmzi.tinnygenius.Model.AlphabetsModel;
import com.github.hmzi.tinnygenius.R;

import java.util.ArrayList;

public class AlphabetsAdapter extends RecyclerView.Adapter<AlphabetsAdapter.ViewHolder>{

    private ArrayList<AlphabetsModel> allAlphabets = new ArrayList<>();
    private Context context;
    public void setAllAlphabets(ArrayList<AlphabetsModel> allAlphabets, Context context) {
        this.allAlphabets = allAlphabets;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_alphabets, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpeakClass speak = new SpeakClass(context);

        holder.alphabets_img.setImageResource(Integer.parseInt(String.valueOf(allAlphabets.get(position).getImage())));
        holder.alphabets_at.setText(allAlphabets.get(position).getLetter());

        holder.alphabets_container.setOnClickListener(view -> {
            speak.speakOut(allAlphabets.get(position).getBgColor());

        });
    }
    @Override
    public int getItemCount() {
        return allAlphabets.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView alphabets_at;
        ImageView alphabets_img;
        CardView alphabets_container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // finding views
            alphabets_at = itemView.findViewById(R.id.alphabets_at);
            alphabets_img = itemView.findViewById(R.id.alphabets_img);
            alphabets_container = itemView.findViewById(R.id.alphabets_container);
        }
    }
}
