package com.example.dafttapchallenge

import android.animation.AnimatorInflater
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_game.*
import java.text.SimpleDateFormat
import java.util.*

class GameActivity : AppCompatActivity() {

    private var score : Int = 0
    private var daisTreshold: Int = 0
    private lateinit var gameScoreTextView: TextView
    private lateinit var countDownTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    private lateinit var gameCountDownTimer: CountDownTimer
    private lateinit var countDownTimer: CountDownTimer
    private val initialGameCountDown: Long = 5000
    private val initialCountDownTime: Long = 4000
    internal var timeLeftCountDown : Long = 0
    internal var timeLeftGameCountDown : Long = 5
    private var initialGameTimeLeft = initialGameCountDown / 1000
    internal val countDownInterval: Long = 1000
    private var gameStarted = false
    internal var countDownDone = false

    companion object {
        private const val SCORE = "SCORE"
        private const val COUNTDOWNTIMELEFT = "COUNTDOWNTIMELEFT"
        private const val GAMETIMELEFT = "GAMETIMELEFT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        gameScoreTextView = findViewById(R.id.score)
        timeLeftTextView = findViewById(R.id.timeleft)
        countDownTextView = findViewById(R.id.countDownText)

        daisTreshold = intent.getIntExtra("DAISTHRESHOLD",0)

        if(savedInstanceState != null) {
            score = savedInstanceState.getInt(SCORE)
            timeLeftCountDown = savedInstanceState.getLong(COUNTDOWNTIMELEFT) * 1000
            timeLeftGameCountDown = savedInstanceState.getLong(GAMETIMELEFT) * 1000
            countDown(timeLeftCountDown,timeLeftGameCountDown)
        }else {
            countDown(initialCountDownTime,initialGameCountDown)
        }

        activityGame.setOnClickListener() {
            incrementScore()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCORE,score)
        outState.putLong(COUNTDOWNTIMELEFT,timeLeftCountDown)
        outState.putLong(GAMETIMELEFT,timeLeftGameCountDown)
        countDownTimer.cancel()
        if(countDownDone)
            gameCountDownTimer.cancel()
    }

    private fun countDown(initialCountDownTime: Long,initialGameCountDown: Long){
        if(countDownDone){
        countDownTextView.text = getString(R.string.countdown, initialCountDownTime.toString())}
        timeLeftTextView.text = getString(R.string.time_left,initialGameTimeLeft.toString())
        gameScoreTextView.text = getString(R.string.score, score.toString())

        countDownTimer = object: CountDownTimer(initialCountDownTime,countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftCountDown = millisUntilFinished / 1000
                if (!countDownDone) {
                    when (timeLeftCountDown) {
                        3L, 2L, 1L -> countDownTextView.text =
                            getString(R.string.countdown, timeLeftCountDown.toString())
                        0L -> {
                            countDownTextView.text = getString(R.string.countdown, "PLAY")
                            fadeZoomOut(countDownTextView)
                        }
                    }
                }
            }
            override fun onFinish() {
                countDownDone = true
                timeLeftCountDown = 0L
                if(countDownDone)
                    startGame(initialGameCountDown)
            }
        }
        countDownTimer.start()
    }

    private fun startGame(initialGameCountDown: Long){
        val initialTimeLeft =  initialGameCountDown / 1000
        timeLeftTextView.text = getString(R.string.time_left,initialTimeLeft.toString())

        gameCountDownTimer = object: CountDownTimer( initialGameCountDown, countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftGameCountDown = millisUntilFinished / 1000
                timeLeftTextView.text = getString(R.string.time_left, timeLeftGameCountDown.toString())
            }

            override fun onFinish() {
                endGame()
            }
        }
        gameStarted = true
        gameCountDownTimer.start()
    }

    private fun endGame(){
         gameStarted = false
         createDialog()
    }

    private fun incrementScore() {
        if( gameStarted){
            score += 1
            val newScore = getString(R.string.score,  score.toString())
            gameScoreTextView.text = newScore
        }
    }

    private fun createDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.dialogTitle)
        builder.setCancelable(false)
        builder.setPositiveButton(R.string.dialogPositiveButton,{ dialogInterface: DialogInterface, i: Int ->
            val intent = Intent()
            intent.putExtra("SCORE", score)
            setResult(Activity.RESULT_OK,intent);
            finish()
        })
        when(daisTreshold >= score){
            true -> builder.setMessage(getString(R.string.dialogMessage, score.toString()))
            false -> builder.setMessage(getString(R.string.dialogTopFiveMessage, score.toString()))
        }
        builder.show()
    }

    private fun fadeZoomOut(view : View){
        val animator = AnimatorInflater.loadAnimator(this,R.animator.fade_zoom_out)
        animator.setTarget(view)
        animator.start()
    }
}
