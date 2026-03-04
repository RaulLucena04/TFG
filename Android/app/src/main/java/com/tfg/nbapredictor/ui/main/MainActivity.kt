package com.tfg.nbapredictor.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tfg.nbapredictor.R
import com.tfg.nbapredictor.databinding.ActivityMainBinding
import com.tfg.nbapredictor.ui.admin.AdminActivity
import com.tfg.nbapredictor.ui.auth.LoginActivity
import com.tfg.nbapredictor.ui.bets.BetsFragment
import com.tfg.nbapredictor.ui.dashboard.DashboardFragment
import com.tfg.nbapredictor.ui.matches.MatchesFragment
import com.tfg.nbapredictor.ui.profile.ProfileFragment
import com.tfg.nbapredictor.ui.rankings.RankingsFragment
import com.tfg.nbapredictor.util.Session

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!Session.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setupBottomNavigation()
        loadFragment(DashboardFragment())
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    loadFragment(DashboardFragment())
                    true
                }
                R.id.nav_matches -> {
                    loadFragment(MatchesFragment())
                    true
                }
                R.id.nav_bets -> {
                    loadFragment(BetsFragment())
                    true
                }
                R.id.nav_rankings -> {
                    loadFragment(RankingsFragment())
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (Session.isAdmin()) {
            menuInflater.inflate(R.menu.admin_menu, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_admin -> {
                startActivity(Intent(this, AdminActivity::class.java))
                true
            }
            R.id.menu_logout -> {
                Session.clearSession()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
