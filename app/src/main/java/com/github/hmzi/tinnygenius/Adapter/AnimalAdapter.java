package com.github.hmzi.tinnygenius.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.hmzi.tinnygenius.Classes.SpeakClass;
import com.github.hmzi.tinnygenius.Model.AnimalModel;
import com.github.hmzi.tinnygenius.R;

import java.util.ArrayList;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder> {
    private ArrayList<AnimalModel> allanimals = new ArrayList<>();
    private Context context;

    public void setAllanimals(ArrayList<AnimalModel> allanimals, Context context ) {
        this.allanimals = allanimals;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_animal, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpeakClass speak = new SpeakClass(context);
        AnimalModel animalModel = allanimals.get(position);

        holder.animal_img.setImageResource(Integer.parseInt(String.valueOf(animalModel.getImage())));
        holder.animal_img.setOnClickListener(view -> {
            speak.speakOut(allanimals.get(position).getText());
        });
    }

    @Override
    public int getItemCount() {
        return allanimals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView animal_img;
        CardView animal_container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // finding views
            animal_img = itemView.findViewById(R.id.animal_img);
            animal_container = itemView.findViewById(R.id.animal_container);
        }
    }

}
