package com.example.skyler.guitarapp

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_drum.*
import kotlinx.android.synthetic.main.fragment_recorder.*
import kotlinx.android.synthetic.main.fragment_bottom_nav_bar.*
import java.util.*
import android.preference.PreferenceManager
import com.google.gson.Gson


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

    //region Globals
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    //mediaPlayer variables
    var soundMap = hashMapOf(1 to R.raw.newjr_16, 2 to R.raw.newjr_13, 3 to R.raw.rimshot,
                             4 to R.raw.newjr_19, 5 to R.raw.newjr_16, 6 to R.raw.mc_snare_4b,
                             7 to R.raw.newjr_16, 8 to R.raw.newjr_16)

    var newSoundMap = hashMapOf(1 to R.raw.crash, 2 to R.raw.tom, 3 to R.raw.rim,
                                4 to R.raw.clap, 5 to R.raw.kick, 6 to R.raw.snare,
                                7 to R.raw.o_hat, 8 to R.raw.c_hat)

    //object representing a recorded beat, contains list of drums pressed and list of delays
    var recordedBeat = com.example.skyler.guitarapp.RecordedBeat()

    //UI state variables
    var buttonMap : Map<String, Button> = hashMapOf()
    //endregion globals

    //region Life-Cycle Methods
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

        this.buttonMap = hashMapOf(ClickUtils.getPlay() to playButton,
         ClickUtils.getRecord() to recordButton,
         ClickUtils.getStop() to stopButton)

        //region Recoring Bar Code
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
            var index = 0

            //check if pressing the button set the state to playing, or not playing
            if(ClickUtils.isPlaying()){
                //play the stuff I have recorded
                //player.playNext(0, recordedBeat)
                playNext(index, recordedBeat)
            }
        }

        saveButton.setOnClickListener {
            saveRecording()
        }
        //endregion

        //region Drum Button Listeners
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
        //endregion

        //region Bottom Nav Bar Listeners
        playbackButton.setOnClickListener{
            val directions = DrumFragmentDirections.action_drumFragment_to_drumPlayBackFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }

        metronomeButton.setOnClickListener{
            val directions = DrumFragmentDirections.action_drumFragment_to_metronomeFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }

        musicButton.setOnClickListener {
            val directions = DrumFragmentDirections.action_drumFragment_to_beatEditingFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }

        guitarButton.setOnClickListener {
            val directions = DrumFragmentDirections.action_drumFragment_to_guitarRecordingFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }
        //endregion
    }
    //endregion

    //region Beat Management Methods
    //play the next sound in the list of recorded sounds
    fun playNext(index: Int, recordedBeat: RecordedBeat)
    {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val mediaPlayer = MediaPlayer.create(context, newSoundMap[recordedBeat.getSound(index)] ?: 0)
                mediaPlayer?.start()
                mediaPlayer.setOnCompletionListener { mediaPlayer.release() }
                if (recordedBeat.getSize() > index + 1) {
                    playNext(index + 1, recordedBeat)
                }
                else {
                    Looper.prepare()
                    Handler().post({ClickUtils.clickPlay(buttonMap)})
                    Looper.loop()
                }
            }
        }, recordedBeat.getDelay(index))
    }

    private fun saveRecording() {
        //get shared preferences editor
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor = prefs.edit()

        //see how many recordings we have, this is used in naming recorded objects
        var numRecordings = prefs.getInt("numRecordings", 0)

        //convert recordedBeat to json
        val gson = Gson()
        val serializedObject = gson.toJson(DrumPlaybackItemModel("recording" + numRecordings,
                gson.toJson(recordedBeat)))

        //store json in sharedPreferences
        prefsEditor.putString("recording" + numRecordings, serializedObject)

        numRecordings++
        prefsEditor.putInt("numRecordings", numRecordings)

        prefsEditor.apply()
    }

    //play drum sound and add to list of recorded sounds if we are recording
    private fun onDrumButtonPressed(soundNumber: Int) {

        //make mediaPlayer in a different thread
        Thread {
            val mediaPlayer = MediaPlayer.create(context, newSoundMap.get(soundNumber) ?: 0)
            mediaPlayer?.start()
            mediaPlayer.setOnCompletionListener { mediaPlayer.release() }

            //keep track of what was pressed if we are recording
            if(ClickUtils.isRecording()) {
                recordedBeat.addBeat(soundNumber)
            }
        }.start()
    }
    //endregion

    //region Boilerplate Override Methods
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
    //endregion
}
