package kz.solva.bank.handler

import kz.solva.bank.model.HttpStatus.ACCEPTED
import kz.solva.bank.model.HttpStatus.BAD_REQUEST
import kz.solva.bank.model.HttpStatus.NO_CONTENT
import kz.solva.bank.model.HttpStatus.OK
import kz.solva.bank.model.Response
import kz.solva.bank.service.Service
import kz.solva.bank.uti.ReflectionUtils.getTypeFromClassGeneric
import kz.solva.bank.uti.ServerResponseBuilder.getServerResponse
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

abstract class AbstractHandler<D, S : Service<D>>(private val service: S) : Handler {

  private val dtoClass: Class<D> = getTypeFromClassGeneric(0, this.javaClass) as Class<D>

  override fun findById(request: ServerRequest): Mono<ServerResponse> {
    return getIdFromRequest(request)
        .toMono()
        .flatMap(service::findById)
        .flatMap { dto ->
          getServerResponse(dto, ACCEPTED, MediaType.APPLICATION_JSON)
        }
        .onErrorResume { error: Throwable -> handleServiceError(error, NO_CONTENT) }
  }

  override fun findAll(request: ServerRequest): Mono<ServerResponse> {
    return getServerResponse(
        service.findAll(), MutableList::class.java,
        OK, MediaType.APPLICATION_JSON
    )
  }

  override fun save(request: ServerRequest): Mono<ServerResponse> {
    return request.bodyToMono(dtoClass)
        .flatMap(service::save)
        .flatMap { dto ->
          getServerResponse(dto, OK, MediaType.APPLICATION_JSON)
        }
        .onErrorResume { error: Throwable -> handleServiceError(error, BAD_REQUEST) }
  }

  override fun delete(request: ServerRequest): Mono<ServerResponse> {
    return getIdFromRequest(request)
        .toMono()
        .mapNotNull(service::delete)
        .flatMap { dto ->
          getServerResponse(dto, OK, MediaType.APPLICATION_JSON)
        }
        .onErrorResume { error: Throwable -> handleServiceError(error, NO_CONTENT) }
  }

  private fun handleServiceError(error: Throwable, status: Int): Mono<ServerResponse> {
    val errorResponse = Response("error", error.message!!)
    val preResponse = ServerResponse.status(status)
    return if (status == BAD_REQUEST) {
      preResponse.contentType(MediaType.APPLICATION_JSON).bodyValue(errorResponse)
    } else preResponse.build()
  }
}
