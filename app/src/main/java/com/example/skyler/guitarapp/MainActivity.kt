package com.example.skyler.guitarapp

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation


class MainActivity : AppCompatActivity(), MetronomeFragment.OnFragmentInteractionListener,
        RecorderFragment.OnFragmentInteractionListener, DrumFragment.OnFragmentInteractionListener,
        ContentMainFragment.OnFragmentInteractionListener, DrumPlayBackFragment.OnFragmentInteractionListener
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onFragmentInteraction(uri: Uri) {
        println("Log state ==> $uri")
    }



    //override fun onSupportNavigateUp(): Boolean = Navigation.findNavController(this, R.id.nav_host).navigateUp()

}