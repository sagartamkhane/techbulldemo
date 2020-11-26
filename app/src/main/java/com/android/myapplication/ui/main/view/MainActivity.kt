package com.android.myapplication.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.myapplication.R
import com.android.myapplication.data.api.ApiHelper
import com.android.myapplication.data.api.RetrofitBuilder
import com.android.myapplication.data.model.Movie
import com.android.myapplication.ui.base.ViewModelFactory
import com.android.myapplication.ui.main.adapter.MainAdapter
import com.android.myapplication.ui.main.viewmodel.MainViewModel
import com.android.myapplication.utils.Status.ERROR
import com.android.myapplication.utils.Status.LOADING
import com.android.myapplication.utils.Status.SUCCESS
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupUI()
        setupObservers("default","6d7f3aa8")
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu.findItem(R.id.search_bar)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                setupObservers(query,"6d7f3aa8")
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
               Log.d("Tag",newText);
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObservers(s:String,apiKey:String) {
        viewModel.getMovie(s,apiKey).observe(this, Observer {
            it?.let { resource ->
               when (resource.status) {
                    SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { movie -> retrieveList(movie) }
                    }
                    ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(movie: Movie) {
        adapter.apply {
            addUsers(movie.search)
            notifyDataSetChanged()
        }
    }
}
