package com.wynnscribe.server.libs

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import com.google.firebase.auth.SessionCookieOptions
import java.io.File

object FirebaseAdmin {
    private val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(File(System.getenv("GOOGLE_SERVICE_ACCOUNT_PATH")?:"service-account.json").inputStream()))
        .build()

    private val app = runCatching { FirebaseApp.getInstance() }.getOrNull()?:FirebaseApp.initializeApp(options)

    private val auth = Authentication(app)

    @Suppress("FunctionName")
    fun Auth() = auth

    fun initialize() {
        // DO NOTHING
    }

    class Authentication(app: FirebaseApp) {
        val instance = FirebaseAuth.getInstance(app)

        fun verifyToken(token: String, checkRevoked: Boolean = true): Result<FirebaseToken> = runCatching {
            instance.verifyIdToken(token, checkRevoked)
        }

        fun verifySessionToken(token: String, checkRevoked: Boolean = true): Result<FirebaseToken> = runCatching {
            instance.verifySessionCookie(token, checkRevoked)
        }

        fun createSessionCookie(idToken: String): Result<String> = runCatching {
            instance.createSessionCookie(idToken, SessionCookieOptions.builder().setExpiresIn(1000 * 60 * 60 * 24 * 5).build())
        }
    }
}