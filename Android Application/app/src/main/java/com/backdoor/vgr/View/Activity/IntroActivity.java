package com.backdoor.vgr.View.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.backdoor.vgr.Model.RoomDB.Game.GameDao;
import com.backdoor.vgr.Model.RoomDB.Review.ReviewDao;
import com.backdoor.vgr.Model.RoomDB.ReviewQuestDatabase;
import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Model.PerfConfig;
import com.backdoor.vgr.databinding.ActivityIntroBinding;
import com.backdoor.vgr.viewmodel.AuthViewModel;

public class IntroActivity extends AppCompatActivity {

    public static ReviewQuestDatabase database;
    public GameDao gameDao = null;
    public ReviewDao reviewDao = null;

    @SuppressLint("StaticFieldLeak")
    public static PerfConfig perfConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityIntroBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_intro);
        AuthViewModel viewModel = ViewModelProviders.of(IntroActivity.this).get(AuthViewModel.class);
        viewModel.setActivity(this);
        binding.setAuthViewModel(viewModel);
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());
        viewModel.setView(binding.getRoot());

        perfConfig = new PerfConfig(this);

        database = ReviewQuestDatabase.Companion.getDatabase(this);
        gameDao = ReviewQuestDatabase.Companion.getDatabase(this).getGameDao();
        reviewDao = ReviewQuestDatabase.Companion.getDatabase(this).getReviewDao();

        viewModel.checkAlreadyLoggedIn();

    }
}