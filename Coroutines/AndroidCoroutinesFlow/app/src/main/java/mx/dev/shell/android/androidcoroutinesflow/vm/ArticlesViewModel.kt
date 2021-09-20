package mx.dev.shell.android.androidcoroutinesflow.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class ArticlesViewModel(application: Application) : AndroidViewModel(application) {

    val articles = MutableLiveData<List<String>>()
}