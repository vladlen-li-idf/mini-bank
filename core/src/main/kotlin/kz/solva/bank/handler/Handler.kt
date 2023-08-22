package kz.solva.bank.handler

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.lang.Long.parseLong

interface Handler {

  fun findAll(request: ServerRequest): Mono<ServerResponse>

  fun findById(request: ServerRequest): Mono<ServerResponse>

  fun save(request: ServerRequest): Mono<ServerResponse>

  fun delete(request: ServerRequest): Mono<ServerResponse>

  fun getIdFromRequest(request: ServerRequest): Long = parseLong(request.pathVariable("id"))
}
