package eventually.kotlin

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class EventuallyTest {
  @Test fun testAcceptsAnyLambda() {
    eventually { "100" }
    eventually { 5 }
    eventually { 5 }
    eventually { assertEquals(true, true) }
  }

  @Test fun testFailsAfterDefaultTimeout() {
    assertFails { eventually { throw Error("failed!") } }
  }

  @Test fun testFailsAfterGivenTimeout() {
    assertFails { eventually(100) { throw Error("failed!") } }
  }

  @Test fun testSucceedsAsync() {
    var someValue = false

    Thread(Runnable {
      Thread.sleep(1001)
      someValue = true
    }).run()

    eventually { assertEquals(someValue, true) }
  }

  @Test fun testAcceptsAnyTimeout() {
    var someValue = "it"
    val nextValue = "works"

    Thread(Runnable {
      Thread.sleep(1000)
      someValue = nextValue
    }).run()

    eventually(1001) { assertEquals(someValue, nextValue) }
  }

  @Test fun testTwoAsyncThings() {
    var someValue1 = false
    val nextValue1 = true
    var someValue2 = "bad"
    val nextValue2 = "good"

    Thread {
      Thread.sleep(2000)
      someValue1 = nextValue1
    }.start()

    Thread {
      Thread.sleep(1000)
      someValue2 = nextValue2
    }.start()

    eventually { assertEquals(someValue1, nextValue1) }
    assertEquals(someValue2, nextValue2)
  }
}
