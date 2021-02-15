package com.shayanne.desafioshayanne.repository

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shayanne.desafioshayanne.api.InicializerApi
import com.shayanne.desafioshayanne.pull.PullActivity
import com.shayanne.desafioshayanne.databinding.ActivityRepositoryBinding
import com.shayanne.desafioshayanne.viewmodel.RepositoryViewModel
import com.shayanne.desafioshayanne.viewmodel.RepositoryViewModelFactory
import com.shayanne.desafioshayanne.viewmodel.RepositoryViewState

class RepositoryActivity : AppCompatActivity(), RepositoryAdapter.ItemClickListener {

    private val repositoryViewModel: RepositoryViewModel by viewModels {
        RepositoryViewModelFactory(InicializerApi.webClientGithub)
    }

    private lateinit var binding: ActivityRepositoryBinding

    val repositoryAdapter = RepositoryAdapter(
        ArrayList(),
        this
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lateinit var viewManager: RecyclerView.LayoutManager

        viewManager = LinearLayoutManager(this)

        binding.recyclerviewId.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = repositoryAdapter
        }

        observeViewModel()

        binding.recyclerviewId.addOnScrollListener(
            object : EndlessRecyclerViewScrollListener
                (viewManager as LinearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    binding.progressBar.visibility = View.VISIBLE
                    repositoryViewModel.loadpage(page)
                }
            })

        repositoryViewModel.loadpage(1)
    } // fim do OnCreate

    /*private*/ fun observeViewModel() {
        repositoryViewModel.getViewState().observe(
            this
            // screenState = it
        ) { screenState ->
            when (screenState) {

                is RepositoryViewState.Sucesso -> {
                    screenState.list

                    repositoryAdapter.addRepositories(screenState.list)
                }
                is RepositoryViewState.Erro -> {
                    showError(screenState.messageError)
                }
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showError(@StringRes errorRes: Int) {
        AlertDialog.Builder(this)
            .setMessage(errorRes)
            .show()
    }

    override fun createIntentClick(position: Int) {
        val intent = Intent(this, PullActivity::class.java)
        intent.putExtra(PullActivity.OWNER, repositoryAdapter.listrep[position].owner.login)
        intent.putExtra(
            PullActivity.PICTURE,
            repositoryAdapter.listrep[position].owner.avatarUrl
        )
        intent.putExtra(PullActivity.REPOSITORY, repositoryAdapter.listrep[position].name)
        startActivity(intent)
    }
}
