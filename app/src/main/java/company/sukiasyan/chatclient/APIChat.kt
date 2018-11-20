package company.sukiasyan.chatclient

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIChat {

    @FormUrlEncoded
    @POST("send_message")
    fun sendMessage(@Field("message") message: String): Call<MessageReceived>

    companion object Factory {
        fun create(baseUrl: String): APIChat {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build()

            return retrofit.create(APIChat::class.java)
        }
    }
}