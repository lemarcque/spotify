package io.capsulo.spotify.features.player

/**
 * Represent a line in a text with a possibly annotation.
 */
class Referent(
    val tag: String,
    val text: String,
    var time: Double?
) {
    var timeShorted: Double? = null
}