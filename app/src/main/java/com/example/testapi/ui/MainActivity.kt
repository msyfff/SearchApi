package com.example.testapi.ui

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapi.R
import com.example.testapi.data.response.ItemsItem
import com.example.testapi.data.viewmodel.MainViewModel
import com.example.testapi.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "GitHub User"

        subscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_bar).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.kata_kunci_user)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.findApi(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }


    private fun setApiData(listApi: List<ItemsItem>) {
        val adapter = ApiAdapter()
        adapter.submitList(listApi)
        binding.rvHeroes.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.memuat.visibility = View.VISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.memuat.visibility = View.GONE
        }
    }

    private fun subscribe() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvHeroes.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvHeroes.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.setApiData.observe(this) {
            setApiData(it)
        }
        mainViewModel.snackbarText.observe(this) { it ->
            it.getContentifNotHandled()?.let {
                Snackbar.make(
                    window.decorView.rootView,
                    "Ditemukan $it Pengguna",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        mainViewModel.showDefault.observe(this) {
            binding.searchUser.isVisible = it
        }

        mainViewModel.showError.observe(this) {
            binding.tvErrorMessage.isVisible = it
        }
        mainViewModel.showFailed.observe(this) {
            binding.tvFailedMessage.isVisible = it
        }
    }
}