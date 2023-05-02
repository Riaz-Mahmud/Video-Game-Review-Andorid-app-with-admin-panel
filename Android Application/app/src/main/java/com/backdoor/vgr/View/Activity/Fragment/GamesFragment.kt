package com.backdoor.vgr.View.Activity.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.backdoor.vgr.R
import com.backdoor.vgr.View.Model.Game.GameAdapter
import com.backdoor.vgr.View.Model.Game.GameDetailsContact
import com.backdoor.vgr.databinding.FragmentGamesBinding
import com.backdoor.vgr.viewmodel.GameViewModel

/**
 * A simple [Fragment] subclass.
 */
class GamesFragment : Fragment() {
    private lateinit var binding: FragmentGamesBinding
    private var adapter: GameAdapter? = null
    private var gameDetailsContactList: List<GameDetailsContact>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_games, container, false)
        val v = binding.root
        val viewModel = ViewModelProviders.of(this@GamesFragment)[GameViewModel::class.java]
        viewModel.setFragmentActivity(activity)
        binding.dashBoardViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.view = v
        viewModel.setUserInfo()
        recyclerViewInit()
        viewModel.getAllGameList()
        listChange(viewModel)
        binding.swipeRefreshGameFragment.setOnRefreshListener { viewModel.getAllGameList() }
        return v
    }

    private fun listChange(viewModel: GameViewModel) {
        viewModel.gameDetailsContactList.observe(viewLifecycleOwner) { gameDetailsContactList: List<GameDetailsContact>? ->
            this.gameDetailsContactList = gameDetailsContactList
            if (this.gameDetailsContactList!!.isNotEmpty()) {
                binding.noDataFoundGames.visibility = View.GONE
                binding.gameRecyclerView.visibility = View.VISIBLE
            } else {
                binding.noDataFoundGames.visibility = View.VISIBLE
                binding.gameRecyclerView.visibility = View.GONE
            }
            adapter!!.notifyChangeData(gameDetailsContactList)
            binding.swipeRefreshGameFragment.isRefreshing = false
        }
    }

    private fun recyclerViewInit() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.gameRecyclerView.layoutManager = layoutManager
        adapter = GameAdapter(gameDetailsContactList, requireContext())
        binding.gameRecyclerView.adapter = adapter
    }

}