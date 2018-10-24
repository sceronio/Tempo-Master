package com.example.skyler.guitarapp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_recorder.*

object ClickUtils {

    private var recording = false
    private var playing = false


    fun animate(button : Button) {
        var animationLength : Long = 35

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

    fun clickRecord(recordButton : Button) {
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

    fun clickStop(recordButton : Button) {
        animate(recordButton)
        if(recording) {
            recordButton.setBackgroundResource(R.drawable.record_button)
            //stopButton.setBackgroundResource(R.drawable.stop_button)
            recording = false
        }
    }

    fun clickPlay(playButton : Button) {
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
