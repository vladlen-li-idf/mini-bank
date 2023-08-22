package kz.solva.bank.mapper

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface EntityToDtoMapper<E, D> {

    fun toDto(entity: E): D

    fun toEntity(dto: D): E

    fun toMonoDto(executable: () -> Mono<E>): Mono<D> = executable
            .invoke()
            .mapNotNull { toDto(it) }

    fun toFluxDto(executable: () -> Flux<E>): Flux<D> = executable
            .invoke()
            .mapNotNull { toDto(it) }
}
