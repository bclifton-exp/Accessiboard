package boomcity.accessiboard

import android.content.DialogInterface
import android.app.Dialog
import android.os.Bundle
import android.app.AlertDialog
import android.app.DialogFragment
import android.widget.EditText

class NewSoundClipDialogFragment(activity: MainActivity) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity, R.style.DankAlertDialogStyle)
        val inflater = activity.layoutInflater

        builder.setView(inflater.inflate(R.layout.new_soundclip_dialog,null))
                .setPositiveButton(R.string.dialog_ok, DialogInterface.OnClickListener { dialog, id ->
                    val dialogBox = dialog as Dialog
                    val editText = dialogBox.findViewById(R.id.new_soundclip_name_text) as EditText
                    val newSoundName = editText.text.toString()
                    val ma = activity as MainActivity
                    ma.newSoundClipName(newSoundName,tag)
                })
                .setNegativeButton(R.string.dialog_cancel, DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() })
        return builder.create()
    }
}
