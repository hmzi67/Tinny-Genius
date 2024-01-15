package com.github.hmzi.tinnygenius.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.hmzi.tinnygenius.Classes.SpeakClass;
import com.github.hmzi.tinnygenius.Model.AlphabetsModel;
import com.github.hmzi.tinnygenius.R;

import java.util.ArrayList;

public class ColorsAdapter  extends RecyclerView.Adapter<ColorsAdapter.ViewHolder>{

    private ArrayList<AlphabetsModel> allAlphabets = new ArrayList<>();
    private Context context;

    public void setAllAlphabets(ArrayList<AlphabetsModel> allAlphabets, Context context) {
        this.allAlphabets = allAlphabets;
        this.context = context;
    }

    @NonNull
    @Override
    public ColorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_colors, parent, false);
        ColorsAdapter.ViewHolder viewHolder = new ColorsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ColorsAdapter.ViewHolder holder, int position) {
        SpeakClass speak = new SpeakClass(context);

        holder.colors_container.setBackgroundColor(Color.parseColor(allAlphabets.get(position).getBgColor()));
        holder.colors_at.setText(allAlphabets.get(position).getLetter());

        holder.colors_container.setOnClickListener(view -> {
            speak.speakOut(allAlphabets.get(position).getLetter());
        });
    }

    @Override
    public int getItemCount() {
        return allAlphabets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView colors_at;
        CardView colors_container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // finding views
            colors_at = itemView.findViewById(R.id.colors_at);
            colors_container = itemView.findViewById(R.id.colors_container);
        }
    }
}
