package mx.dev.shell.android.androidcoroutinesflow.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.dev.shell.android.androidcoroutinesflow.databinding.ItemArticleBinding
import mx.dev.shell.android.androidcoroutinesflow.loadImage
import mx.dev.shell.android.androidcoroutinesflow.model.NewsArticle

class ArticlesAdapter(private val articles: ArrayList<NewsArticle>): RecyclerView.Adapter<ArticlesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticlesViewHolder(
        ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) =
        holder.bind(articles[position])

    override fun getItemCount() = articles.size

    fun onAddNewsItem(article: NewsArticle) {
        articles.add(0, article)
        notifyItemInserted(0)
    }
}

class ArticlesViewHolder(private val binding: ItemArticleBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(article: NewsArticle) {
        binding.apply {
            this.articleImage.loadImage(article.urlToImage)
            this.articleTitle.text = article.title
            this.articleDescription.text = article.description
            this.articleDate.text = article.publishedAt
        }
    }
}
