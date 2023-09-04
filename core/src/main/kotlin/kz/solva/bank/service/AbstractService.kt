package kz.solva.bank.service

import kz.solva.bank.mapper.EntityToDtoMapper
import kz.solva.bank.repository.ReactiveRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

abstract class AbstractService<E, D, R : ReactiveRepository<E>>(
    private val repository: R,
    private val mapper: EntityToDtoMapper<E, D>) : Service<D> {

  override fun findAll(): Flux<D> = mapper.toFluxDto { repository.findAll() }

  override fun findById(id: Long): Mono<D> = mapper.toMonoDto { repository.findById(id) }

  override fun save(dto: D): Mono<D> = mapper.toMonoDto {
    repository.save(mapper.toEntity(dto))
  }

  override fun delete(id: Long): Mono<D> = mapper.toMonoDto { repository.delete(id) }
}
