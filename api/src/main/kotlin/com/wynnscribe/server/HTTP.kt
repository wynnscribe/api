package com.wynnscribe.server

import com.ucasoft.ktor.simpleCache.SimpleCache
import com.ucasoft.ktor.simpleCache.SimpleCacheProvider
import com.ucasoft.ktor.simpleMemoryCache.memoryCache
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.application.install
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.conditionalheaders.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.forwardedheaders.*
import io.ktor.server.plugins.hsts.*
import io.ktor.server.plugins.partialcontent.*
import kotlin.time.Duration.Companion.seconds

fun Application.configureHTTP() {
    install(SimpleCache) {
        memoryCache {
            invalidateAt = 10.seconds
        }
    }
    install(PartialContent) {
            // Maximum number of ranges that will be accepted from a HTTP request.
            // If the HTTP request specifies more ranges, they will all be merged into a single range.
            maxRangeCount = 10
        }
    install(HSTS) {
        includeSubDomains = true
    }
    install(ForwardedHeaders) // WARNING: for security, do not include this if not behind a reverse proxy
    install(XForwardedHeaders) // WARNING: for security, do not include this if not behind a reverse proxy
    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }
    install(ConditionalHeaders)
    install(Compression)
    install(CachingHeaders) {
        options { call, outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                ContentType.Text.CSS -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 24 * 60 * 60))
                else -> null
            }
        }
    }
}
