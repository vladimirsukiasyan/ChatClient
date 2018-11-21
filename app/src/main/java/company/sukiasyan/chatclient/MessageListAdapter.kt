package company.sukiasyan.chatclient

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_message_sent.view.*


class MessageListAdapter(private val mContext: Context, private val mMessageList: List<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            SentMessageHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_sent, parent, false))

        } else {
            ReceivedMessageHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_received, parent, false))
        }
    }

    override fun getItemCount() = mMessageList.size

    override fun getItemViewType(position: Int) =
            if (mMessageList[position].type == TypeOfMessage.SentMessage) {
                VIEW_TYPE_MESSAGE_SENT
            } else {
                VIEW_TYPE_MESSAGE_RECEIVED
            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = mMessageList[position]

        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageHolder).bind(message)
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedMessageHolder).bind(message as MessageReceived)
        }
    }

    inner class SentMessageHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(message: Message) {
            itemView.text_message_body.text = message.message
        }
    }

    inner class ReceivedMessageHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(message: MessageReceived) {
            if(message.continue_dialog_on_client){
                itemView.text_message_body.text=message.message
            }
            else {
                itemView.text_message_body.text="${message.message}\n${convertListToChatAnswer(message.body)}"
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_MESSAGE_SENT = 1
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 2
    }
}