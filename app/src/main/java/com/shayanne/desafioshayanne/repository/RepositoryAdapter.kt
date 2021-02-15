package com.shayanne.desafioshayanne.repository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shayanne.desafioshayanne.R
import com.shayanne.desafioshayanne.model.RepositoryRequests
import com.shayanne.desafioshayanne.utils.loadImageUrl

class RepositoryAdapter(
    val listrep: MutableList<RepositoryRequests>,
    private val listener: RepositoryActivity
) :
    RecyclerView.Adapter<RepositoryAdapter.List1ViewHolder>() {

    // cria a view que precisa ser repetida
    // cria um objeto do item e nele infla os ids para mostrar na tela,
    // o context é onde vamos colocar(main activity)
    //  retorna a view que devemos criar e repetir
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): List1ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.scroll_rep,
            parent, false
        )

        return List1ViewHolder(itemView)
    }

    // repete a view varias vezes
    // conecta os ids dos itens a posicao que devem ser exibidos
    // por isso passe td na mesma ordem aqui
    override fun onBindViewHolder(holder: List1ViewHolder, position: Int) {
        val posicaoItem = listrep[position]
        holder.repository.text = posicaoItem.name
        holder.descriptionRep.text = posicaoItem.description

        holder.userRep.loadImageUrl(posicaoItem.owner.avatarUrl)

        holder.usernameRep.text = posicaoItem.owner.login
        holder.nameRep.text = posicaoItem.fullName
        holder.numberForks.text = posicaoItem.forksCount.toString()
        holder.numberStars.text = posicaoItem.stargazersCount.toString()
    }

    override fun getItemCount() = listrep.size

    fun addRepositories(items: List<RepositoryRequests>) {
        val previousCount = itemCount
        listrep.addAll(items)
        notifyItemRangeInserted(previousCount, items.size)
    }

    inner class List1ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val repository: TextView = itemView.findViewById(R.id.nome_repositorio)
        val descriptionRep: TextView = itemView.findViewById(R.id.descricao_rep)
        val userRep: ImageView = itemView.findViewById(R.id.user_rep)
        val usernameRep: TextView = itemView.findViewById(R.id.username_rep)
        val nameRep: TextView = itemView.findViewById(R.id.nome_completo_rep)
        val numberForks: TextView = itemView.findViewById(R.id.n_conexoes)
        val numberStars: TextView = itemView.findViewById(R.id.n_estrelas)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.createIntentClick(position)
            }
        }
    }

    interface ItemClickListener {
        fun createIntentClick(position: Int)
    }
}
