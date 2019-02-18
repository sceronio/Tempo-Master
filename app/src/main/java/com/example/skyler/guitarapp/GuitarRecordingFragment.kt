package com.example.skyler.guitarapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_bottom_nav_bar.*
import kotlinx.android.synthetic.main.fragment_recorder.*
import java.io.File
import java.io.FileOutputStream

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DrumPlayBackFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DrumPlayBackFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GuitarRecordingFragment : Fragment() {

    //region Globals
    private var listener: OnFragmentInteractionListener? = null
    private var mMediaRecorder: MediaRecorder? = null
    private var mPlayer: MediaPlayer? = null
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200

    private val mFileName = "audiorecordtest.3gp"

    var file: File? = null
    val filename = "myfile"
    val fileContents = "!!!!!!!!!!!!!!!!!!!!!!Hello world!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
    //endregion

    //region Life-Cycle Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissions(permissions, REQUEST_RECORD_AUDIO_PERMISSION)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_guitar_recording, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonMap: Map<String, Button> = hashMapOf(ClickUtils.getPlay() to playButton,
                ClickUtils.getRecord() to recordButton,
                ClickUtils.getStop() to stopButton)

        //region Recording Bar Code
        recordButton.setOnClickListener {
            if (ClickUtils.isRecording()) {
                stopRecording()
            } else {
                startRecording()
            }
            ClickUtils.clickRecord(buttonMap)
        }

        stopButton.setOnClickListener {
            if (ClickUtils.isRecording()) {
                stopRecording()
            } else if (ClickUtils.isPlaying()) {
                stopPlaying()
            }
            ClickUtils.clickStop(buttonMap)
        }

        playButton.setOnClickListener {
            startPlaying()
            ClickUtils.clickPlay(buttonMap)
        }

        saveButton.setOnClickListener {

        }
        //endregion

        //region Navbar Code
        drumButton.setOnClickListener {
            val directions = GuitarRecordingFragmentDirections.action_guitarRecordingFragment_to_drumFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }

        metronomeButton.setOnClickListener {
            val directions = GuitarRecordingFragmentDirections.action_guitarRecordingFragment_to_metronomeFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }

        playbackButton.setOnClickListener {
            val directions = GuitarRecordingFragmentDirections.action_guitarRecordingFragment_to_drumPlayBackFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }

        musicButton.setOnClickListener {
            val directions = GuitarRecordingFragmentDirections.action_guitarRecordingFragment_to_beatEditingFragment()
            NavHostFragment.findNavController(this).navigate(directions)
        }
        //endregion
    }
    //endregion

    //region Recording Management Code
    private fun startRecording() {
        Thread(Runnable {
            //this gives you a reference to the file system
            val path: File = context!!.filesDir
            //this gives you a reference to the file you want to write to in the file system
            val actualFile = File(path, mFileName)
            //this opens a file output stream to the file you want to write to
            val fileStream = FileOutputStream(actualFile)
            //this gives you a file descriptor for that file output stream
            val fd = fileStream.fd

            //prepare media recorder and start recording
            mMediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setOutputFile(fd)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                prepare()
                start()
            }
        }).start()
    }

    private fun stopRecording() {
        mMediaRecorder?.apply {
            stop()
            release()
        }
        mMediaRecorder = null
    }

    private fun startPlaying() {
        Thread(Runnable {
            val fileDescriptor = context!!.openFileInput(mFileName).fd
            mPlayer = MediaPlayer().apply {
                setDataSource(fileDescriptor)
                prepare()
                start()
            }
        }).start()
    }

    private fun stopPlaying() {
        mPlayer?.release()
        mPlayer = null
    }
    //endregion

    //region Permissions Code
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        //if (!permissionToRecordAccepted) finish()
    }
    //endregion

    //region Boilerplater override code
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

    //this interface must be implemented by main activity
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                DrumPlayBackFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
    //endregion
}
