package mx.dev.shell.android.outsideintddexample

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Engine(
    var temp: Int = 15,
    var isTurnedOn: Boolean = false
) {

    suspend fun turnOn(): Flow<Int> {
        isTurnedOn = true

        return flow {
            delay(2000)
            temp = 25
            emit(temp)

            delay(2000)
            temp = 50
            emit(temp)

            delay(2000)
            temp = 95
            emit(temp)

            Log.d("Engine", "Engine has turned on")
        }
    }
}