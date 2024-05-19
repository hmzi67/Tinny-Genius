package com.github.hmzi.tinnygenius.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.hmzi.tinnygenius.AiScan;
import com.github.hmzi.tinnygenius.AlphabetsDetailsActivity;
import com.github.hmzi.tinnygenius.AnimalActivity;
import com.github.hmzi.tinnygenius.BirdsActivity;
import com.github.hmzi.tinnygenius.Classes.SpeakClass;
import com.github.hmzi.tinnygenius.ColorsDetailsActivity;
import com.github.hmzi.tinnygenius.Model.Users;
import com.github.hmzi.tinnygenius.NumbersDetailsActivity;
import com.github.hmzi.tinnygenius.Quiz;
import com.github.hmzi.tinnygenius.R;
import com.github.hmzi.tinnygenius.ShapesActivity;
import com.github.hmzi.tinnygenius.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private SpeakClass speak;

    private String userName;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container, false);
        init();
        speak = new SpeakClass(getContext());
        return binding.getRoot();
    }
    private void init() {
        // ready the database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        // show the user name
        if (mAuth.getCurrentUser() != null){
        mDatabase.getReference().child("Users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                userName = user.getUserName();
                if (userName != null) binding.userName.setText("Hello, " + userName);
                if (user.getUserImg().isEmpty())
                    binding.editUserPic.setImageResource(R.drawable.ic_profile);
                else
                    Picasso.get().load(user.getUserImg()).placeholder(R.drawable.ic_profile).into(binding.editUserPic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        }
        // alphabets intent call
        binding.alphabets.setOnClickListener(view -> {
            getContext().startActivity(new Intent(getContext(), AlphabetsDetailsActivity.class));
            speak.speakOut("Welcome to Alphabets");
        });

        // numbers intent call
        binding.numbers.setOnClickListener(view -> {
            getContext().startActivity(new Intent(getContext(), NumbersDetailsActivity.class));
            speak.speakOut("Welcome to Numbers");
        });

        // colors intent call
        binding.colors.setOnClickListener(view -> {
            getContext().startActivity(new Intent(getContext(), ColorsDetailsActivity.class));
            speak.speakOut("Welcome to Colors Section");
        });
        // colors intent call
        binding.practice.setOnClickListener(view -> {
            SharedPreferences pref = getActivity().getSharedPreferences("Score", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            if(pref.getInt("index", -1) > 0){
                Intent intent = new Intent(getContext(), Quiz.class);
                intent.putExtra("continue", true);
                startActivity(intent);
                speak.speakOut("Welcome to Practice section");

            }
            else {
                getContext().startActivity(new Intent(getContext(), Quiz.class));
                speak.speakOut("Welcome to Practice section");
            }



        });

        //Shapes intent call
        binding.shapes.setOnClickListener(view -> {
            getContext().startActivity(new Intent(getContext(), ShapesActivity.class));
            speak.speakOut("Welcome to Shapes section");
        });

        //Animal intent call
        binding.animals.setOnClickListener(view -> {
            getContext().startActivity(new Intent(getContext(), AnimalActivity.class));
            speak.speakOut("Welcome to the animals section");
        });

        //Animal intent call
        binding.birds.setOnClickListener(view -> {
            getContext().startActivity(new Intent(getContext(), BirdsActivity.class));
            speak.speakOut("Welcome to the birds section");
        });

        //Speak details
        binding.speakDetails.setOnClickListener(view -> {
            speak.speakOut("Welcome to the home screen");
        });

        //Ai Scan
        binding.aiScan.setOnClickListener(view -> {
            getContext().startActivity(new Intent(getContext(), AiScan.class));
            speak.speakOut("Welcome to the AI SCAN screen");
        });
    }
}