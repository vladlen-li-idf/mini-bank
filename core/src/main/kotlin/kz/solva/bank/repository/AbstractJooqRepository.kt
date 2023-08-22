package kz.solva.bank.repository

import kz.solva.bank.exception.ReflectionException
import kz.solva.bank.uti.ReflectionUtils.getTypeFromClassGeneric
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Table
import reactor.core.publisher.Flux

abstract class AbstractJooqRepository<
  E,
  R : Record,
  T : Table<R>
  >(private val dslContext: DSLContext) : JooqRepository<E, R, T> {

  private val tableClass: Class<T> = getTypeFromClassGeneric(2, this.javaClass) as Class<T>
  private val entityClass: Class<E> = getTypeFromClassGeneric(0, this.javaClass) as Class<E>
  private val table: T = getTable()

  override fun findAll(): Flux<E> = Flux
    .fromIterable(
      dslContext
        .select()
        .from(table)
    ).mapNotNull { it.into(entityClass) }

  private fun getTable(): T {
    try {
      return tableClass.getDeclaredConstructor().newInstance()
    } catch (e: ReflectiveOperationException) {
      throw ReflectionException
        .fromMessageAndCause("Can not get table using Reflection API", e)
    }
  }
}
