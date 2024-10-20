package com.example.breath

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.breath.R

class MainActivity : AppCompatActivity() {

    private lateinit var breathInInput: EditText
    private lateinit var holdInput: EditText
    private lateinit var breathOutInput: EditText
    private lateinit var secondHoldInput: EditText
    private lateinit var countdownTimer: TextView
    private lateinit var currentPhaseLabel: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var breathTitle: TextView


    private lateinit var circularProgressView: CircularProgressView

    private var breathInTime: Int = 0
    private var holdTime: Int = 0
    private var breathOutTime: Int = 0
    private var secondHoldTime: Int = 0

    private var times = intArrayOf(0, 0, 0, 0)
    private val phases = arrayOf("Breathe In", "Hold", "Breathe Out", "Hold")
    private var currentPhase = 0

    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        breathInInput = findViewById(R.id.breathInTime)
        holdInput = findViewById(R.id.holdTime)
        breathOutInput = findViewById(R.id.breathOutTime)
        secondHoldInput = findViewById(R.id.secondHoldTime)
        countdownTimer = findViewById(R.id.countdownTimer)
        currentPhaseLabel = findViewById(R.id.currentPhaseLabel)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)
        circularProgressView = findViewById(R.id.circularProgressView)
        breathTitle = findViewById(R.id.breathTitle)

        // Start button click listener
        startButton.setOnClickListener {
            startBreathingCycle()
        }

        // Stop button click listener
        stopButton.setOnClickListener {
            stopBreathingCycle()
        }
    }

    private fun startBreathingCycle() {
        // Get values from input fields
        breathInTime = breathInInput.text.toString().toIntOrNull() ?: 0
        holdTime = holdInput.text.toString().toIntOrNull() ?: 0
        breathOutTime = breathOutInput.text.toString().toIntOrNull() ?: 0
        secondHoldTime = secondHoldInput.text.toString().toIntOrNull() ?: 0

        // Store the times in an array (converted to milliseconds)
        times = intArrayOf(breathInTime * 1000, holdTime * 1000, breathOutTime * 1000, secondHoldTime * 1000)

        // Hide input fields and start button
        breathInInput.visibility = View.GONE
        holdInput.visibility = View.GONE
        breathOutInput.visibility = View.GONE
        secondHoldInput.visibility = View.GONE
        startButton.visibility = View.GONE
        stopButton.visibility = View.VISIBLE // Show stop button
        circularProgressView.visibility = View.VISIBLE
        currentPhaseLabel.visibility = View.VISIBLE
        countdownTimer.visibility = View.VISIBLE
        breathTitle.visibility = View.GONE

        // Start with the first phase
        currentPhase = 0
        startPhase()
    }

    private fun startPhase() {
        // Display the current phase name
        currentPhaseLabel.text = phases[currentPhase]

        // Create a CountDownTimer for the current phase
        timer = object : CountDownTimer(times[currentPhase].toLong(), 100) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = (1 - millisUntilFinished / times[currentPhase].toFloat()) * 100
                countdownTimer.text = (millisUntilFinished / 1000).toString()
                circularProgressView.setProgress(progress)
            }

            override fun onFinish() {
                // Move to the next phase (cycle through 4 phases)
                currentPhase = (currentPhase + 1) % 4
                startPhase()
            }
        }.start()
    }

    private fun stopBreathingCycle() {
        // Cancel the current timer
        timer?.cancel()

        // Reset the progress and labels
        circularProgressView.setProgress(0f)
        countdownTimer.text = "0"
        currentPhaseLabel.text = "Ready"

        // Show input fields and start button again
        breathInInput.visibility = View.VISIBLE
        holdInput.visibility = View.VISIBLE
        breathOutInput.visibility = View.VISIBLE
        secondHoldInput.visibility = View.VISIBLE
        startButton.visibility = View.VISIBLE
        stopButton.visibility = View.GONE // Hide stop button
        circularProgressView.visibility = View.INVISIBLE
        currentPhaseLabel.visibility = View.GONE
        countdownTimer.visibility = View.GONE
        breathTitle.visibility = View.VISIBLE

    }

    override fun onDestroy() {
        // Cancel the timer if the activity is destroyed
        timer?.cancel()
        super.onDestroy()
    }
}
