package kz.solva.bank.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface Service<D> {

  fun findAll(): Flux<D>

  fun findById(id: Long): Mono<D>

  fun save(dto: D): Mono<D>

  fun delete(id: Long): Mono<D>
}
