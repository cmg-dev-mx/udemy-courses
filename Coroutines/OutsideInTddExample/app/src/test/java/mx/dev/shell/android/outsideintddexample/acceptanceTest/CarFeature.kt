package mx.dev.shell.android.outsideintddexample.acceptanceTest

import junit.framework.TestCase.assertEquals
import mx.dev.shell.android.outsideintddexample.Car
import org.junit.Test

class CarFeature {

    private val car = Car(6.0)

    @Test
    fun carIsLoosingFuelWhenItTurnsOn() {
        car.turnOn()

        assertEquals(5.5, car.fuel)
    }
}