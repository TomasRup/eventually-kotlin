package eventually.kotlin

const val REPEAT_EVERY_MS: Long = 100

fun <T> eventually(timeoutMs: Long = 5000L, conditionFn: () -> T) {
  try {
    conditionFn()
  } catch (err: Error) {
    when {
      timeoutMs == 0L -> throw err
      timeoutMs > REPEAT_EVERY_MS -> {
        Thread.sleep(REPEAT_EVERY_MS)
        eventually(timeoutMs - REPEAT_EVERY_MS, conditionFn)
      }
      timeoutMs <= REPEAT_EVERY_MS -> {
        Thread.sleep(REPEAT_EVERY_MS)
        eventually(0, conditionFn)
      }
    }
  }
}
