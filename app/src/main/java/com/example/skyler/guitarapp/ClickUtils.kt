package com.example.skyler.guitarapp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.widget.Button
import java.lang.NullPointerException

//this object keeps track of the state of the recorder buttons
object ClickUtils {

    private var recording = false
    private var playing = false
    private val RECORD = "record"
    private val STOP = "stop"
    private val PLAY = "play"

    fun getRecord() : String {
        return this.RECORD
    }

    fun getStop() : String {
        return this.STOP
    }

    fun getPlay() : String {
        return this.PLAY
    }

    fun isPlaying() : Boolean {
        return this.playing
    }

    fun isRecording() : Boolean {
        return this.recording
    }


    fun animate(button : Button) {
        val animationLength : Long = 35

        val scaleDownX = ObjectAnimator.ofFloat(button, "scaleX", 0.6f)
        val scaleDownY = ObjectAnimator.ofFloat(button, "scaleY", 0.6f)
        scaleDownX.setDuration(animationLength)
        scaleDownY.setDuration(animationLength)

        val scaleUpX = ObjectAnimator.ofFloat(button, "scaleX", 1f)
        val scaleUpY = ObjectAnimator.ofFloat(button, "scaleY", 1f)
        scaleUpX.setDuration(animationLength)
        scaleUpY.setDuration(animationLength)

        val scaleDown = AnimatorSet()
        scaleDown.play(scaleDownX).with(scaleDownY)
        scaleDown.start()

        val scaleUp = AnimatorSet()
        scaleUp.play(scaleUpX).with(scaleUpY).after(animationLength)
        scaleUp.start()
    }

    fun clickRecord(buttons : Map<String, Button>) {
        val recordButton : Button = buttons.get(RECORD) ?: throw NullPointerException("button RECORD not found")
        animate(recordButton)
        if(recording) {
            recordButton.setBackgroundResource(R.drawable.record_button)
            recording = false
        }
        else {
            recordButton.setBackgroundResource(R.drawable.mic_pressed)
            //stopButton.setBackgroundResource(R.drawable.stop_recording)
            recording = true
        }
    }

    fun clickStop(buttons : Map<String, Button>) {
        val recordButton : Button = buttons.get(RECORD) ?: throw NullPointerException("button RECORD not found")
        val stopButton : Button = buttons.get(STOP) ?: throw NullPointerException("button STOP not found")
        animate(stopButton)
        if(recording) {
            recordButton.setBackgroundResource(R.drawable.record_button)
            //stopButton.setBackgroundResource(R.drawable.stop_button)
            recording = false
        }
    }

    fun clickPlay(buttons : Map<String, Button>) {
        val playButton : Button = buttons.get(PLAY) ?: throw NullPointerException("button PLAY not found")
        animate(playButton)
        if(playing) {
            playButton.setBackgroundResource(R.drawable.play_button)
            playing = false
        }
        else {
            playButton.setBackgroundResource(R.drawable.play_pressed)
            playing = true
        }
    }
}
