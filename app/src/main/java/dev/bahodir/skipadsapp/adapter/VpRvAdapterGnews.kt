package dev.bahodir.skipadsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.bahodir.skipadsapp.databinding.RvItemBinding
import dev.bahodir.skipadsapp.news.Article

class VpRvAdapterGnews(var listener: OnItem) : ListAdapter<Article, VpRvAdapterGnews.VH>(DU()) {

    class DU : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.source.name == newItem.source.name
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    interface OnItem {
        fun onTouchListener(article: Article, position: Int, view: View)
    }

    inner class VH(var binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article, position: Int) {
            Picasso.get().load(article.urlToImage ).into(binding.actionImage)
            binding.title.text = article.title
            binding.name.text = article.source.name
            binding.date.text = article.publishedAt.split("T")[0]

            itemView.setOnClickListener {
                listener.onTouchListener(article, position, it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position), position)
    }
}