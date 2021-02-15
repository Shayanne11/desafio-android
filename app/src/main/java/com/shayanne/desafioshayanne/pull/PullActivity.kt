package com.shayanne.desafioshayanne.pull

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels

import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shayanne.desafioshayanne.R
import com.shayanne.desafioshayanne.api.InicializadorApi
import com.shayanne.desafioshayanne.databinding.ActivityPullBinding
import com.shayanne.desafioshayanne.model.PullRequests
import com.shayanne.desafioshayanne.viewmodel.viewmodelpull.PullViewModel
import com.shayanne.desafioshayanne.viewmodel.viewmodelpull.PullViewModelFactory
import com.shayanne.desafioshayanne.viewmodel.viewmodelpull.PullViewState

class PullActivity : AppCompatActivity(), PullAdapter.ItemClickListener {

    private val pullViewModel: PullViewModel by viewModels {
        PullViewModelFactory(InicializadorApi.webClientGithub)
    }
    val list = ArrayList<PullRequests>()
    val pullAdapter = PullAdapter(list, this)
    var owner = ""
    var repository = ""
    private lateinit var bindingpull: ActivityPullBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingpull = ActivityPullBinding.inflate(layoutInflater)
        setContentView(bindingpull.root)

        lateinit var viewManager: RecyclerView.LayoutManager

        viewManager = LinearLayoutManager(this)

        bindingpull.recyclerviewIdPull.layoutManager = viewManager
        bindingpull.recyclerviewIdPull.setHasFixedSize(true)
        bindingpull.recyclerviewIdPull.adapter = pullAdapter

        // intent pra chamar a pagina do pull
        owner = intent.getStringExtra(OWNER).toString()
        repository = intent.getStringExtra(REPOSITORY).toString()

        // BOTAO DE RETORNAR
        setSupportActionBar(findViewById(R.id.toolbar2))
        supportActionBar!!.title = repository
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // chamando o callGit do pull
        pullViewModel.loadurl(owner, repository)

        observeViewModel()
    }

    private fun observeViewModel() {
        pullViewModel.getViewState().observe(
            this
        ) {
            when (it) {
                is PullViewState.Sucesso -> {

                    pullAdapter.addRepositories(it.list)
                }

                is PullViewState.Erro -> {
                    showError(it.messageError)
                }
            }
        }
    }

    private fun showError(@StringRes errorRes: Int) {
        AlertDialog.Builder(this)
            .setMessage(errorRes)
            .show()
    }

    companion object {
        const val REPOSITORY = "repositorio"
        const val OWNER = "owner"
        const val PICTURE = "picture"
    }

    override fun createIntentClickPullUrl(item: PullRequests) {
        val url = item.htmlUrl
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
