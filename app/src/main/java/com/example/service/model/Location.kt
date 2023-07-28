package com.example.service.model

import java.io.Serializable

data class Location(
    val name: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val country: String? = null,
    val state: String? = null
): Serializable
