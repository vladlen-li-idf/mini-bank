package kz.solva.bank.exception

class ReflectionException : RuntimeException {
  private constructor(message: String) : super(message)
  private constructor(message: String, cause: Throwable) : super(message, cause)

  companion object {
    fun fromMessage(message: String): ReflectionException {
      return ReflectionException(message)
    }

    fun fromMessageAndCause(message: String, cause: Throwable): ReflectionException {
      return ReflectionException(message, cause)
    }
  }
}
