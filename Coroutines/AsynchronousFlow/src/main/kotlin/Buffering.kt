import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val time = measureTimeMillis {
            generate()
                .buffer()
                .collect { value ->
                    delay(300)
                    println(value)
                }
        }

        println("Collected in $time ms")
    }
}

fun generate() = flow {
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}

