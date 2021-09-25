package mx.dev.shell.android.outsideintddexample.unitTest

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.outsideintddexample.Engine
import mx.dev.shell.android.outsideintddexample.utils.MainCoroutineScopeRule
import org.junit.Rule
import org.junit.Test

class EngineShould {

    private val engine = Engine()

    @get: Rule
    var coroutineTestRule = MainCoroutineScopeRule()

    @Test
    fun turnOn() = runBlockingTest {
        engine.turnOn()

        assertTrue(engine.isTurnedOn)
    }

    @Test
    fun riseTemperatureWhenItTurnsOn() = runBlockingTest {
        engine.turnOn()

        assertEquals(95, engine.temp)
    }
}