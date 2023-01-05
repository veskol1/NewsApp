package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.newsapp.databinding.ListItemArticleBinding
import com.example.newsapp.model.Article

class ArticlesAdapter(private val articles: ArrayList<Article>):
    RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder>() {

    inner class ArticlesViewHolder(val binding: ListItemArticleBinding): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val binding = ListItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticlesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        with(holder) {
            with(articles[position]) {
                binding.apply {
                    newspaperTitle.text = title
                    newspaperName.text = name
                    newspaperDesc.text = desc
                    newspaperDate.text = date

                    Glide.with(holder.itemView.context)
                        .load(image)
                        .transform(CenterCrop(), RoundedCorners(16))
                        .into(articleImage)
                }
            }
        }
    }

    override fun getItemCount(): Int = articles.size

}