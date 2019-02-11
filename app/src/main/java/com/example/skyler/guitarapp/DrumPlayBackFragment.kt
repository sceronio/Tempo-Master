package com.example.skyler.guitarapp

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_bottom_nav_bar.*
import kotlinx.android.synthetic.main.fragment_drum_play_back.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

//TODO: bug where the item after an item that was just removed cannot be removed 
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DrumPlayBackFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DrumPlayBackFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DrumPlayBackFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var adapter: PlaybackItemsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drum_play_back, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get shared preferences editor
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        var prefMap : MutableMap<String, *> = prefs.all
        val gson = Gson()


        //get keys in shared preferences for populating list with recordings
        val keys = prefMap.keys.toTypedArray()

        val playbackItemModelList : ArrayList<PlaybackItemModel> = arrayListOf()

        //create list of playbackitemmodels
        for(filename in keys) {
            var currentPlaybackObject = prefMap[filename]
            if(currentPlaybackObject is String) {
                currentPlaybackObject = gson.fromJson(currentPlaybackObject, PlaybackItemModel::class.java)
                playbackItemModelList.add(currentPlaybackObject)
            }
        }

        //create the adapter for playbackItemModelList
        adapter = PlaybackItemsAdapter(context, playbackItemModelList)
        //set adapter on the view
        playback_list_view.adapter = adapter

        var pref_listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
           //notify adapter that it must refresh when shared preferences changes
            //adapter?.notifyDataSetChanged()
        }

        prefs.registerOnSharedPreferenceChangeListener(pref_listener)

        //navbar code
        drumButton.setOnClickListener {
            val directions = DrumPlayBackFragmentDirections.action_drumPlayBackFragment_to_drumFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }

        metronomeButton.setOnClickListener{
            val directions = DrumPlayBackFragmentDirections.action_drumPlayBackFragment_to_metronomeFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }

        musicButton.setOnClickListener {
            val directions = DrumPlayBackFragmentDirections.action_drumPlayBackFragment_to_beatEditingFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }

        guitarButton.setOnClickListener {
            val directions = DrumPlayBackFragmentDirections.action_drumPlayBackFragment_to_guitarRecordingFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /*fun onSharedPreferenceChanged(sharedPreferences : SharedPreferences, key: String) {
        getActivity()?.runOnUiThread{ object: Runnable {
            override fun run() {
                adapter?.notifyDataSetChanged()
            }
        }
    } }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DrumPlayBackFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                DrumPlayBackFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
