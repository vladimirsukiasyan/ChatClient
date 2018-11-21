package company.sukiasyan.chatclient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import company.sukiasyan.chatclient.R
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val TAG: String="Activities"

    val messages = mutableListOf<Message>()
    var multiplyChoiceList = listOf<Professor>()
    var continueDialogOnClient: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        HostPortDialogFragment().show(supportFragmentManager, null)

        button_chatbox_send.setOnClickListener {
            Log.d(TAG,"send is clicked")

            val message = edittext_chatbox.text.toString()
            val messageRequest = Message(message, TypeOfMessage.SentMessage)


            if (message.isNotEmpty()) {
                messages.add(messageRequest)

                if(continueDialogOnClient){
                    messages.add(MessageReceived(listOf(multiplyChoiceList[message.toInt()-1]),false,""))
                    continueDialogOnClient=false
                    updateRecyclerView()
                }
                else {
                    val request = APIChat.create(getBaseUrl()).sendMessage(messageRequest)
                    request.enqueue(object : Callback<MessageReceived> {

                        override fun onResponse(call: Call<MessageReceived>?, response: Response<MessageReceived>?) {
                            response?.let {
                                if(response.isSuccessful){
                                    val messageReceived = response.body()!!
                                    messageReceived.type = TypeOfMessage.ReceivedMessage

                                    messages.add(messageReceived)
                                    continueDialogOnClient = messageReceived.continue_dialog_on_client
                                    if(continueDialogOnClient){
                                        multiplyChoiceList=messageReceived.body!!
                                    }
                                    updateRecyclerView()
                                }
                                else{
                                    messages.add(MessageReceived(null,false, response.message()))
                                }
                            }
                        }

                        override fun onFailure(call: Call<MessageReceived>?, t: Throwable?) {
                            Log.d(TAG,t.toString())
                        }

                    })
                }
            }
            edittext_chatbox.text.clear()
        }

        recyclerview_message_list.setHasFixedSize(true)
        recyclerview_message_list.layoutManager = LinearLayoutManager(this)
        recyclerview_message_list.adapter = MessageListAdapter(this, messages)


    }

    private fun updateRecyclerView(){
        recyclerview_message_list.adapter?.notifyDataSetChanged()
        recyclerview_message_list.scrollToPosition(recyclerview_message_list.adapter?.itemCount!! -1)
    }
    private fun getBaseUrl() = "http://${getHost()}:${getPort()}/"
}


