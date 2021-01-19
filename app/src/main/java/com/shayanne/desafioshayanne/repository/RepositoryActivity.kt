package com.shayanne.desafioshayanne.repository

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shayanne.desafioshayanne.api.InicializadorRepositories
import com.shayanne.desafioshayanne.pull.PullActivity
import com.shayanne.desafioshayanne.databinding.ActivityRepositoryBinding
import com.shayanne.desafioshayanne.viewmodel.RepositoryViewModel
import com.shayanne.desafioshayanne.viewmodel.RepositoryViewModelFactory
import com.shayanne.desafioshayanne.viewmodel.RepositoryViewState


class RepositoryActivity : AppCompatActivity(), RepositoryAdapter.ItemClickListener {


    private val repositoryViewModel: RepositoryViewModel by viewModels {
        RepositoryViewModelFactory(InicializadorRepositories.webClientGithub)
    }


    private lateinit var binding: ActivityRepositoryBinding
    val repositoryAdapter =
        RepositoryAdapter(
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



        binding.recyclerviewId.addOnScrollListener(object : EndlessRecyclerViewScrollListener(
            viewManager as LinearLayoutManager
        ) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                repositoryViewModel.loadpage(page)
            }
        })

        repositoryViewModel.loadpage(1)
    }

    private fun observeViewModel() {
        repositoryViewModel.getViewState().observe(
            this
            // screenState = it
        ) { screenState ->
            when (screenState) {

                is RepositoryViewState.Sucesso -> {
                    screenState.list /*tratar o estado de sucesso: adapter*/


                }
                is RepositoryViewState.Erro -> {
                    screenState.messageError /*tratar erro, mostrar msg*/

                }

            }

        }
    }

    /*private fun loadPage(page: Int) {
        Log.d("MainActivity-loadMore", "loading page $page")

        fun tryAgain() {
            AlertDialog.Builder(this@RepositoryActivity)
                .setMessage(R.string.error_network_request_failed)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    loadPage(page)
                }
                .show()
        }


        callGit.getRepositories(page).enqueue(object : Callback<ItemsRepositories> {
            override fun onResponse(
                call: Call<ItemsRepositories>,
                response: Response<ItemsRepositories>
            ) {
                if (response.isSuccessful) {
                    Log.d("MainActivity-loadMore", "isSucessfull, page : $page")
                    response.body()?.let {
                        repositoryAdapter.addRepositories(it.items)
                    }
                } else {
                    Log.d(
                        "MainActivity-loadMore",
                        "is NOT sucessful - ${response.code()} + ${response.errorBody()?.string()}"
                    )
                    tryAgain()
                }
            }

            override fun onFailure(call: Call<ItemsRepositories>, t: Throwable) {
                tryAgain()
                Toast.makeText(this@RepositoryActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }*/

    override fun CreateIntentClick(position: Int) {
        val intent = Intent(this, PullActivity::class.java)
        intent.putExtra(PullActivity.OWNER, repositoryAdapter.minhalista[position].owner.login)
        intent.putExtra(
            PullActivity.PICTURE,
            repositoryAdapter.minhalista[position].owner.avatarUrl
        )
        intent.putExtra(PullActivity.REPOSITORY, repositoryAdapter.minhalista[position].name)
        startActivity(intent)
    }


    /* override fun CreateIntentClick( item: RepositoryRequests) {
         val intent = Intent(this, PullActivity::class.java)
         intent.putExtra(PullActivity.OWNER, repositoryAdapter.minhalista.owner.login)
         intent.putExtra(
             PullActivity.PICTURE,
            repositoryAdapter.minhalista.owner.avatarUrl
         )
         intent.putExtra(PullActivity.REPOSITORY, repositoryAdapter.minhalista.name)
         startActivity(intent)
     }*/

}





