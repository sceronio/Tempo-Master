package com.example.skyler.guitarapp

import android.support.v4.app.Fragment
import androidx.navigation.fragment.NavHostFragment

object NavUtils {

    fun clickDrum(currentFragment : Fragment) {
        val directions = ContentMainFragmentDirections.action_contentMainFragment_to_metronomeFragment()
        NavHostFragment.findNavController(currentFragment).navigate(directions)
    }
}