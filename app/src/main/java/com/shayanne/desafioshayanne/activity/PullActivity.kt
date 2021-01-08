package com.shayanne.desafioshayanne.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shayanne.desafioshayanne.R
import com.shayanne.desafioshayanne.adapter.PullAdapter
import com.shayanne.desafioshayanne.databinding.ToolbarDetRepositorioBinding
import com.shayanne.desafioshayanne.modelo.PullRequests
import com.shayanne.desafioshayanne.webservices.InicializadorRepositories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PullActivity : AppCompatActivity(),PullAdapter.ItemClickListener {

    var repositorio = ""
    var owner = ""
    var nome = ""
    var picture = ""
    private val listurl = ArrayList<PullRequests>()


    private val callGit by lazy { InicializadorRepositories.initRep() }
    private lateinit var bindingpull: ToolbarDetRepositorioBinding
    private lateinit var idDoRecycleViewPull: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingpull = ToolbarDetRepositorioBinding.inflate(layoutInflater)
        setContentView(bindingpull.root)

        viewManager = LinearLayoutManager(this)

        idDoRecycleViewPull = findViewById<RecyclerView>(R.id.recyclerview_id_pull).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            layoutManager = viewManager
        }


        repositorio = intent.getStringExtra(PullActivity.repositorio).toString()
        owner = intent.getStringExtra(PullActivity.owner).toString()
        nome = intent.getStringExtra(PullActivity.nome).toString()
        picture = intent.getStringExtra(PullActivity.picture).toString()




        //BOTAO DE RETORNAR
        setSupportActionBar(findViewById(R.id.toolbar2))
        supportActionBar!!.title = repositorio
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)



        callGit.getPullRequests(owner, repositorio).enqueue(object : Callback<List<PullRequests>> {

            override fun onFailure(call: Call<List<PullRequests>>, t: Throwable) {
                Log.d("Erro", t.message.toString())
                Toast.makeText(this@PullActivity, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<PullRequests>>,
                response: Response<List<PullRequests>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        bindingpull.recyclerviewIdPull.adapter =
                            PullAdapter(listurl, this@PullActivity)
                        listurl.addAll(it)
                    }
                }
            }
        })
    }


    companion object {
        const val repositorio = "repositorio"
        const val owner = "owner"
        const val nome = "nome"
        const val picture = "picture"
    }



    override fun CreateIntentClickPullUrl(position: Int) {
        val url = listurl[position].urlpull
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

}




