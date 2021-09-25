package mx.dev.shell.android.outsideintddexample.unitTest

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.outsideintddexample.Car
import mx.dev.shell.android.outsideintddexample.Engine
import mx.dev.shell.android.outsideintddexample.utils.MainCoroutineScopeRule
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class CarShould {

    private val engine = mock(Engine::class.java)

    private val car = Car(5.0, engine)

    @get: Rule
    var coroutineTestRule = MainCoroutineScopeRule()

    @Test
    fun looseFuelWhenItTurnsOn() = runBlockingTest {
        car.turnOn()

        assertEquals(4.5, car.fuel)
    }

    @Test
    fun turnsOnItsEngine() = runBlockingTest {
        car.turnOn()

        verify(engine, times(1)).turnOn()
    }
}