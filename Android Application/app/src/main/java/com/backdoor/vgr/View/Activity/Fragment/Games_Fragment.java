package com.backdoor.vgr.View.Activity.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.backdoor.vgr.Model.RoomDB.GameDao;
import com.backdoor.vgr.Model.RoomDB.GameDataEntity;
import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Activity.IntroActivity;
import com.backdoor.vgr.View.Model.Game.GameAdapter;
import com.backdoor.vgr.View.Model.Game.GameDetailsContact;
import com.backdoor.vgr.databinding.FragmentGamesBinding;
import com.backdoor.vgr.viewmodel.GameViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Games_Fragment extends Fragment {

    private FragmentGamesBinding binding;

    private GameAdapter adapter;
    private List<GameDetailsContact> gameDetailsContactList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_games, container, false);
        View v = binding.getRoot();
        GameViewModel viewModel = ViewModelProviders.of(Games_Fragment.this).get(GameViewModel.class);
        viewModel.setFragmentActivity(getActivity());
        binding.setDashBoardViewModel(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.setView(v);
        viewModel.setUserInfo();

        recyclerViewInit(v);

        viewModel.getAllGameList();

        listChange(viewModel);

        binding.swipeRefreshGameFragment.setOnRefreshListener(viewModel::getAllGameList);

        return v;
    }

    private void listChange(GameViewModel viewModel) {
        viewModel.getGameDetailsContactList().observe(getViewLifecycleOwner(), gameDetailsContactList -> {
            this.gameDetailsContactList = gameDetailsContactList;
            if (this.gameDetailsContactList.size() > 0) {
                binding.noDataFoundGames.setVisibility(View.GONE);
                binding.gameRecyclerView.setVisibility(View.VISIBLE);
            } else {
                binding.noDataFoundGames.setVisibility(View.VISIBLE);
                binding.gameRecyclerView.setVisibility(View.GONE);
            }
            adapter.notifyChangeData(gameDetailsContactList);
            binding.swipeRefreshGameFragment.setRefreshing(false);

        });
    }

    private void recyclerViewInit(View v) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.gameRecyclerView.setLayoutManager(layoutManager);
        adapter = new GameAdapter(gameDetailsContactList, getContext());
        binding.gameRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


}
