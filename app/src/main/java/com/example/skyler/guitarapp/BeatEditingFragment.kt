package com.example.skyler.guitarapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_beat_editing.*
import kotlinx.android.synthetic.main.fragment_bottom_nav_bar.*
import kotlinx.android.synthetic.main.fragment_drum.*
import kotlinx.android.synthetic.main.fragment_drum_play_back.*
import kotlinx.android.synthetic.main.fragment_recorder.*
import kotlinx.android.synthetic.main.fragment_recorder.view.*
import java.io.File


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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
class BeatEditingFragment : Fragment() {

    //region Globals
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    //endregion

    //region Life-Cycle Methods
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
        return inflater.inflate(R.layout.fragment_beat_editing, container, false)
    }
    //endregion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //region Make DrumPlayBackItemModelList
        //get shared prefs
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val prefMap : MutableMap<String, *> = prefs.all

        val gson = Gson()

        //get keys for shared preferences
        val keys = prefMap.keys.toTypedArray()

        //create list of playbackItemModels
        val playbackItemList : ArrayList<DrumPlaybackItemModel> = arrayListOf()
        for(filename in keys) {
            var currentPlaybackObject = prefMap[filename]
            //deserialize each object to a DrumPlaybackItemModel and add it to the list
            if(currentPlaybackObject is String) {
                currentPlaybackObject = gson.fromJson(currentPlaybackObject, DrumPlaybackItemModel::class.java)
                playbackItemList.add(currentPlaybackObject)
            }
        }

        //create the adapterDrum for playbackItemModelList
        val comboAdapterDrum = ComboDrumPlaybackItemsAdapter(context, playbackItemList)
        combo_list_view.adapter = comboAdapterDrum
        //endregion

        //region make RecordingPlaybackItemModel list
        //this gives you a reference to the file system
        val path: File = context!!.filesDir
        val fileList = path.list()
        val fileDescriptorList = path.listFiles()
        val recordingItemList : ArrayList<RecordingPlaybackItemModel> = arrayListOf()

        var i = 0
        while(i < fileList.size) {
            recordingItemList.add(RecordingPlaybackItemModel(fileList[i], fileDescriptorList[i]))
            i++
        }

        val comboAdapterRecording = ComboRecordingPlaybackItemsAdapter(context, recordingItemList)
        //endregion

        //region Switch Buttons
        combo_recordings.setOnClickListener {
            combo_list_view.adapter = comboAdapterRecording
        }

        combo_beats.setOnClickListener {
            combo_list_view.adapter = comboAdapterDrum
        }

        //region Navbar Code
        drumButton.setOnClickListener {
            val directions = BeatEditingFragmentDirections.action_beatEditingFragment_to_drumFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }

        metronomeButton.setOnClickListener{
            val directions = BeatEditingFragmentDirections.action_beatEditingFragment_to_metronomeFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }

        playbackButton.setOnClickListener {
            val directions = BeatEditingFragmentDirections.action_beatEditingFragment_to_drumPlayBackFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }

        guitarButton.setOnClickListener {
            val directions = BeatEditingFragmentDirections.action_beatEditingFragment_to_guitarRecordingFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }
        //endregion
    }

    //region Boilerplate Override Code
    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
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
    //endregion
}
