package com.badmitry.bkshackaton

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.badmitry.bkshackaton.databinding.ActivityMainBinding
import com.badmitry.bkshackaton.navigation.Screens
import com.badmitry.bkshackaton.view.OnErrorView
import com.badmitry.bkshackaton.view.OnProgressView
import com.badmitry.bkshackaton.viewmodels.MainViewModel
import com.badmitry.domain.entities.EventArgs
import dagger.android.support.DaggerAppCompatActivity
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), OnErrorView, OnProgressView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViewModel()
        setNavigator()
        if (supportFragmentManager.findFragmentById(binding.container.id) == null) {
            viewModel.replaceFragment(Screens.OPTIONS)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, vmFactory)[MainViewModel::class.java]
        viewModel.observe(this, ::onProgress, ::onError)
    }

    fun initToolbar(idTitle: Int, onBackBtnVisible: Boolean) {
        setSupportActionBar(binding.toolbar.materialToolbar)
        binding.toolbar.materialToolbar.title = getString(idTitle)
        supportActionBar?.apply {
            setHomeButtonEnabled(onBackBtnVisible)
            setDisplayHomeAsUpEnabled(onBackBtnVisible)
        }
    }

    private fun setNavigator() {
        val navigator = SupportAppNavigator(this, supportFragmentManager, binding.container.id)
        viewModel.setNavigator(navigator)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onProgress(arg: EventArgs<Boolean>) {
        if (arg.args) {
            binding.pbLoadingLayout.visibility = View.VISIBLE
        } else {
            binding.pbLoadingLayout.visibility = View.GONE
        }
    }

    override fun onError(arg: EventArgs<Throwable>) {
        Toast.makeText(this, arg.args.message, Toast.LENGTH_LONG).show()
    }
}