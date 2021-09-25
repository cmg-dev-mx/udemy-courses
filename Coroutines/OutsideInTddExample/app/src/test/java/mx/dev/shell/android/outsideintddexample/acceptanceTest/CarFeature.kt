package mx.dev.shell.android.outsideintddexample.acceptanceTest

import junit.framework.Assert.assertEquals
import org.junit.Test

class CarFeature {

    val car = Car(6.0)

    @Test
    fun carIsLoosingFuelWhenItTurnsOn() {
        car.turOn()

        assertEquals(5.5, car.fuel)
    }
}