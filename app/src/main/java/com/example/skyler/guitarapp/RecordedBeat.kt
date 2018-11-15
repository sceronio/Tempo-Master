package com.example.skyler.guitarapp

class RecordedBeat {

    var previousTime : Long = 0L
    var currentTime : Long = 0L
    var delayList = mutableListOf<Long>(0)
    var drumRecordingList = mutableListOf<Int>()

    fun getDelay(index : Int) : Long {
        return delayList[index]
    }

    fun getSound(index : Int) : Int {
        return drumRecordingList[index]
    }

    fun addBeat(soundNumber : Int) {
        drumRecordingList.add(soundNumber)
        addDelay()
    }

    fun getSize() : Int {
        return drumRecordingList.size
    }

    fun addDelay() {
        if(previousTime == 0L) {
            previousTime = System.currentTimeMillis()
        }
        else {
            currentTime = System.currentTimeMillis()

            //subtract 100 from the delay because the method calls are not fast enough
            // for the delays to sound totally correct
            var delay = currentTime - previousTime - 100L
            if(delay < 0) {
                delay = 0
            }
            delayList.add(delay)

            previousTime = currentTime
        }
    }

    fun reset() {
        //reset list of recorded sounds
        drumRecordingList = mutableListOf()
        //reset the list of delays
        delayList = mutableListOf(0)
        //reset previous time when recording again
        previousTime = 0L
    }

}