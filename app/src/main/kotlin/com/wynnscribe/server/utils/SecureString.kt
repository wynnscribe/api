package com.wynnscribe.server.utils

fun isNeedReview(text: String): Boolean {
    if("click:" in text) {
        return true
    }
    if("http:" in text) {
        return true
    }
    if("https:" in text) {
        return true
    }
    return false
}