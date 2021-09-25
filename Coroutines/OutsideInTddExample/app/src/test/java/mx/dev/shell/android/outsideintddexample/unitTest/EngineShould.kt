package mx.dev.shell.android.outsideintddexample.unitTest

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import mx.dev.shell.android.outsideintddexample.Engine
import org.junit.Test

class EngineShould {

    private val engine = Engine()

    @Test
    fun turnOn() {
        engine.turnOn()

        assertTrue(engine.isTurnedOn)
    }

    @Test
    fun riseTemperatureWhenItTurnsOn() {
        engine.turnOn()

        assertEquals(95, engine.temp)
    }
}