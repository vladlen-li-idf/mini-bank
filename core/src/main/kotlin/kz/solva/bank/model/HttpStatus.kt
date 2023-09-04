package kz.solva.bank.model

import org.springframework.http.HttpStatus

object HttpStatus {

  val OK = HttpStatus.OK.value()
  val NO_CONTENT = HttpStatus.NO_CONTENT.value()
  val BAD_REQUEST = HttpStatus.BAD_REQUEST.value()
  val ACCEPTED = HttpStatus.ACCEPTED.value()
}
