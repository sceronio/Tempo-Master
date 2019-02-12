package com.example.skyler.guitarapp

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation


class MainActivity : AppCompatActivity(), MetronomeFragment.OnFragmentInteractionListener,
        RecorderFragment.OnFragmentInteractionListener, DrumFragment.OnFragmentInteractionListener,
        ContentMainFragment.OnFragmentInteractionListener, DrumPlayBackFragment.OnFragmentInteractionListener,
        BeatEditingFragment.OnFragmentInteractionListener, GuitarRecordingFragment.OnFragmentInteractionListener
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    override fun onFragmentInteraction(uri: Uri) {
        println("Log state ==> $uri")
    }

    //override fun onSupportNavigateUp(): Boolean = Navigation.findNavController(this, R.id.nav_host).navigateUp()
}