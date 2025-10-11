package uz.akbarovdev.safechat.core.retrofits

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import uz.akbarovdev.safechat.core.extension.sharedPreferences
import java.util.concurrent.TimeUnit

object SafeChatClient {


    //    private const val BASE_URL = "http://92.112.181.239/api/v1/"
//    private const val BASE_URL = "http://avangard-mobile.uz/api/v1/"

    private const val BASE_URL = "http://10.0.2.2:8000/api/v1/"
    val logging = HttpLoggingInterceptor { message ->
        // Avoid spamming binary data into logs
        if (message.startsWith("{") || message.startsWith("[")) {
            Log.d("OkHttp", message) // pretty JSON
        } else {
            Log.d("OkHttp", "Non-text response (possibly binary)")
        }
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    fun getClient(applicationContext: Context): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS).addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                val token by applicationContext.sharedPreferences("Token")
                requestBuilder.addHeader("Authorization", "Bearer $token")
                chain.proceed(requestBuilder.build())
            }.addInterceptor(logging)
            .build()
        return BaseRetrofitBuilder.buildRetrofit(BASE_URL, httpClient)
    }
}
