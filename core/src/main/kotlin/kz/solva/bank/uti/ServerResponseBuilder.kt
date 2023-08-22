package kz.solva.bank.uti

import org.reactivestreams.Publisher
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

object ServerResponseBuilder {
  fun getServerResponse(status: Int): Mono<ServerResponse> {
    return ServerResponse
        .status(status)
        .build()
  }

  fun <E : Any> getServerResponse(body: E?, status: Int, contentType: MediaType?): Mono<ServerResponse> {
    return ServerResponse
        .status(status)
        .contentType(contentType!!)
        .bodyValue(body!!)
  }

  fun getServerResponse(body: Publisher<*>?, bodyClass: Class<*>, status: Int,
                        contentType: MediaType): Mono<ServerResponse> {
    return ServerResponse
        .status(status)
        .contentType(contentType)
        .body(body!!, bodyClass)
  }
}
