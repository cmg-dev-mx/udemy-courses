package mx.dev.shell.android.outsideintddexample.acceptanceTest

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import mx.dev.shell.android.outsideintddexample.Car
import mx.dev.shell.android.outsideintddexample.Engine
import mx.dev.shell.android.outsideintddexample.utils.MainCoroutineScopeRule
import org.junit.Rule
import org.junit.Test

class CarFeature {

    private val engine = Engine()
    private val car = Car(6.0, engine)

    @get: Rule
    var coroutineTestRule = MainCoroutineScopeRule()

    @Test
    fun carIsLoosingFuelWhenItTurnsOn() {
        car.turnOn()

        assertEquals(5.5, car.fuel)
    }

    @Test
    fun carIsTurningOnItsEngineAndIncreasesTheTemperatureGradually() {
        car.turnOn()

        coroutineTestRule.advanceTimeBy(2001)
        assertEquals(25, car.engine.temp)

        coroutineTestRule.advanceTimeBy(2001)
        assertEquals(50, car.engine.temp)

        coroutineTestRule.advanceTimeBy(2001)
        assertEquals(95, car.engine.temp)

        assertTrue(car.engine.isTurnedOn)
    }
}