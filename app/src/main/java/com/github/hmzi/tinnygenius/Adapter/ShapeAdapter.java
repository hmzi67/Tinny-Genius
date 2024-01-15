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
import com.github.hmzi.tinnygenius.Model.ShapeModel;
import com.github.hmzi.tinnygenius.R;

import java.util.ArrayList;

public class ShapeAdapter extends RecyclerView.Adapter<ShapeAdapter.ViewHolder> {

    private ArrayList<ShapeModel> allShapes = new ArrayList<>();
    private Context context;

    public void setAllShapes(ArrayList<ShapeModel> allShapes, Context context ) {
        this.allShapes = allShapes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_shapes, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpeakClass speak = new SpeakClass(context);
        ShapeModel shapeModel = allShapes.get(position);

        holder.shapes_img.setContentDescription(shapeModel.getBgColor());
        holder.shapes_img.setImageResource(Integer.parseInt(String.valueOf(shapeModel.getImage())));
        holder.shapes_img.setOnClickListener(view -> {
            speak.speakOut(allShapes.get(position).getBgColor());
        });
    }

    @Override
    public int getItemCount() {
        return allShapes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView shapes_img;
        CardView shapes_container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // finding views
            shapes_img = itemView.findViewById(R.id.shapes_img);
            shapes_container = itemView.findViewById(R.id.alphabets_container);
        }
    }
}
