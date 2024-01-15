package com.github.hmzi.tinnygenius;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.github.hmzi.tinnygenius.Adapter.AlphabetsAdapter;
import com.github.hmzi.tinnygenius.Adapter.ShapeAdapter;
import com.github.hmzi.tinnygenius.Classes.SpeakClass;
import com.github.hmzi.tinnygenius.Model.AlphabetsModel;
import com.github.hmzi.tinnygenius.Model.ShapeModel;
import com.github.hmzi.tinnygenius.databinding.ActivityShapesBinding;

import java.util.ArrayList;

public class ShapesActivity extends AppCompatActivity {
    ActivityShapesBinding binding;
    private SpeakClass speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShapesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        speak = new SpeakClass(this);
        init();
    }
    private void init() {

        //go back
        binding.goBack.setOnClickListener(view ->  {
            onBackPressed();
        });

        // Tell user what to do.
        binding.speakDetails.setOnClickListener(view -> {
            speak.speakOut("Press on any shape for it's pronunciation.");
        });

        // loading the recycler view adapter.
        // data set
        ArrayList<ShapeModel> allShapes = new ArrayList<>();
        allShapes.add(new ShapeModel(String.valueOf(R.drawable.square), "Square"));
        allShapes.add(new ShapeModel(String.valueOf(R.drawable.rectangle), "Rectangle"));
        allShapes.add(new ShapeModel(String.valueOf(R.drawable.star), "Star"));
        allShapes.add(new ShapeModel(String.valueOf(R.drawable.circle), "Circle"));
        allShapes.add(new ShapeModel(String.valueOf(R.drawable.triangle), "Triangle"));
        allShapes.add(new ShapeModel(String.valueOf(R.drawable.cross), "Cross"));
        allShapes.add(new ShapeModel(String.valueOf(R.drawable.octagon), "Octagon"));
        allShapes.add(new ShapeModel(String.valueOf(R.drawable.hexagon), "Hexagon"));
        allShapes.add(new ShapeModel(String.valueOf(R.drawable.parallelogram), "Parallelogram"));
        allShapes.add(new ShapeModel(String.valueOf(R.drawable.pentagon), "Pentagon"));
        allShapes.add(new ShapeModel(String.valueOf(R.drawable.rhombus), "Rhombus"));
        allShapes.add(new ShapeModel(String.valueOf(R.drawable.trapezoid), "Trapezoid"));
        allShapes.add(new ShapeModel(String.valueOf(R.drawable.arrow), "Arrow"));
        allShapes.add(new ShapeModel(String.valueOf(R.drawable.oval), "Oval"));
        allShapes.add(new ShapeModel(String.valueOf(R.drawable.crescent), "crescent"));

        ShapeAdapter adapter = new ShapeAdapter();
        adapter.setAllShapes(allShapes, ShapesActivity.this);

        binding.shapes.setAdapter(adapter);
        binding.shapes.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
