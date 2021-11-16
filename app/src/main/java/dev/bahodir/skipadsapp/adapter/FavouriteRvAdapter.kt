package dev.bahodir.skipadsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.bahodir.skipadsapp.databinding.FavouriteItemBinding
import dev.bahodir.skipadsapp.room.User

class FavouriteRvAdapter : ListAdapter<User, FavouriteRvAdapter.VH>(DU()) {

    class DU : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }

    inner class VH(var binding: FavouriteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            Picasso.get().load(user.url).into(binding.actionImage)
            binding.author.text = user.author
            binding.date.text = user.published
            binding.description.text = user.description
            binding.title.text = user.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(FavouriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}