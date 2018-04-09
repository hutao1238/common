package com.lbb.common.utils

import android.app.Activity
import android.content.Context.AUDIO_SERVICE
import android.media.AudioManager
import android.media.MediaPlayer
import com.lbb.common.R
import java.io.IOException


/**
 * Created by 胡涛.
 */
object BeepUtil {
    private val BEEP_VOLUME = 0.50f
    private var playBeep = false
    private var mediaPlayer: MediaPlayer? = null

    fun playBeep(mContext: Activity) {
        playBeep = true
        val audioService = mContext.getSystemService(AUDIO_SERVICE) as AudioManager
        if (audioService.ringerMode != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false
        }
        if (playBeep && mediaPlayer != null) {
            mediaPlayer!!.start()
        } else {
            mContext.volumeControlStream = AudioManager.STREAM_MUSIC
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer!!.setOnCompletionListener { mediaPlayer -> mediaPlayer.seekTo(0) }

            val file = mContext.resources.openRawResourceFd(R.raw.beep)
            try {
                mediaPlayer!!.setDataSource(file.fileDescriptor, file.startOffset, file.length)
                file.close()
                mediaPlayer!!.setVolume(BEEP_VOLUME, BEEP_VOLUME)
                mediaPlayer!!.prepare()
            } catch (e: IOException) {
                mediaPlayer = null
            }

        }
    }
}