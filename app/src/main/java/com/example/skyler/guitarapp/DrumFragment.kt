package com.example.skyler.guitarapp

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_drum.*
import kotlinx.android.synthetic.main.fragment_recorder.*
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DrumFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DrumFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DrumFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var mediaPlayer: MediaPlayer? = null
    var drumRecordingList: MutableList<Int> = mutableListOf()
    var recording = false
    var soundMap = hashMapOf(1 to R.raw.newjr_16, 2 to R.raw.newjr_13, 3 to R.raw.emt_rimshot,
                             4 to R.raw.newjr_19, 5 to R.raw.newjr_16, 6 to R.raw.mc_snare_4b,
                             7 to R.raw.newjr_16, 8 to R.raw.newjr_16)
    var timer : Timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        mediaPlayer = MediaPlayer.create(context, R.raw.newjr_16)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drum, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        print("****inonviewcreated****")

        //code for recorder fragment
        val buttonMap : Map<String, Button> = hashMapOf(ClickUtils.getPlay() to playButton,
         ClickUtils.getRecord() to recordButton,
         ClickUtils.getStop() to stopButton)

        recordButton.setOnClickListener {
            ClickUtils.clickRecord(buttonMap)
            toggleRecording()
        }

        stopButton.setOnClickListener {
            ClickUtils.clickStop(buttonMap)
            setRecordingFalse()
        }

        playButton.setOnClickListener {
            ClickUtils.clickPlay(buttonMap)

            //playRecordedBeat()
            playNext(0)
            //playback the current recorded list of drum beats
            for(drum in drumRecordingList) {
                print("*****")
                print(drum)
            }
        }

        /**
         * When a drum button is pressed, it is added to a list. Drum playback will
         * work by simply playing back the drums you pressed in the same order. The problem that
         * this will cause is all the sounds being played over each-other or robotically at evenly
         * spaced intervals. I need to keep track of the small time gaps between button presses and
         * find a way to insert those gaps during drum playback.
         */
        //code for each of the drum buttons
        crashButton.setOnClickListener {
            onDrumButtonPressed(1)
        }
        tomButton.setOnClickListener {
            onDrumButtonPressed(2)
        }
        rimButton.setOnClickListener {
            onDrumButtonPressed(3)
        }
        clapButton.setOnClickListener {
            onDrumButtonPressed(4)
        }
        kickButton.setOnClickListener {
            onDrumButtonPressed(5)
        }
        snareButton.setOnClickListener {
            onDrumButtonPressed(6)
        }
        oHatButton.setOnClickListener {
            onDrumButtonPressed(7)
        }
        cHatButton.setOnClickListener {
            onDrumButtonPressed(8)
        }
    }

    fun playRecordedBeat() {
        var delay : Long = 500


    }

    fun playNext(index: Int) {
        timer.schedule(object : TimerTask() {
            override fun run() {
                //mp.reset
                mediaPlayer?.release()
                mediaPlayer = MediaPlayer.create(context, soundMap.get(drumRecordingList.get(index)) ?: 0)
                mediaPlayer?.start()
                if (drumRecordingList.size > index + 1) {
                    playNext(index + 1)
                }
            }
        }, 1000)
    }

    //TODO: create a hashmap that maps numbers 1-6 inclusive to a sound uri. Use the number
    // to retrieve the uri that is supposed to be played from the map. This will allow easy changing of
    // sound sets by simply changing the hashmap
    fun onDrumButtonPressed(soundNumber: Int) {

        //make mediaPlayer in a different thread
        Thread {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(context, soundMap.get(soundNumber) ?: 0)
            mediaPlayer?.start()

            //keep track of what was pressed if we are recording
            if(recording) {
                drumRecordingList.add(soundNumber)
            }
        }.start()
    }

    fun toggleRecording() {
        if(this.recording) {
            setRecordingFalse()
        }
        else {
            setRecordingTrue()
        }
    }

    fun setRecordingFalse() {
        this.recording = false
    }

    fun setRecordingTrue() {
        this.recording = true
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
         * @return A new instance of fragment DrumFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                DrumFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
