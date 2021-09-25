package mx.dev.shell.android.outsideintddexample.unitTest

import junit.framework.TestCase.assertEquals
import mx.dev.shell.android.outsideintddexample.Car
import org.junit.Test

class CarShould {

    private val car = Car(5.0)

    @Test
    fun looseFuelWhenItTurnsOn() {
        car.turnOn()

        assertEquals(4.5, car.fuel)
    }
}