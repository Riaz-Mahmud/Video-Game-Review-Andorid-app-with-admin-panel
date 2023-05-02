package com.backdoor.vgr.View.Activity.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.backdoor.vgr.R
import com.backdoor.vgr.databinding.FragmentProfileBinding
import com.backdoor.vgr.viewmodel.ProfilePageViewModel

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )
        val v = binding.root
        val viewModel =
            ViewModelProviders.of(this@ProfileFragment)[ProfilePageViewModel::class.java]
        viewModel.setActivity(activity)
        viewModel.setView(v)
        binding.profilePageViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.setUserInfo()
        return v
    }
}