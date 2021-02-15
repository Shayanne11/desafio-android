package com.shayanne.desafioshayanne.pull

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shayanne.desafioshayanne.R
import com.shayanne.desafioshayanne.model.PullRequests
import com.shayanne.desafioshayanne.utils.loadImageUrl

class PullAdapter(

    // tornar private e criar uma val list para usar
    val listpull: MutableList<PullRequests>,

    private val callUrl: ItemClickListener
) :
    RecyclerView.Adapter<PullAdapter.List2ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): List2ViewHolder {
        val itemView2 = LayoutInflater.from(parent.context).inflate(
            R.layout.scroll_pull,
            parent, false
        )

        return List2ViewHolder(itemView2)
    }

    override fun onBindViewHolder(holder: List2ViewHolder, position: Int) {
        val posicaoItem = listpull[position]
        holder.titlePull.text = posicaoItem.title
        holder.descriptionPull.text = posicaoItem.body
        holder.userPull.loadImageUrl(posicaoItem.user.avatarUrl)
        holder.usernamePull.text = posicaoItem.user.login
        holder.namePull.text = posicaoItem.fullName
        holder.itemView.setOnClickListener { callUrl.createIntentClickPullUrl(posicaoItem) }
    }

    override fun getItemCount() = listpull.size

    fun addRepositories(items: List<PullRequests>) {
        val previousCount = itemCount
        listpull.addAll(items)
        notifyItemRangeInserted(previousCount, items.size)
    }

    inner class List2ViewHolder(itemView2: View) : RecyclerView.ViewHolder(itemView2) {
        val titlePull: TextView = itemView2.findViewById(R.id.titulo_pull)
        val descriptionPull: TextView = itemView2.findViewById(R.id.descricao_pull)
        val userPull: ImageView = itemView2.findViewById(R.id.user_pull)
        val usernamePull: TextView = itemView2.findViewById(R.id.username_pull)
        val namePull: TextView = itemView2.findViewById(R.id.nome_completo_pull)
    }

    interface ItemClickListener {
        fun createIntentClickPullUrl(item: PullRequests/*position: Int*/)
    }
}
