package com.backdoor.vgr.View.Activity.Fragment;

import static com.backdoor.vgr.View.Activity.MainActivity.perfConfig;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Activity.MainActivity;
import com.backdoor.vgr.databinding.FragmentProfileBinding;
import com.backdoor.vgr.viewmodel.ProfilePageViewModel;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_Fragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.backdoor.vgr.databinding.FragmentProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        View v = binding.getRoot();
        ProfilePageViewModel viewModel = ViewModelProviders.of(Profile_Fragment.this).get(ProfilePageViewModel.class);
        viewModel.setActivity(getActivity());
        viewModel.setView(v);
        binding.setProfilePageViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.setUserInfo();

        return v;
    }

}
