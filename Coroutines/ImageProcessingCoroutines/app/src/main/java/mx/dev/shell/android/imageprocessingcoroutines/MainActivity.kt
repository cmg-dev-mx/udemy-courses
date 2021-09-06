package mx.dev.shell.android.imageprocessingcoroutines

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.URL

class MainActivity : AppCompatActivity() {

    companion object {
        private const val IMAGE_URL =
            "https://raw.githubusercontent.com/DevTides/JetpackDogsApp/master/app/src/main/res/drawable/dog.png"
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        imageView = findViewById(R.id.imageView)

        coroutineScope.launch {
            val originalDeferred = coroutineScope.async(Dispatchers.IO) {
                getOriginalBitmap()
            }

            val originalBitmap = originalDeferred.await()

            val filterDeferred = coroutineScope.async(Dispatchers.Default) {
                applyFilter(originalBitmap)
            }

            val filterBitmap = filterDeferred.await()

            loadImage(filterBitmap)
        }
    }

    private fun getOriginalBitmap() = URL(IMAGE_URL)
        .openStream()
        .use {
            BitmapFactory.decodeStream(it)
        }

    private fun applyFilter(originalBitmap: Bitmap) = Filter.apply(originalBitmap)

    private fun loadImage(bmp: Bitmap) {
        progressBar.visibility = View.GONE
        imageView.setImageBitmap(bmp)
        imageView.visibility = View.VISIBLE
    }
}