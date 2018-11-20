package company.sukiasyan.chatclient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import company.sukiasyan.chatclient.R
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val messages = mutableListOf<Message>()
    var continueDialogOnClient: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        HostPortDialogFragment().show(supportFragmentManager, null)

        button_chatbox_send.setOnClickListener {
            val message = edittext_chatbox.text.toString()
            val messageRequest = Message(message, TypeOfMessage.SentMessage)

            if (message.isNotEmpty()) {
                if(continueDialogOnClient){
                    when(message){

                    }
                }
                else {


                    val request = APIChat.create(getBaseUrl()).sendMessage(messageRequest.message)
                    request.enqueue(object : Callback<MessageReceived> {

                        override fun onResponse(call: Call<MessageReceived>?, response: Response<MessageReceived>?) {
                            response?.let {
                                val messageResponse = response.body()!!
                                messageResponse.type = TypeOfMessage.ReceivedMessage
                                messages.add(messageResponse)
                                recyclerview_message_list.adapter?.notifyItemInserted(messages.size - 1)

                                continueDialogOnClient = messageResponse.continue_dialog_on_client
                            }
                        }

                        override fun onFailure(call: Call<MessageReceived>?, t: Throwable?) {
                        }

                    })
                }
                messages.add(messageRequest)
            }
        }

        recyclerview_message_list.setHasFixedSize(true)
        recyclerview_message_list.layoutManager = LinearLayoutManager(this)
        recyclerview_message_list.adapter = MessageListAdapter(this, messages)


    }

    private fun getBaseUrl() = "http://${getHost()}:${getPort()}/"
}


