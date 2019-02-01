package com.example.skyler.guitarapp

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.widget.Button
import java.util.*

class Player constructor(val context: Context?) {

    var soundMap = hashMapOf(1 to R.raw.newjr_16, 2 to R.raw.newjr_13, 3 to R.raw.rimshot,
            4 to R.raw.newjr_19, 5 to R.raw.newjr_16, 6 to R.raw.mc_snare_4b,
            7 to R.raw.newjr_16, 8 to R.raw.newjr_16)

    //play the next sound in the list of recorded sounds
    fun playNext(index: Int, recordedBeat: RecordedBeat)
    {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val mediaPlayer = MediaPlayer.create(context, soundMap[recordedBeat.getSound(index)] ?: 0)
                mediaPlayer?.start()
                mediaPlayer.setOnCompletionListener { mediaPlayer.release() }
                if (recordedBeat.getSize() > index + 1) {
                    playNext(index + 1, recordedBeat)
                }
                else {
                    Looper.prepare()
                    //Handler().post({ClickUtils.clickPlay(buttonMap)})
                    Looper.loop()
                }
            }
        }, recordedBeat.getDelay(index))
    }
}
