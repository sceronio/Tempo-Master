package com.example.skyler.guitarapp

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.widget.Button
import java.util.*

class Player constructor(val context: Context?, val soundMap: HashMap<Int, Int>, val buttonMap: Map<String, Button>) {

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
                    Handler().post({ClickUtils.clickPlay(buttonMap)})
                    Looper.loop()
                }
            }
        }, recordedBeat.getDelay(index))
    }
}
