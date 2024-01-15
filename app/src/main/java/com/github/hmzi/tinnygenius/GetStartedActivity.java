package com.github.hmzi.tinnygenius;

import static android.view.animation.AnimationUtils.loadAnimation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.github.hmzi.tinnygenius.Fragments.HomeFragment;
import com.github.hmzi.tinnygenius.databinding.ActivityGetStartedBinding;

public class GetStartedActivity extends AppCompatActivity {
    ActivityGetStartedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        Animation btnAnim =  AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);
        binding = ActivityGetStartedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.guestBtn.setAnimation(btnAnim);
        binding.guestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GetStartedActivity.this, MainActivity.class));
                finish();
            }
        });
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GetStartedActivity.this, LoginActivity.class));
                finish();
            }
        });
        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GetStartedActivity.this, SignupActivity.class));
                finish();
            }
        });
    }
}