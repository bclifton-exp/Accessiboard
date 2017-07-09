package boomcity.accessiboard

import android.app.AlertDialog
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView

class SoundRecyclerAdapter(data: MutableList<TtsObject>, val tabPosition: Int) : RecyclerView.Adapter<SoundRecyclerAdapter.ViewHolder>() {
    private var mDataset: MutableList<TtsObject> = data
    var mp: MediaPlayer = MediaPlayer()
    var mediaState: MediaState = MediaState.OK
    var currentTrack = 0
    lateinit var lastPlayButton: ImageButton

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val mTtsObject = TtsObject(mDataset[position].Title, mDataset[position].Phrase, mDataset[position].TtsId)

        val text = holder!!.mView.findViewById(R.id.sound_clip_text_view) as TextView
        text.setText(mTtsObject.Title)

        val deleteButton = holder.mView.findViewById(R.id.delete_button) as ImageButton
        deleteButton.setOnClickListener {
            if(tabPosition != 0) {
                DataService.removeTtsObjectFromTab(this, mTtsObject, tabPosition)
            }
            else {
                val deleteConfirmation = AlertDialog.Builder(holder.mView.context, R.style.DankAlertDialogStyle)
                deleteConfirmation.setTitle(R.string.permanent_delete)
                deleteConfirmation.setNegativeButton(R.string.dialog_cancel, { dialog, which ->
                    dialog.dismiss()
                })
                deleteConfirmation.setPositiveButton(R.string.dialog_yes, {dialog, which ->
                    DataService.removeTtsObjectFromApp(mTtsObject)
                    dialog.dismiss()
                })
                deleteConfirmation.show()
            }
        }

        val playButton = holder.mView.findViewById(R.id.play_button) as ImageButton

        playButton.setOnClickListener {

            //TODO

//            if (currentTrack == mTtsObject.AudioId && mediaState == MediaState.OK && mp.isPlaying) {
//                mp.stop()
//                playButton.setImageResource(R.drawable.ic_playbutton)
//                mp.release()
//                mediaState = MediaState.RELEASED
//            }
//            else {
//                if (mediaState == MediaState.OK && mp.isPlaying) {
//                    mp.stop()
//                    mp.release()
//                    if(lastPlayButton != playButton) {
//                        lastPlayButton.setImageResource(R.drawable.ic_playbutton)
//                    }
//                    mediaState = MediaState.RELEASED
//                }
//
//                if (mTtsObject.Path != null) {
//                    try {
//                        mp = MediaPlayer.create(holder.mView.context, Uri.parse(mTtsObject.Path))
//                    } catch (ex: Exception) {
//                        text.setText(R.string.invalid_path)
//                    }
//                } else {
//                    mp = MediaPlayer.create(holder.mView.context, mTtsObject.AudioId)
//                }
//                mp.setOnCompletionListener {
//                    playButton.setImageResource(R.drawable.ic_playbutton)
//                    mp.release()
//                    mediaState = MediaState.RELEASED
//                }
//
//                mp.start()
//                playButton.setImageResource(android.R.drawable.ic_media_pause)
//                lastPlayButton = playButton
//                mediaState = MediaState.OK
//                currentTrack = mTtsObject.AudioId
//            }
        }

        val favoritesExists = holder.mView.findViewById(R.id.favorite_button)
        if (favoritesExists != null) {
            val addToFavoriteButton = holder.mView.findViewById(R.id.favorite_button) as ImageButton
            addToFavoriteButton.setTag(mDataset[position].TtsId)
            addToFavoriteButton.setOnClickListener {
                showTabSelectionDialog(holder.mView.context, mTtsObject)
            }
        }
    }

    fun showTabSelectionDialog(context: Context, ttsObject: TtsObject) {
        val builder = AlertDialog.Builder(context, R.style.DankAlertDialogStyle)
        builder.setTitle(R.string.add_dank_sound)

        var arrayAdapter = ArrayAdapter<String>(context, android.R.layout.select_dialog_item)

        for (tab in DataService.getTabsData().tabsList!!) {
            if(tab.position != 0) {
                arrayAdapter.add(tab.name)
            }
        }

        builder.setNegativeButton(R.string.dialog_cancel, { dialog, which ->
            dialog.dismiss()
        })

        builder.setAdapter(arrayAdapter, { dialog, which ->

            if (DataService.getTabsData().getTab(which + 1)!!.ttsObjects.any { tts -> tts.TtsId == ttsObject.TtsId }) {
                val errorBuilder = AlertDialog.Builder(context, R.style.DankAlertDialogStyle)
                errorBuilder.setTitle(R.string.dank_sound_exists)
                errorBuilder.setNegativeButton(R.string.dialog_aight, { dialog, which ->
                    dialog.dismiss()
                })
                errorBuilder.show()
            }
            else {
                DataService.addTtsObjectToTab(ttsObject, which + 1)
            }
        })

        builder.show()
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        val ttsFragmentId: Int

        if (tabPosition > 0) {
            ttsFragmentId = R.layout.fragment_sound_clip
        }
        else {
            ttsFragmentId = R.layout.fragment_sound_clip_all
        }

        val v = LayoutInflater.from(parent!!.getContext()).inflate(ttsFragmentId, parent, false)
        val vh = ViewHolder(v)
        return vh
    }

    class ViewHolder(var mView: View) : RecyclerView.ViewHolder(mView)

    enum class MediaState {
        OK,
        RELEASED,
    }

}
