package com.github.hmzi.tinnygenius;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.github.hmzi.tinnygenius.Adapter.AnimalAdapter;
import com.github.hmzi.tinnygenius.Adapter.ShapeAdapter;
import com.github.hmzi.tinnygenius.Classes.SpeakClass;
import com.github.hmzi.tinnygenius.Model.AnimalModel;
import com.github.hmzi.tinnygenius.Model.ShapeModel;
import com.github.hmzi.tinnygenius.databinding.ActivityAnimalBinding;
import com.github.hmzi.tinnygenius.databinding.ActivityShapesBinding;

import java.util.ArrayList;

public class AnimalActivity extends AppCompatActivity {
    private ActivityAnimalBinding binding;
    private SpeakClass speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnimalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         speak = new SpeakClass(this);
        init();
    }
    private void init(){
        //goback
        binding.goBack.setOnClickListener(view -> {
            onBackPressed();
        });

        //speak details
        binding.speakDetails.setOnClickListener(view -> {
            speak.speakOut("Press on any shape for it's pronunciation.");
        });


        ArrayList<AnimalModel> allAnimal = new ArrayList<>();
        allAnimal.add(new AnimalModel(String.valueOf(R.drawable.cow), "Cow"));
        allAnimal.add(new AnimalModel(String.valueOf(R.drawable.elephant), "elephant"));
        allAnimal.add(new AnimalModel(String.valueOf(R.drawable.frog), "frog"));
        allAnimal.add(new AnimalModel(String.valueOf(R.drawable.lion), "lion"));
        allAnimal.add(new AnimalModel(String.valueOf(R.drawable.giraffe), "giraffe"));
        allAnimal.add(new AnimalModel(String.valueOf(R.drawable.monkey), "monkey"));
        allAnimal.add(new AnimalModel(String.valueOf(R.drawable.panda), "panda"));
        allAnimal.add(new AnimalModel(String.valueOf(R.drawable.sheep), "sheep"));
        allAnimal.add(new AnimalModel(String.valueOf(R.drawable.tortoise), "tortoise"));

        AnimalAdapter adapter = new AnimalAdapter();
        adapter.setAllanimals(allAnimal, AnimalActivity.this);

        binding.animalsRv.setAdapter(adapter);
        binding.animalsRv.setLayoutManager(new GridLayoutManager(this, 2));
    }
}