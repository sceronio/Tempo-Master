package com.example.skyler.guitarapp

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import kotlinx.android.synthetic.main.playback_item.*

class PlaybackItem : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.playback_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val playButton: Button = playback_item_play

        playButton.setOnClickListener {
            Toast.makeText(context, "HELLO HELLO HELLO", LENGTH_LONG).show()
        }
    }
}