import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
//        sendNumbers().collect {
//            println("Received $it ")
//        }
//        sendNumbers2().collect {
//            println("Received $it ")
//        }
        sendNumbersAsWords().collect {
            println("Received $it ")
        }
    }
}

fun sendNumbers() = flow {
    for (i in 1..10) {
        emit(i)
    }
}

fun sendNumbers2() = listOf(1, 2, 3)
    .asFlow()

fun sendNumbersAsWords() = flowOf("One", "Two", "Three")