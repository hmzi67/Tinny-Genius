package com.github.hmzi.tinnygenius;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.github.hmzi.tinnygenius.Adapter.AnimalAdapter;
import com.github.hmzi.tinnygenius.Classes.SpeakClass;
import com.github.hmzi.tinnygenius.Model.AnimalModel;
import com.github.hmzi.tinnygenius.databinding.ActivityBirdsBinding;

import java.util.ArrayList;

public class BirdsActivity extends AppCompatActivity {
    ActivityBirdsBinding binding;
    private SpeakClass speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBirdsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        speak = new SpeakClass(this);
        init();

    }
    private void init() {
        //goback
        binding.goBack.setOnClickListener(view -> {
            onBackPressed();
        });

        //speak details
        binding.speakDetails.setOnClickListener(view -> {
            speak.speakOut("Press on any shape for it's pronunciation.");
        });

        ArrayList<AnimalModel> allAnimal = new ArrayList<>();
        allAnimal.add(new AnimalModel(String.valueOf(R.drawable.sparrow), "sparrow"));
        allAnimal.add(new AnimalModel(String.valueOf(R.drawable.owl), "owl"));
        allAnimal.add(new AnimalModel(String.valueOf(R.drawable.parrot), "parrot"));
        allAnimal.add(new AnimalModel(String.valueOf(R.drawable.eagle), "eagle"));
        allAnimal.add(new AnimalModel(String.valueOf(R.drawable.toucan), "toucan"));
        allAnimal.add(new AnimalModel(String.valueOf(R.drawable.duck), "duck"));
        allAnimal.add(new AnimalModel(String.valueOf(R.drawable.flamingo), "flamingo"));

        AnimalAdapter adapter = new AnimalAdapter();
        adapter.setAllanimals(allAnimal, BirdsActivity.this);

        binding.birdsRv.setAdapter(adapter);
        binding.birdsRv.setLayoutManager(new GridLayoutManager(this, 2));
    }
}