package com.example.calculator

sealed class Exceptions(error: String): Exception(error)

object NotSuchCharactersException: Exceptions("Value must be a number")

object InsufficientDataException: Exceptions("Insufficient data")

object UnexpectedException: Exceptions("Unexpected error")
