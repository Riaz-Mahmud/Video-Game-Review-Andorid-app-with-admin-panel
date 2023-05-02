package com.backdoor.vgr.View.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.backdoor.vgr.R
import com.backdoor.vgr.View.Activity.Fragment.GamesFragment
import com.backdoor.vgr.View.Activity.Fragment.ProfileFragment
import com.backdoor.vgr.View.Model.PerfConfig
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private var navigationView: BottomNavigationView? = null
    private var id = 0
    private var dashBoardFragment: GamesFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        perfConfig = PerfConfig(this)
        navigationView = findViewById(R.id.bottomNavigationView)
        dashBoardFragment =
            GamesFragment()
        val profileFragment = ProfileFragment()
        navigationView!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem: MenuItem ->
            id = menuItem.itemId
            if (id == R.id.nav_games) {
                setFragment(dashBoardFragment)
                return@OnNavigationItemSelectedListener true
            } else if (id == R.id.nav_profile) {
                setFragment(profileFragment)
                return@OnNavigationItemSelectedListener true
            }
            false
        })
        navigationView!!.selectedItemId = R.id.nav_games
    }

    private fun setFragment(fragment: Fragment?) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.adminFrame, fragment!!)
        fragmentTransaction.commit()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (id != R.id.nav_games) {
            navigationView!!.selectedItemId = R.id.nav_games
            setFragment(dashBoardFragment)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var perfConfig: PerfConfig

        @JvmField
        var IMAGE_LINK = "imageLnk"

        @JvmField
        var IMAGE_PATH = "image_path"

        @JvmField
        var GAME_ID = "gameId"
    }
}
