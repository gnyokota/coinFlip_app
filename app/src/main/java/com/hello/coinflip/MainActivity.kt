package com.hello.coinflip

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hello.coinflip.databinding.ActivityMainBinding
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var heads = 0
    private var tails = 0
    private var total = 0

    var coinStatus = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivity = this

        binding.mainActivitySwSimulate.setOnCheckedChangeListener { _, isChecked -> enableSimulation(isChecked) }
        binding.mainActivityBtFlip.setOnClickListener { flip() }
        binding.mainActivityBtReset.setOnClickListener { reset() }
        binding.mainActivityBtSimulate.setOnClickListener { simulation() }
    }

    private fun enableSimulation(onState: Boolean) {
        if(onState){
            Log.i("test","Sim is on")
            binding.mainActivityEtSimulate.visibility = View.VISIBLE
            binding.mainActivityBtSimulate.visibility = View.VISIBLE
        }else{
            Log.i("test","Sim is off")
            binding.mainActivityEtSimulate.visibility = View.INVISIBLE
            binding.mainActivityBtSimulate.visibility = View.INVISIBLE
        }
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
            coinStatus = "You fliped a tail"
            binding.mainActivityIvThumbs.setImageResource(R.drawable.ic_tail_icon)

        } else {
            heads++
            coinStatus = "You fliped a head"
            binding.mainActivityIvThumbs.setImageResource(R.drawable.ic_head_icon)
        }
        //invalidate binding expressions
        binding.apply { invalidateAll() }
        total++
        binding.mainActiityTvTotalFlips.text = "Total Flips: $total"
        binding.mainActivityTvTotalHeads.text = "Total Heads: $heads"
        binding.mainActivityTvTotalTails.text = "Total Tails: $tails"
        updateProgress()
    }

    private fun updateProgress() {
        val tailsPercentValue = round((tails.toDouble() / total) * 10000)/100
        binding.mainActivityPbTails.progress = tailsPercentValue.toInt()
        binding.mainActivityTailsStats.text = "Tails: $tailsPercentValue%"
        val headsPercentValue = round((heads.toDouble() / total) * 10000)/100
        binding.mainActivityPbHeads.progress = headsPercentValue.toInt()
        binding.mainActivityHeadsStats.text = "Tails: $headsPercentValue%"
    }

    private fun reset() {
       binding.mainActivityIvThumbs.setImageResource(R.drawable.ic_thumb_icon)
        heads = 0
        tails = 0
        total = 0
        binding.mainActiityTvTotalFlips.setText("Total Flips: $total")
        binding.mainActivityTvTotalHeads.setText("Total Heads: $heads")
        binding.mainActivityTvTotalTails.setText("Total Tails: $tails")
        binding.mainActivityPbHeads.progress = 0
        binding.mainActivityHeadsStats.text = "Heads: 0%"
        binding.mainActivityPbTails.progress = 0
        binding.mainActivityTailsStats.text = "Tails: 0%"
        coinStatus = "Press to flip"
        //invalidate binding expressions
        binding.apply { invalidateAll() }
    }

    private fun simulation() {
        var numberOfFlips = 1
        if(!binding.mainActivityEtSimulate.text.toString().isBlank()) {
            numberOfFlips = binding.mainActivityEtSimulate.text.toString().toInt()
        }
        binding.mainActivityEtSimulate.setText("")
        hideKeyboard()
        for(i in 1..numberOfFlips){
            flip()
        }
    }

    private fun hideKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.mainActivityIvThumbs.windowToken,0)
    }
}