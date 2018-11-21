package company.sukiasyan.chatclient

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIChat {

    @POST("api/v1/send_message")
    fun sendMessage(@Body message: Message): Call<MessageReceived>

    companion object Factory {
        fun create(baseUrl: String): APIChat {
            val interceptor=HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client=OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .client(client)
                    .build()

            return retrofit.create(APIChat::class.java)
        }
    }
}