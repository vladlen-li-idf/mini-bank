package kz.solva.bank.repository

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ReactiveRepository<E> {

  fun findAll(): Flux<E>

  fun findById(id: Long): Mono<E>

  fun save(entity: E): Mono<E>

  fun delete(id: Long): Mono<E>
}
