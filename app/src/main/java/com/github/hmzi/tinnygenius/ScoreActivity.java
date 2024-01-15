package com.github.hmzi.tinnygenius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.github.hmzi.tinnygenius.Model.Users;
import com.github.hmzi.tinnygenius.databinding.ActivityScoreBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ScoreActivity extends AppCompatActivity {
    ActivityScoreBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    private String userName;
    private String userScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        init();
    }
    private void init() {
        firebaseDatabase.getReference().child("Users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                userName = user.getUserName();
                binding.userName.setText(user.getUserName());
                binding.userEmail.setText(user.getUserEmail());
                binding.userScore.setText(user.getUserScore());
                if (user.getUserImg().isEmpty())
                    binding.editUserPic.setImageResource(R.drawable.ic_profile);
                else
                    Picasso.get().load(user.getUserImg()).placeholder(R.drawable.ic_profile).into(binding.editUserPic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ScoreActivity.this, "Internet issue", Toast.LENGTH_SHORT).show();
            }
        });

        binding.resetScore.setOnClickListener(view -> {
            binding.userScore.setText("0");
            if(auth.getCurrentUser() != null) {
                firebaseDatabase.getReference().child("Users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (auth.getCurrentUser() != null) {
                            Users user = snapshot.getValue(Users.class);
                            user.setUserScore("0");
                            firebaseDatabase.getReference().child("Users").child(auth.getCurrentUser().getUid()).setValue(user);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ScoreActivity.this, "Internet issue", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            SharedPreferences pref = getSharedPreferences("Score", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("Score", 0);
            editor.apply();

        });
        binding.goBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}