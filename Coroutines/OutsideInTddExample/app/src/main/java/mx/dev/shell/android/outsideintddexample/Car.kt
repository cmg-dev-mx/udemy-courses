package mx.dev.shell.android.outsideintddexample

class Car(var fuel: Double, val engine: Engine) {

    fun turnOn() {
        fuel -= 0.5
        engine.turnOn()
    }
}