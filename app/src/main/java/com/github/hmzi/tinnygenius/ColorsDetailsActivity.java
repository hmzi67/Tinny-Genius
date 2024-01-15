package com.github.hmzi.tinnygenius;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.github.hmzi.tinnygenius.Adapter.ColorsAdapter;
import com.github.hmzi.tinnygenius.Classes.SpeakClass;
import com.github.hmzi.tinnygenius.Model.AlphabetsModel;
import com.github.hmzi.tinnygenius.databinding.ActivityColorsDetailsBinding;

import java.util.ArrayList;

public class ColorsDetailsActivity extends AppCompatActivity {

    ActivityColorsDetailsBinding binding;
    private SpeakClass speak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_colors_details);
        binding = ActivityColorsDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        speak = new SpeakClass(this);
        init();
    }

    private void init() {
        // goback
        binding.goBack.setOnClickListener(view -> {
            onBackPressed();
        });

        // Tell user what to do.
        binding.speakDetails.setOnClickListener(view -> {
            speak.speakOut("Press on any color for it's pronunciation.");
        });

        // loading the recycler view adapter.
        ArrayList<AlphabetsModel> allColors = new ArrayList<>();

        // Rendering numbers from 0 to 9
        allColors.add(new AlphabetsModel("Red", "#f21e0f", 0));
        allColors.add(new AlphabetsModel("Green", "#0cc402", 0));
        allColors.add(new AlphabetsModel("Yellow", "#ffff00", 0));
        allColors.add(new AlphabetsModel("Pink", "#f01f61", 0));
        allColors.add(new AlphabetsModel("Orange", "#FFA500", 0));
        allColors.add(new AlphabetsModel("White", "#FFFFFF", 0));
        allColors.add(new AlphabetsModel("Blue", "#0000ff", 0));
        allColors.add(new AlphabetsModel("Gray", "#808080", 0));
        allColors.add(new AlphabetsModel("Gold", "#ffd700", 0));
        allColors.add(new AlphabetsModel("Lime", "#00ff00", 0));
        allColors.add(new AlphabetsModel("Olive", "#808000",0));
        allColors.add(new AlphabetsModel("Cyan", "#00ffff", 0));

        ColorsAdapter adapter = new ColorsAdapter();
        adapter.setAllAlphabets(allColors, ColorsDetailsActivity.this);

        binding.allColors.setAdapter(adapter);
        binding.allColors.setLayoutManager(new GridLayoutManager(this, 1));
    }
}