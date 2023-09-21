package kz.solva.bank.repository

import kz.solva.bank.exception.ReflectionException
import kz.solva.bank.uti.ReflectionUtils.getTypeFromClassGeneric
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Table
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.util.Optional

abstract class AbstractJooqRepository<
    E,
    R : Record,
    T : Table<R>
    >(private val dslContext: DSLContext) : ReactiveRepository<E> {

  private val tableClass: Class<T> = getTypeFromClassGeneric(2, this.javaClass) as Class<T>
  private val entityClass: Class<E> = getTypeFromClassGeneric(0, this.javaClass) as Class<E>
  private val table: T = getTable()

  override fun findAll(): Flux<E> = Flux
    .fromIterable(
      dslContext
        .selectFrom(table)
    ).mapNotNull { it.into(entityClass) }

  override fun findById(id: Long): Mono<E> =
    getMonoEntity {
      dslContext
        .selectFrom(table)
        .where(eq(id))
        .fetchOptional()
    }

  override fun save(entity: E): Mono<E> =
    getMonoEntity {
      dslContext
        .transactionResult { config ->
          val transactionContext = config.dsl()
          transactionContext
            .insertInto(table)
            .set(transactionContext.newRecord(table, entity))
            .returning()
            .fetchOptional()
        }
    }

  override fun delete(id: Long): Mono<E> =
    getMonoEntity {
      dslContext
        .delete(table)
        .where(eq(id))
        .returning()
        .fetchOptional()
    }

  private fun getMonoEntity(executable: () -> Optional<R>): Mono<E> = executable
      .invoke()
      .get()
      .toMono()
      .mapNotNull { it.into(entityClass) }

  private fun getTable(): T {
    try {
      return tableClass.getDeclaredConstructor().newInstance()
    } catch (e: ReflectiveOperationException) {
      throw ReflectionException
        .fromMessageAndCause("Can not get table using Reflection API", e)
    }
  }

  protected abstract fun eq(id: Long): Condition
}
