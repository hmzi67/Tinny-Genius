package com.github.hmzi.tinnygenius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.danny_jiang.tracinglibrary.bean.LetterFactory;
import com.danny_jiang.tracinglibrary.view.TracingLetterView;
import com.github.hmzi.tinnygenius.Model.Users;
import com.github.hmzi.tinnygenius.databinding.ActivityQuizBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Angle;
import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.Spread;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class Quiz extends AppCompatActivity {
    private KonfettiView konfettiView = null;
    private ActivityQuizBinding binding;
    private int i;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    Animation btnAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // animation init
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);
        //firebase init
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        // Celebration on tab
        konfettiView = findViewById(R.id.konfettiView);


        // goBack method.
        binding.goBack.setOnClickListener(view ->  {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });


        // getting letter index
        i = getIntent().getIntExtra("Letter", -1);

        // button visibility gone
        if (i <= -1  ) {
            binding.previousBtn.setVisibility(View.GONE);
        }
        if (i == 24) {
            binding.nextBtn.setVisibility(View.GONE);
        }


        SharedPreferences pref = getSharedPreferences("Score", MODE_PRIVATE);

        binding.score.setText(String.valueOf(pref.getInt("Score", 0)));

        if(getIntent().getBooleanExtra("continue", false) ) {
            binding.alertBox.setVisibility(View.VISIBLE);
            binding.alertBox.setAnimation(btnAnim);
            binding.continuePractice.setOnClickListener(view -> {
                // i = pref.getInt("index", -1);
                Intent intent = new Intent(this, Quiz.class);
                intent.putExtra("Letter", pref.getInt("index", -1));
                startActivity(intent);
            });
        }


        //private method calling
        letterChange(i);
        //next btn Intent calling
        binding.nextBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Quiz.class);
            intent.putExtra("Letter", i + 1);
            startActivity(intent);
        });

        //previous btn Intent calling
        binding.previousBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Quiz.class);
            intent.putExtra("Letter", i - 1);
            startActivity(intent);

        });

        //reset btn Intent calling
        binding.resetBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Quiz.class);
            intent.putExtra("Letter", i);
            startActivity(intent);
        });

    }


    private void letterChange(int i) {
        ++i;
//        SharedPreferences pref = getSharedPreferences("Score", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putInt("index", i);
//        editor.apply();

        binding.letter.setLetterChar(LetterFactory.A + i);
        binding.letter.setPointColor(Color.RED);
        binding.letter.setStrokeColor(Color.RED);

        // Want to see magic? Original magic goes here.
        binding.letter.setInstructMode(false);
        binding.magic.setOnClickListener(view -> {
            binding.letter.setInstructMode(true);
        });

        int finalI = i;
        binding.letter.setTracingListener(new TracingLetterView.TracingListener() {
            @Override
            public void onFinish() {
                // Score added
                SharedPreferences pref = getSharedPreferences("Score", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                int score = pref.getInt("Score", 0);
                binding.score.setText(String.valueOf(pref.getInt("Score", 0)));
                score += 100;
                editor.putInt("Score", score);
                editor.apply();
                binding.score.setText(String.valueOf(pref.getInt("Score", 0)));

                // storing traced letter
                editor.putInt("index", finalI);
                editor.apply();

                if(pref.getBoolean("Synchronised", false)) {
                    // Storing score on data base
                    String finalScore = String.valueOf(score);
                    if (auth.getCurrentUser() != null) {
                        firebaseDatabase.getReference().child("Users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Users userScore = snapshot.getValue(Users.class);
                                assert userScore != null;
                                userScore.setUserScore(finalScore);
                                firebaseDatabase.getReference().child("Users").child(auth.getCurrentUser().getUid()).setValue(userScore);

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(Quiz.this, "Internet Issue", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }



                Toast.makeText(Quiz.this,"tracing finished", Toast.LENGTH_SHORT).show();
                // calling celebrations function
                celebrate();
                rain();
                explode();
            }

            @Override
            public void onTracing(float x, float y) {
                Log.e("ABC", "tracing x : " + x + " y : " + y);
            }
        });
    }


    // celebration after finish tracing
    private void celebrate() {
        EmitterConfig emitterConfig = new Emitter(5, TimeUnit.SECONDS).perSecond(30);
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        .angle(Angle.RIGHT - 45)
                        .spread(Spread.SMALL)
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                        .setSpeedBetween(10f, 30f)
                        .position(new Position.Relative(0.0, 0.5))
                        .build(),
                new PartyFactory(emitterConfig)
                        .angle(Angle.LEFT + 45)
                        .spread(Spread.SMALL)
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                        .setSpeedBetween(10f, 30f)
                        .position(new Position.Relative(1.0, 0.5))
                        .build());
    }
    public void rain() {
        EmitterConfig emitterConfig = new Emitter(5, TimeUnit.SECONDS).perSecond(100);
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        .angle(Angle.BOTTOM)
                        .spread(Spread.ROUND)
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                        .setSpeedBetween(0f, 15f)
                        .position(new Position.Relative(0.0, 0.0).between(new Position.Relative(1.0, 0.0)))
                        .build());
    }
    public void explode() {
        EmitterConfig emitterConfig = new Emitter(100L, TimeUnit.MILLISECONDS).max(100);
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        .spread(360)
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                        .setSpeedBetween(0f, 30f)
                        .position(new Position.Relative(0.5, 0.3))
                        .build());
    }
}
