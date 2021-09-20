import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

fun main() {
    runBlocking {
//        val numbersFlow = sendNumbers()
//        println("Flow hasn't started yet")
//        println("Starting flow now:")
//        numbersFlow.collect { println(it) }

        val numbersFlow = sendNumbersWithDelay()
        println("Flow hasn't started yet")
        println("Starting flow now:")
        withTimeoutOrNull(1000) {
            numbersFlow.collect { println(it) }
        }
    }
}

private fun sendNumbers(): Flow<Int> = flow {
    val list = listOf(1, 2, 3)
    list.forEach { emit(it) }
}

private fun sendNumbersWithDelay(): Flow<Int> = flow {
    val list = listOf(1, 2, 3)
    list.forEach {
        delay(400)
        emit(it)
    }
}