package com.siskakhnnisa.userapp.ui.third

import android.os.Bundle
import android.view.View
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.siskakhnnisa.userapp.ViewModelFactory
import com.siskakhnnisa.userapp.Result
import com.siskakhnnisa.userapp.model.DataItem
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.siskakhnnisa.userapp.adapter.LoadingAdapter
import com.siskakhnnisa.userapp.adapter.ListUserAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.siskakhnnisa.userapp.R
import com.siskakhnnisa.userapp.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private lateinit var viewModel: ThirdViewModel
    private var selectedUser: DataItem? = null
    private var currentPage = 1
    private val perPage = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val factory: ViewModelFactory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[ThirdViewModel::class.java]
        setToolBar()
        setupUsersList()
        binding.ivBack.setOnClickListener {
            finish()
        }

    }

    private fun setToolBar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.show()
        supportActionBar?.title = ""
    }

    private fun setupUsersList(){
        val userAdapter = ListUserAdapter()
        val layout = LinearLayoutManager(this)
        binding.rvUser.apply {
            layoutManager = layout
            setHasFixedSize(true)
            adapter = userAdapter
            adapter = userAdapter.withLoadStateFooter(
                footer = LoadingAdapter {
                    userAdapter.retry()
                }
            )
        }

        viewModel.getUsers(currentPage, perPage).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressbar.visibility = View.GONE
                    result.data.observe(this) { pagingData ->
                        userAdapter.submitData(lifecycle, pagingData)
                    }
                }
                is Result.Error -> {
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.swipeFresh.setOnRefreshListener {
            viewModel.getUsers(currentPage, perPage).observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.swipeFresh.isRefreshing = true
                    }
                    is Result.Success -> {
                        binding.swipeFresh.isRefreshing = false
                        result.data.observe(this) { pagingData ->
                            userAdapter.submitData(lifecycle, pagingData)
                        }
                    }
                    is Result.Error -> {
                        binding.swipeFresh.isRefreshing = false
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        userAdapter.onClick = {
            selectedUser = it
            val intent = Intent()
            intent.putExtra("selected_user", selectedUser)
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}