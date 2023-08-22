package kz.solva.bank

import org.springframework.boot.fromApplication
import org.springframework.boot.with

class TestBankApplication

fun main(args: Array<String>) {
	fromApplication<BankApplication>().with(TestBankApplication::class).run(*args)
}
