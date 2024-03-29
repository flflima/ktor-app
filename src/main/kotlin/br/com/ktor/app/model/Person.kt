package br.com.ktor.app.model

import java.time.LocalDate

data class Person(val name: String, val document: String, val birthdate: LocalDate, val address: String, val email: String)
