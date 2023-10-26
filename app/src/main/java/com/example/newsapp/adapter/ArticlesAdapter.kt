package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.newsapp.databinding.ListItemArticleBinding
import com.example.newsapp.model.Article

class ArticlesAdapter( val listener: ArticleClickListener) : ListAdapter<Article, ArticlesAdapter.ArticlesViewHolder>(DiffCallback()) {

    inner class ArticlesViewHolder(val binding: ListItemArticleBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onArticlesClicked(position = adapterPosition)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val binding = ListItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticlesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val item = getItem(position)

                holder.binding.apply {
                    newspaperTitle.text = item.title
                    newspaperName.text = item.name
                    newspaperDesc.text = item.desc
                    newspaperDate.text = item.date

                    Glide.with(holder.itemView.context)
                        .load(item.image)
                        .transform(CenterCrop(), RoundedCorners(16))
                        .into(articleImage)
                }
    }

    interface ArticleClickListener {
        fun onArticlesClicked(position: Int)
    }
}