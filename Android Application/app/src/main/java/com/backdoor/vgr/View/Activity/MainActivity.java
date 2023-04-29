package com.backdoor.vgr.View.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Activity.Fragment.Games_Fragment;
import com.backdoor.vgr.View.Activity.Fragment.Profile_Fragment;
import com.backdoor.vgr.View.Model.PerfConfig;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static PerfConfig perfConfig;
    private BottomNavigationView navigationView;
    private int id;
    private Games_Fragment dashBoardFragment;

    public static String IMAGE_LINK = "imageLnk";
    public static String IMAGE_PATH = "image_path";
    public static String GAME_ID = "gameId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        perfConfig = new PerfConfig(this);

        navigationView = findViewById(R.id.bottomNavigationView);

        dashBoardFragment = new Games_Fragment();
        final Profile_Fragment profile_fragment = new Profile_Fragment();

        navigationView.setOnNavigationItemSelectedListener(menuItem -> {
            id = menuItem.getItemId();
            if (id == R.id.nav_games) {
                setFragment(dashBoardFragment);
                return true;
            } else if (id == R.id.nav_profile) {
                setFragment(profile_fragment);
                return true;
            }
            return false;
        });
        navigationView.setSelectedItemId(R.id.nav_games);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.adminFrame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (id != R.id.nav_games) {
            navigationView.setSelectedItemId(R.id.nav_games);
            setFragment(dashBoardFragment);
        } else {
            super.onBackPressed();
        }
    }
}