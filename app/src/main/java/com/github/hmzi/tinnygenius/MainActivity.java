package com.github.hmzi.tinnygenius;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Switch;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.github.hmzi.tinnygenius.Fragments.HomeFragment;
import com.github.hmzi.tinnygenius.Fragments.ProfileFragment;
import com.github.hmzi.tinnygenius.Fragments.SettingsFragment;
import com.github.hmzi.tinnygenius.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navView;
    protected final int setting = 1;
    protected final int home = 2;
    protected final int profile = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(savedInstanceState);
    }
    private void init(Bundle savedInstanceState) {
        MeowBottomNavigation bottomNavigation = findViewById(R.id.navBarView);
        bottomNavigation.add(new MeowBottomNavigation.Model(setting, R.drawable.setting_icon));
        bottomNavigation.add(new MeowBottomNavigation.Model(home, R.drawable.home_icon));
        bottomNavigation.add(new MeowBottomNavigation.Model(profile, R.drawable.profile_logo));

        //Selected fragment
        bottomNavigation.show(home, true);

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                //Initialize Fragments according to its id
                Fragment fragment = null;
                switch (model.getId()) {
                    case 1:
                        fragment = new SettingsFragment();
                        break;
                    case 2:
                        fragment = new HomeFragment();
                        break;
                    case 3:
                        fragment = new ProfileFragment();
                        break;
                    default:
                        return null;
                };
                LoadAndReplaceFragments(fragment);
                return null;
            }
        });
    }
    private void LoadAndReplaceFragments(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment,null)
                .commit();
    }

}