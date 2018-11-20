package com.example.skyler.guitarapp

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_drum.*
import kotlinx.android.synthetic.main.fragment_recorder.*
import kotlinx.android.synthetic.main.fragment_bottom_nav_bar.*
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

    //mediaPlayer variables
    var soundMap = hashMapOf(1 to R.raw.newjr_16, 2 to R.raw.newjr_13, 3 to R.raw.emt_rimshot,
                             4 to R.raw.newjr_19, 5 to R.raw.newjr_16, 6 to R.raw.mc_snare_4b,
                             7 to R.raw.newjr_16, 8 to R.raw.newjr_16)

    //object representing a recorded beat, contains list of drums pressed and list of delays
    var recordedBeat = com.example.skyler.guitarapp.RecordedBeat()

    //UI state variables
    var buttonMap : Map<String, Button> = hashMapOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drum, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //code for recorder fragment buttons
        this.buttonMap = hashMapOf(ClickUtils.getPlay() to playButton,
         ClickUtils.getRecord() to recordButton,
         ClickUtils.getStop() to stopButton)

        recordButton.setOnClickListener {
            //reset the variables that keep track of the state of the currently recorded beat
            recordedBeat.reset()
            ClickUtils.clickRecord(buttonMap)
        }

        stopButton.setOnClickListener {
            ClickUtils.clickStop(buttonMap)
        }

        playButton.setOnClickListener {
            ClickUtils.clickPlay(buttonMap)

            //check if pressing the button set the state to playing, or not playing
            if(ClickUtils.isPlaying()){
                //play the stuff I have recorded
                playNext(0)
            }
        }

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

        //navbar code
        drumButton.setOnClickListener{
            /*val directions = ContentMainFragmentDirections.action_contentMainFragment_to_metronomeFragment()
            NavHostFragment.findNavController(this).navigate(directions)*/
        }

    }

    //play the next sound in the list of recorded sounds
    fun playNext(index: Int) {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                var mediaPlayer = MediaPlayer.create(context, soundMap[recordedBeat.getSound(index)] ?: 0)
                mediaPlayer?.start()
                mediaPlayer.setOnCompletionListener { mediaPlayer.release() }
                if (recordedBeat.getSize() > index + 1) {
                    playNext(index + 1)
                }
                else {
                    Looper.prepare()
                    Handler().post({ClickUtils.clickPlay(buttonMap)})
                    Looper.loop()
                }
            }
        }, recordedBeat.getDelay(index))
    }

    //play drum sound and add to list of recorded sounds if we are recording
    fun onDrumButtonPressed(soundNumber: Int) {

        //make mediaPlayer in a different thread
        Thread {
            var mediaPlayer = MediaPlayer.create(context, soundMap.get(soundNumber) ?: 0)
            mediaPlayer?.start()
            mediaPlayer.setOnCompletionListener { mediaPlayer.release() }

            //keep track of what was pressed if we are recording
            if(ClickUtils.isRecording()) {
                recordedBeat.addBeat(soundNumber)
            }
        }.start()
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
}
