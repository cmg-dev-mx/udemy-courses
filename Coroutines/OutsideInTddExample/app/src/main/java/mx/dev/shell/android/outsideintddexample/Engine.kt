package mx.dev.shell.android.outsideintddexample

import android.util.Log
import kotlinx.coroutines.delay

class Engine(
    var temp: Int = 15,
    var isTurnedOn: Boolean = false
) {

    suspend fun turnOn() {
        isTurnedOn = true

        delay(6000)

        temp = 95

        Log.d("Engine", "Engine has turned on")
    }
}