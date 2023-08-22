package kz.solva.bank.uti

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object ReflectionUtils {
  fun getTypeFromClassGeneric(position: Int, clazz: Class<*>): Type {
    return (clazz
      .genericSuperclass as ParameterizedType).actualTypeArguments[position]
  }
}
