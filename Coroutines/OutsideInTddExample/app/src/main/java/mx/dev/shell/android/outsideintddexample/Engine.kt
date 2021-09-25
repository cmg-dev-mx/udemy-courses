package mx.dev.shell.android.outsideintddexample

class Engine(
    var temp: Int = 15,
    var isTurnedOn: Boolean = false
) {

    fun turnOn() {
        isTurnedOn = true
        temp = 95
    }
}