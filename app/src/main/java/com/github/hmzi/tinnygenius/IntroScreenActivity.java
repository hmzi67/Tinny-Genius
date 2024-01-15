package com.github.hmzi.tinnygenius;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.github.hmzi.tinnygenius.Adapter.IntroViewPageAdapter;
import com.github.hmzi.tinnygenius.Classes.ScreenItem;
import com.github.hmzi.tinnygenius.databinding.ActivityIntroScreenBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroScreenActivity extends AppCompatActivity {
    ActivityIntroScreenBinding binding;
    private ViewPager screenPager;
    IntroViewPageAdapter introViewPagerAdapter;
    private Button btnNext;
    TabLayout tabIndicator;
    int position = 0;
    Button btnGetStarted;
    Animation btnAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);

        // when the activity is about to be launch we need to check id it's opened before or not
        if (restorePrefData() ) {
            Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(mainActivity);
            finish();
        }

        // tab init
        tabIndicator = findViewById(R.id.tab_indicator);
        // btn init
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);

        // fill list screen
        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Alphabets", "An alphabet is a standardized set of basic written graphemes (called letters) representing phonemes, units of sounds that distinguish words, of certain spoken languages. ", R.drawable.img1));
        mList.add(new ScreenItem("Numbers", "A number is a mathematical object used to count, measure, and label. The original examples are the natural numbers 1, 2, 3, 4, and so forth. Numbers can be represented in language with number words. ", R.drawable.img2));
        mList.add(new ScreenItem("Colors", "Color (American English) or colour (Commonwealth English) is the visual perception based on the electromagnetic spectrum. Though color is not an inherent property of matter, color perception is related to an object's light absorption, reflection, emission spectra and interference.", R.drawable.img3));

        // setup viewpager
        screenPager = findViewById(R.id.screen_viewpager2);
        introViewPagerAdapter = new IntroViewPageAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        // setup tabLayout with viewPager
        tabIndicator.setupWithViewPager(screenPager);

        //next button click Listener
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = screenPager.getCurrentItem();
                if(position < mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }
                //when we reach the last page
                // will show get started button and hide all tab indicators
                if(position == mList.size() -1){
                    loadLastScreen();
                }
            }
        });

        // tab layout add change Listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == mList.size() -1) {
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroScreenActivity.this, GetStartedActivity.class));
                savePrefsData();
                finish();
            }
        });
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = pref.getBoolean("isIntroOpened", false);
        return isIntroActivityOpenedBefore;
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.commit();
    }

    // will show getstarted button and hide all tab indicators
    private void loadLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

        // todo: add an animation to get started button
        // setup animation
        btnGetStarted.setAnimation(btnAnim);
    }
}