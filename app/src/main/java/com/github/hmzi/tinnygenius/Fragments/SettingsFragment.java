package com.github.hmzi.tinnygenius.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.hmzi.tinnygenius.Classes.ProgressStatus;
import com.github.hmzi.tinnygenius.Model.Users;
import com.github.hmzi.tinnygenius.R;
import com.github.hmzi.tinnygenius.ScoreActivity;
import com.github.hmzi.tinnygenius.databinding.FragmentSettingsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class SettingsFragment extends Fragment {

    FragmentSettingsBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase mDatabase;
    private String userName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        init();
        return binding.getRoot();
    }

    private void init() {
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        SharedPreferences pref = getActivity().getSharedPreferences("Score", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
//        editor.putBoolean("Synchronised", true);
//        editor.apply();
        binding.syncProgress.setChecked(pref.getBoolean("Synchronised", false));

        // Sync progress
        binding.syncProgress.setOnClickListener(view -> {
            syncProgress();
        });
        

        // show userData
        // show the user name
        if (auth.getCurrentUser() != null) {
            mDatabase.getReference().child("Users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users user = snapshot.getValue(Users.class);
                    userName = user.getUserName();
                    binding.userName.setText(user.getUserName());
                    binding.userEmail.setText(user.getUserEmail());
                    if (user.getUserImg().isEmpty())
                        binding.editUserPic.setImageResource(R.drawable.ic_profile);
                    else
                        Picasso.get().load(user.getUserImg()).placeholder(R.drawable.ic_profile).into(binding.editUserPic);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            // Intent call
            binding.progress.setOnClickListener(view -> {
                startActivity(new Intent(getContext(), ScoreActivity.class));
            });
        }

    }
    private void syncProgress() {
        SharedPreferences pref = getActivity().getSharedPreferences("Score", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        if (binding.syncProgress.isChecked()) {
            editor.putBoolean("Synchronised", true);
            editor.apply();
            binding.syncProgress.setChecked(true);

        } else {
            editor.putBoolean("Synchronised", false);
            editor.apply();
            binding.syncProgress.setChecked(false);

        }
    }
}