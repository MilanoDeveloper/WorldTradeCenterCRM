package br.com.fiap.challengewtcc.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val BASE_URL = "http://10.0.2.2:8080/api/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(AuthInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val campaignService: CampaignService by lazy {
        retrofit.create(CampaignService::class.java)
    }

    val notificationService: NotificationService by lazy {
        retrofit.create(NotificationService::class.java)
    }

    val chatService: ChatService by lazy {
        retrofit.create(ChatService::class.java)
    }

    val clientService: ClientService by lazy {
        retrofit.create(ClientService::class.java)
    }

    val dashboardService: DashboardService by lazy {
        retrofit.create(DashboardService::class.java)
    }

}