package com.github.hmzi.tinnygenius;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.github.hmzi.tinnygenius.Adapter.AlphabetsAdapter;
import com.github.hmzi.tinnygenius.Classes.SpeakClass;
import com.github.hmzi.tinnygenius.Model.AlphabetsModel;
import com.github.hmzi.tinnygenius.databinding.ActivityNumbersDetailsBinding;

import java.util.ArrayList;

public class NumbersDetailsActivity extends AppCompatActivity {

    ActivityNumbersDetailsBinding binding;

    private SpeakClass speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_numbers_details);

        binding = ActivityNumbersDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        speak = new SpeakClass(this);
        init();
    }
    private void init() {
        // goBack
        binding.goBack.setOnClickListener(view -> {
            onBackPressed();
        });

        // Tell user what to do.
        binding.speakDetails.setOnClickListener(view -> {
            speak.speakOut("Press on any number for it's pronunciation.");
        });

        // loading the recycler view adapter.
        ArrayList<AlphabetsModel> allAlphabets = new ArrayList<>();

        // Rendering numbers from 0 to 9
//        for (int i = 0; i < 10; i++) {
//            int id = getResources().getIdentifier("_"+i , "drawable", getPackageName());
//            allAlphabets.add(new AlphabetsModel(String.valueOf(i), String.valueOf(i), id));
//        }
        allAlphabets.add(new AlphabetsModel(String.valueOf(0), "0", R.drawable._0));
        allAlphabets.add(new AlphabetsModel(String.valueOf(1), "1", R.drawable._1));
        allAlphabets.add(new AlphabetsModel(String.valueOf(2), "2", R.drawable._2));
        allAlphabets.add(new AlphabetsModel(String.valueOf(3), "3", R.drawable._3));
        allAlphabets.add(new AlphabetsModel(String.valueOf(4), "4", R.drawable._4));
        allAlphabets.add(new AlphabetsModel(String.valueOf(5), "5", R.drawable._5));
        allAlphabets.add(new AlphabetsModel(String.valueOf(6), "6", R.drawable._6));
        allAlphabets.add(new AlphabetsModel(String.valueOf(7), "7", R.drawable._7));
        allAlphabets.add(new AlphabetsModel(String.valueOf(8), "8", R.drawable._8));
        allAlphabets.add(new AlphabetsModel(String.valueOf(9), "9", R.drawable._9));

        AlphabetsAdapter adapter = new AlphabetsAdapter();
        adapter.setAllAlphabets(allAlphabets, NumbersDetailsActivity.this);

        binding.allNumbers.setAdapter(adapter);
        binding.allNumbers.setLayoutManager(new GridLayoutManager(this, 3));
    }
}