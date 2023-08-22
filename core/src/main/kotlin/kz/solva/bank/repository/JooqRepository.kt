package kz.solva.bank.repository

import org.jooq.Record
import org.jooq.Table
import reactor.core.publisher.Flux

interface JooqRepository<
  E,
  R : Record,
  T : Table<R>
  > {
  fun findAll(): Flux<E>
}
