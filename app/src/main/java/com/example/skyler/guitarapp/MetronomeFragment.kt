package com.example.skyler.guitarapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_bottom_nav_bar.*
import kotlinx.android.synthetic.main.fragment_drum.*
import kotlinx.android.synthetic.main.fragment_recorder.*
import kotlinx.android.synthetic.main.fragment_recorder.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MetronomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MetronomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MetronomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

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
        return inflater.inflate(R.layout.fragment_metronome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var buttonMap : Map<String, Button> = hashMapOf(ClickUtils.getPlay() to playButton,
                ClickUtils.getRecord() to recordButton,
                ClickUtils.getStop() to stopButton)
        recordButton.setOnClickListener {
            ClickUtils.clickRecord(buttonMap)
        }

        stopButton.setOnClickListener {
            ClickUtils.clickStop(buttonMap)
        }

        playButton.setOnClickListener {
            ClickUtils.clickPlay(buttonMap)
        }

        //navbar code
        drumButton.setOnClickListener {
            val directions = MetronomeFragmentDirections.action_metronomeFragment_to_drumFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }

        playbackButton.setOnClickListener {
            val directions = MetronomeFragmentDirections.action_metronomeFragment_to_drumPlayBackFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }

        musicButton.setOnClickListener {
            val directions = MetronomeFragmentDirections.action_metronomeFragment_to_beatEditingFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }

        guitarButton.setOnClickListener {
            val directions = MetronomeFragmentDirections.action_metronomeFragment_to_guitarRecordingFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }
    }

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
         * @return A new instance of fragment MetronomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                MetronomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
