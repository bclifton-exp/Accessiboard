package boomcity.accessiboard

import android.content.DialogInterface
import android.app.Dialog
import android.os.Bundle
import android.app.AlertDialog
import android.app.DialogFragment
import android.widget.EditText

class EditDialogFragment(activity: MainActivity) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity, R.style.DankAlertDialogStyle)
        val inflater = activity.layoutInflater

        builder.setView(inflater.inflate(R.layout.edit_tab_name_dialog,null))
                .setPositiveButton(R.string.dialog_ok, DialogInterface.OnClickListener { dialog, id ->
                    val dialogBox = dialog as Dialog
                    val editText = dialogBox.findViewById(R.id.new_tab_name_text) as EditText
                    val newTabName = editText.text.toString()

                    if(this.tag != null) {
                        DataService.renameTab(newTabName,this.tag.toInt())
                    }
                    else
                    {
                        val ma = activity as MainActivity
                        ma.addNewTab(newTabName)
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() })
        return builder.create()
    }
}
