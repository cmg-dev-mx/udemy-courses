import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val channel = produce {
            for (x in 1..5) {
                send(x * x)
            }
        }

        for (i in channel) {
            println(i)
        }

        // Simplificado
        for (j in produceSquares()) {
            println(j)
        }

        // Más simplificado
        produceSquares().consumeEach {
            println(it)
        }
    }
}

fun CoroutineScope.produceSquares() = produce {
    for (x in 1..5) {
        send(x * x)
    }
}