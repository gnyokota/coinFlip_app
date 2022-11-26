package com.hello.coinflip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    private lateinit var coinImage: ImageView
    private lateinit var simSwitch: SwitchCompat
    private lateinit var flipBtn: Button
    private lateinit var resetBtn: Button
    private lateinit var totalFlips: TextView
    private lateinit var totalHeads: TextView
    private lateinit var totalTails: TextView
    private lateinit var headsPercent: TextView
    private lateinit var headsProgressBar: ProgressBar
    private lateinit var tailsPercent: TextView
    private lateinit var tailsProgressBar: ProgressBar
    private lateinit var simulationText: EditText
    private lateinit var simulationBtn: Button

    private var heads = 0
    private var tails = 0
    private var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coinImage = findViewById(R.id.main_activity_iv_thumbs)
        simSwitch = findViewById(R.id.main_activity_sw_simulate)
        flipBtn = findViewById(R.id.main_activity_bt_flip)
        resetBtn = findViewById(R.id.main_activity_bt_reset)
        totalFlips = findViewById(R.id.main_actiity_tv_total_flips)
        totalHeads = findViewById(R.id.main_activity_tv_total_heads)
        totalTails = findViewById(R.id.main_activity_tv_total_tails)
        headsPercent = findViewById(R.id.main_activity_heads_stats)
        headsProgressBar = findViewById(R.id.main_activity_pb_heads)
        tailsPercent = findViewById(R.id.main_activity_tails_stats)
        tailsProgressBar = findViewById(R.id.main_activity_pb_tails)
        simulationText = findViewById(R.id.main_activity_et_simulate)
        simulationBtn = findViewById(R.id.main_activity_bt_simulate)

        simSwitch.setOnCheckedChangeListener { _, isChecked -> enableSimulation(isChecked) }
        flipBtn.setOnClickListener { flip() }
        resetBtn.setOnClickListener { reset() }
        simulationBtn.setOnClickListener { simulation() }
    }

    private fun enableSimulation(onState: Boolean) {

    }

    private fun flip() {
        val isTails = (0..1).random()
        when (isTails) {
            0 -> update(false)
            1 -> update(true)
        }
    }

    private fun update(isTails: Boolean) {
        if (isTails) {
            tails++
            coinImage.setImageResource(R.drawable.ic_tail_icon)

        } else {
            heads++
            coinImage.setImageResource(R.drawable.ic_head_icon)
        }
        total++
        totalFlips.setText("Total Flips: $total")
        totalHeads.setText("Total Heads: $heads")
        totalTails.setText("Total Tails: $tails")
        updateProgress(isTails)
    }

    private fun updateProgress(isTails: Boolean) {
        if (isTails) {
            val tailsPercentValue = round((tails.toDouble() / total.toDouble()) * 100)
            tailsProgressBar.progress = tailsPercentValue.toInt()
            tailsPercent.text = "Tails: $tailsPercentValue%"

        } else {
            val headsPercentValue = round((heads.toDouble() / total.toDouble()) * 100)
            headsProgressBar.progress = headsPercentValue.toInt()
            headsPercent.text = "Tails: $headsPercentValue%"
        }
    }

    private fun reset() {

    }

    private fun simulation() {

    }
}