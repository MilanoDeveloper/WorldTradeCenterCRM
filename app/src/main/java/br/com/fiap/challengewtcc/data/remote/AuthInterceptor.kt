package br.com.fiap.challengewtcc.data.remote

import okhttp3.Interceptor
import okhttp3.Response

object SessionManager {
    var token: String? = null
}

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        val token = SessionManager.token

        if (token.isNullOrBlank()) {
            return chain.proceed(originalRequest)
        }

        val authenticatedRequest = originalRequest.newBuilder()
            .addHeader(
                "Authorization",
                "Bearer $token"
            )
            .build()

        return chain.proceed(authenticatedRequest)
    }
}