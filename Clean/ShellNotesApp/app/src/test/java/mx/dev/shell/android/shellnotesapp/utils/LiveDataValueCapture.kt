package mx.dev.shell.android.shellnotesapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class LiveDataValueCapture<T> {

    private val lock = Any()
    private val _values = mutableListOf<T?>()

    val values: List<T?>
        get() = synchronized(lock) {
            _values.toList()
        }

    fun addValue(value: T?) = synchronized(lock) {
        _values += value
    }
}

inline fun <T> LiveData<T>.captureValues(block: LiveDataValueCapture<T>.() -> Unit) {
    val capture = LiveDataValueCapture<T>()
    val observer = Observer<T> {
        capture.addValue(it)
    }

    observeForever(observer)

    try {
        capture.block()
    } finally {
        removeObserver(observer)
    }
}

fun <T> LiveData<T>.getValueForTest(): T? {
    var value: T? = null
    val observer = Observer<T> {
        value = it
    }

    observeForever(observer)
    removeObserver(observer)
    return value
}