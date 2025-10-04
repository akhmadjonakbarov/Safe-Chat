package uz.akbarovdev.safechat.core.retrofits

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BaseRetrofitBuilder {

    fun buildRetrofit(url: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(
            url
        ).client(client).addConverterFactory(GsonConverterFactory.create()).build()
    }

}