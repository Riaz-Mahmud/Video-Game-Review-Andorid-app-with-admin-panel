package com.backdoor.vgr.View.Activity.Fragment;

import static com.backdoor.vgr.View.Activity.MainActivity.perfConfig;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Model.Game.GameAdapter;
import com.backdoor.vgr.View.Model.Game.GameContact;
import com.backdoor.vgr.View.Model.Game.GameDetailsContact;
import com.backdoor.vgr.databinding.FragmentGamesBinding;
import com.backdoor.vgr.network.ApiClient;
import com.backdoor.vgr.network.ApiInterface;
import com.backdoor.vgr.viewmodel.DashboardViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Games_Fragment extends Fragment {

    Animation fromBottom;

    //Binding
    private FragmentGamesBinding binding;
    private View v;

    private RecyclerView recyclerView;
    private GameAdapter adapter;
    private List<GameDetailsContact> gameDetailsContactList;
    private boolean isGameLoad;
    private ImageView noDataFound;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_games, container, false);
        v = binding.getRoot();
        DashboardViewModel viewModel = ViewModelProviders.of(Games_Fragment.this).get(DashboardViewModel.class);
        viewModel.setActivity(getActivity());
        binding.setDashBoardViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.setUserInfo();

        fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.from_bottom);
        noDataFound = v.findViewById(R.id.noDataFound);
        refreshLayout = v.findViewById(R.id.swipeRefreshGameFragment);

        recyclerViewInit(v);

        refresh();

        return v;
    }

    private void refresh() {
        refreshLayout.setOnRefreshListener(() -> {
            getData();
            refreshLayout.setRefreshing(false);
        });
    }

    private void recyclerViewInit(View v) {
        recyclerView = v.findViewById(R.id.gameRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GameAdapter(gameDetailsContactList, getContext());
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        if (checkConnection()) {

            perfConfig.LoadingBar(v.getContext(), "Please wait");

            ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
            Call<GameContact> call = apiService.getUserHistory();

            call.enqueue(new Callback<GameContact>() {
                @Override
                public void onResponse(Call<GameContact> call, Response<GameContact> response) {
                    perfConfig.loadingBar.cancel();
                    isGameLoad = true;
                    noDataFound.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    if (response.isSuccessful() && response.body().isSuccess()) {
                        gameDetailsContactList = response.body().getGameDetailsContactList();
                        adapter.notifyChangeData(gameDetailsContactList);
                    } else {
                        noDataFound.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<GameContact> call, Throwable t) {
                    isGameLoad = false;
                    perfConfig.loadingBar.cancel();
                    call.cancel();
                    perfConfig.displayToast("Reach Max Time! Try again!");
                }
            });
        }
    }

    public boolean checkConnection() {
        if (getActivity() != null) {
            ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert manager != null;
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            if (activeNetwork == null) {
                perfConfig.displaySnackBar(v, "No Internet Connection");
                return false;

            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isGameLoad) {
            recyclerView.setAdapter(adapter);
        } else {
            getData();
        }
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
