package mx.dev.shell.android.androidcoroutinesflow.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import mx.dev.shell.android.androidcoroutinesflow.model.NewsRepository

class ArticlesViewModel(application: Application) : AndroidViewModel(application) {

    val articles = NewsRepository().getNewsArticles()
        .asLiveData()
}