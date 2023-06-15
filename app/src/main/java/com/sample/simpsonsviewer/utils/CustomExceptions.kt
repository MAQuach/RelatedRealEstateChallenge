package com.sample.simpsonsviewer.utils

class FailureResponse(message: String?) : Exception(message)

class NullCharacterListResponse(
    message: String = "The character list response is null."
) : Exception(message)
