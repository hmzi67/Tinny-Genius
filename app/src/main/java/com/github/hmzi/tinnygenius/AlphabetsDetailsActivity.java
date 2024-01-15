package com.github.hmzi.tinnygenius;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.github.hmzi.tinnygenius.Adapter.AlphabetsAdapter;
import com.github.hmzi.tinnygenius.Classes.SpeakClass;
import com.github.hmzi.tinnygenius.Model.AlphabetsModel;
import com.github.hmzi.tinnygenius.databinding.ActivityAlphabetsDetailsBinding;

import java.util.ArrayList;

public class AlphabetsDetailsActivity extends AppCompatActivity {

    ActivityAlphabetsDetailsBinding binding;

    private SpeakClass speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_alphabets_details);
        binding = ActivityAlphabetsDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        speak = new SpeakClass(this);
        init();
    }

    private void init() {
        // goBack method.
        binding.goBack.setOnClickListener(view ->  {
            onBackPressed();
        });

        // Tell user what to do.
        binding.speakDetails.setOnClickListener(view -> {
            speak.speakOut("Press on any letter for it's phonetics sound.");
            speak.shutDown();
        });

        // loading the recycler view adapter.
        ArrayList<AlphabetsModel> allAlphabets = new ArrayList<>();

        // Rendering letters from A to Z
//        for (char i = 'A'; i <= 'Z'; i++) {
//            String letter = String.valueOf(i);
//            String alphabets = letter;
//            int id = getResources().getIdentifier(letter.toLowerCase(), "drawable", getPackageName());
//            String alphabetLetter = getString(getResources().getIdentifier(alphabets, "string", getPackageName()));
//            allAlphabets.add(new AlphabetsModel( String.valueOf(i), alphabetLetter, id ));
//        }
        allAlphabets.add(new AlphabetsModel("A", getString(R.string.A), R.drawable.a));
        allAlphabets.add(new AlphabetsModel("B", getString(R.string.B), R.drawable.b));
        allAlphabets.add(new AlphabetsModel("C", getString(R.string.C), R.drawable.c));
        allAlphabets.add(new AlphabetsModel("D", getString(R.string.D), R.drawable.d));
        allAlphabets.add(new AlphabetsModel("E", getString(R.string.E), R.drawable.e));
        allAlphabets.add(new AlphabetsModel("F", getString(R.string.F), R.drawable.f));
        allAlphabets.add(new AlphabetsModel("G", getString(R.string.G), R.drawable.g));
        allAlphabets.add(new AlphabetsModel("H", getString(R.string.H), R.drawable.h));
        allAlphabets.add(new AlphabetsModel("I", getString(R.string.I), R.drawable.i));
        allAlphabets.add(new AlphabetsModel("J", getString(R.string.J), R.drawable.j));
        allAlphabets.add(new AlphabetsModel("K", getString(R.string.K), R.drawable.k));
        allAlphabets.add(new AlphabetsModel("L", getString(R.string.L), R.drawable.l));
        allAlphabets.add(new AlphabetsModel("M", getString(R.string.M), R.drawable.m));
        allAlphabets.add(new AlphabetsModel("N", getString(R.string.N), R.drawable.n));
        allAlphabets.add(new AlphabetsModel("O", getString(R.string.O), R.drawable.o));
        allAlphabets.add(new AlphabetsModel("P", getString(R.string.P), R.drawable.p));
        allAlphabets.add(new AlphabetsModel("Q", getString(R.string.Q), R.drawable.q));
        allAlphabets.add(new AlphabetsModel("R", getString(R.string.R), R.drawable.r));
        allAlphabets.add(new AlphabetsModel("S", getString(R.string.S), R.drawable.s));
        allAlphabets.add(new AlphabetsModel("T", getString(R.string.T), R.drawable.t));
        allAlphabets.add(new AlphabetsModel("U", getString(R.string.U), R.drawable.u));
        allAlphabets.add(new AlphabetsModel("V", getString(R.string.V), R.drawable.v));
        allAlphabets.add(new AlphabetsModel("W", getString(R.string.W), R.drawable.w));
        allAlphabets.add(new AlphabetsModel("X", getString(R.string.X), R.drawable.x));
        allAlphabets.add(new AlphabetsModel("Y", getString(R.string.Y), R.drawable.y));
        allAlphabets.add(new AlphabetsModel("Z", getString(R.string.Z), R.drawable.z));

        AlphabetsAdapter adapter = new AlphabetsAdapter();
        adapter.setAllAlphabets(allAlphabets, AlphabetsDetailsActivity.this);

        binding.allAlphabets.setAdapter(adapter);
        binding.allAlphabets.setLayoutManager(new GridLayoutManager(this, 3));
    }
}