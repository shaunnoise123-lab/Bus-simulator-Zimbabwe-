package com.bussimulatorzim.game.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack

class SoundEngine {
    private val audioTrack: AudioTrack
    private var isPlaying = false
    private val audioBuffer = ShortArray(44100 / 10)
    
    init {
        audioTrack = AudioTrack(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build(),
            AudioFormat.Builder()
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setSampleRate(44100)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .build(),
            audioBuffer.size * 2,
            AudioTrack.MODE_STREAM
        )
    }
    
    fun playEngineSound(rpm: Float, speed: Float) {
        val frequency = 100f + (rpm / 10000f) * 500f
        generateTone(frequency, 0.5f)
    }
    
    fun playHornSound(hornType: String) {
        when (hornType) {
            "daf_vintage" -> generateTone(300f, 0.8f)
            "dual_air" -> {
                generateTone(400f, 0.6f)
                generateTone(600f, 0.6f)
            }
            "electric" -> generateTone(800f, 0.5f)
            else -> generateTone(500f, 0.7f)
        }
    }
    
    fun playDoorSound() {
        generateTone(200f, 0.4f)
    }
    
    fun playBrakeSound() {
        generateTone(150f, 0.6f)
    }
    
    fun playTireSound(skidIntensity: Float) {
        val frequency = 100f + skidIntensity * 300f
        generateTone(frequency, 0.7f)
    }
    
    fun playCollisionSound(force: Float) {
        val frequency = (force / 1000f) * 500f
        generateTone(frequency.coerceIn(100f, 800f), 0.9f)
    }
    
    fun playAmbientSound(soundType: String) {
        when (soundType) {
            "wind" -> generateTone(80f, 0.3f)
            "rain" -> generateTone(200f, 0.4f)
            "traffic" -> generateTone(150f, 0.3f)
            else -> {}
        }
    }
    
    private fun generateTone(frequency: Float, amplitude: Float) {
        val sampleRate = 44100
        for (i in audioBuffer.indices) {
            val sample = (amplitude * 32767 * kotlin.math.sin(2.0 * Math.PI * frequency * i / sampleRate)).toShort()
            audioBuffer[i] = sample
        }
        
        if (!isPlaying) {
            audioTrack.play()
            isPlaying = true
        }
        audioTrack.write(audioBuffer, 0, audioBuffer.size)
    }
    
    fun stop() {
        audioTrack.stop()
        isPlaying = false
    }
    
    fun release() {
        audioTrack.release()
    }
}
