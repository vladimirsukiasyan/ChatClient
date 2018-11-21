package company.sukiasyan.chatclient

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import company.sukiasyan.chatclient.R
import kotlinx.android.synthetic.main.fragment_host_port_dialog.view.*

class HostPortDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.fragment_host_port_dialog, null)!!
        return AlertDialog.Builder(activity)
                .setMessage("Укажите, пожалуйста, адрес хоста и порт!")
                .setView(view)
                .setPositiveButton("Подтвердить") { dialog, _  ->
                    val host = view.host_edit_text.text.toString()
                    val port = view.port_edit_text.text.toString()

                    activity?.saveHttpConfig(host,port)

                    dialog.dismiss()
                }
                .create()
    }
}
