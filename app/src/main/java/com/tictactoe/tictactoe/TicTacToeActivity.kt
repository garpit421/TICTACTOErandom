package com.tictactoe.tictactoe

import android.content.DialogInterface
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AlertDialog
import android.view.View
import android.media.MediaPlayer
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_tic_tac_toe.*
import java.lang.Exception
import java.util.*

class TicTacToeActivity : AppCompatActivity() {
    private val START_TIME_IN_MILLIS: Long = 5000                   //  set time limit

    private var mTextViewCountDown: TextView? = null               // assign the the value as null of time display

    private var mCountDownTimer: CountDownTimer? = null

    private var mTimerRunning: Boolean = false                     // boolean to show time is running or not

    private var mTimeLeftInMillis = START_TIME_IN_MILLIS

    var selectedImageButton: ImageButton? =null

    var flag=0   // it need to create becouse after complete game by random function
    // the game still work and show alert the winner again and again


    enum class PLAYINGPLAYER{          // playing 2 player
        FIRST_PLAYER,
        SECOND_PLAYER
    }

    enum class WINNER_OF_GAME {          // winning 1,2 aur draw
        PLAYER_ONE,
        PLAYER_TWO,
        NO_ONE
    }

    // Instant variable
    // assign the value of varaible as null
    var playingPlayer: PLAYINGPLAYER? = null
    var winnerofGame: WINNER_OF_GAME? = null

    var player1Options: ArrayList<Int> = ArrayList()                          // creating two array list uses below
    var player2Options: ArrayList<Int> = ArrayList()
    var allDisabledImages: ArrayList<ImageButton?> = ArrayList()             // ? for optional

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tic_tac_toe)

        // for play music

//        val  player = MediaPlayer.create(this, R.raw.dilli )
//        player.isLooping = true
//        player.start()

        playingPlayer = PLAYINGPLAYER.FIRST_PLAYER


        mTextViewCountDown = findViewById(R.id.textView)          // counter display

        updateCountDownText()
    }

    private fun resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS
        mTimerRunning = false
        updateCountDownText()
    }

    private fun updateCountDownText() {
        val minutes = (mTimeLeftInMillis / 1000).toInt() / 60
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60
        val miliseconds = mTimeLeftInMillis.toInt()%60

        val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d",minutes,  seconds)

        mTextViewCountDown?.setText(timeLeftFormatted)
    }

    private fun startTimer() {
        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                mTimerRunning = false
                //random function calling after end timer
                if(playingPlayer == PLAYINGPLAYER.FIRST_PLAYER){
                    randomfirst()
              //      startTimer()
                }else   if (playingPlayer == (PLAYINGPLAYER.SECOND_PLAYER)){
                    randomsecond()
           //         startTimer()
                }
                if (flag==0){
                specifyTheWinnerOfGame()}
                // same apply all the condition for random function
                // whenever the random function complete the game without any
                // update and any winner
                }
        }.start()
        mTimerRunning = true

    }

    fun  imagrButtonTapped(view: View){

        val selectedImageButton: ImageButton = view as ImageButton
        // becouse we know user tap button and imagebutton uses
        var randomNumber = (Math.random()*9+1).toInt()


        // for change color with differnt button
        when (randomNumber){
            1 -> {tableLayout.setBackgroundColor(Color.RED)
                resetTimer()
            startTimer()}
            2 -> {tableLayout.setBackgroundColor(Color.BLUE)
                resetTimer()
                startTimer()}
            3 -> {tableLayout.setBackgroundColor(Color.GREEN)
                resetTimer()
                startTimer()}
            4 -> {tableLayout.setBackgroundColor(Color.YELLOW)
                resetTimer()
                startTimer()}
            5 -> {tableLayout.setBackgroundColor(Color.WHITE)
                resetTimer()
                startTimer()}
            6 -> {tableLayout.setBackgroundColor(Color.GRAY)
                resetTimer()
                startTimer()}
            7 -> {tableLayout.setBackgroundColor(Color.TRANSPARENT)
                resetTimer()
                startTimer()}
            8 -> {tableLayout.setBackgroundColor(Color.CYAN)
                resetTimer()
                startTimer()}
            9 -> {tableLayout.setBackgroundColor(Color.MAGENTA)
                resetTimer()
                startTimer()}
        }
        // to assing value after clicking button
        var optionNumber = 0
        when(selectedImageButton.id){
            R.id.imgButton1 -> optionNumber=1
            R.id.imgButton2 -> optionNumber=2
            R.id.imgButton3 -> optionNumber=3
            R.id.imgButton4 -> optionNumber=4
            R.id.imgButton5 -> optionNumber=5
            R.id.imgButton6 -> optionNumber=6
            R.id.imgButton7 -> optionNumber=7
            R.id.imgButton8 -> optionNumber=8
            R.id.imgButton9 -> optionNumber=9
        }
        // to use input button no and image of button
        action(optionNumber, selectedImageButton)

    }
    // function for action function
    private  fun action(optionNumber: Int, _selectedImageButton: ImageButton){
         selectedImageButton = _selectedImageButton
        if(playingPlayer == PLAYINGPLAYER.FIRST_PLAYER){        // instant variable = enum

            selectedImageButton?.setImageResource(R.drawable.tic)       // change image on clicking button
            player1Options.add(optionNumber)                          // adding position of x in an array
            selectedImageButton?.isEnabled = false                    // it fix the final image of button after that can not change the image o button on clicking
            allDisabledImages.add(selectedImageButton)              // this is another array for that condition when match draw no one wins
            playingPlayer = PLAYINGPLAYER.SECOND_PLAYER            // this for second move done by the compputer after complete first player move
                                                                  // for move first player to another
        }
     else   if (playingPlayer == (PLAYINGPLAYER.SECOND_PLAYER)) {       // main function done by second player

                selectedImageButton?.setImageResource(R.drawable.tac)         //  same as above but done for second
                player2Options.add(optionNumber)                            //  player
                selectedImageButton?.isEnabled = false
                allDisabledImages.add(selectedImageButton)
                playingPlayer = PLAYINGPLAYER.FIRST_PLAYER               // moves to first player again

        }
        specifyTheWinnerOfGame()
    }
     // this function specify the winner
    private fun specifyTheWinnerOfGame() {

         // if 1,,3 all for x then true
         // do all the possible cases for that
         // like 123,456,789,147,258,369,357,159 all this

         //1
        if (player1Options.contains(1)&&player1Options.contains(2)&&player1Options.contains(3)){
            winnerofGame = WINNER_OF_GAME.PLAYER_ONE
        }
         // for second player
         else if (player2Options.contains(1)&&player2Options.contains(2)&&player2Options.contains(3)){
            winnerofGame = WINNER_OF_GAME.PLAYER_TWO
        }
         // this is combination of two player
         // other conditions for this pair

        //2
        else if (player1Options.contains(4)&&player1Options.contains(5)&&player1Options.contains(6)){
            winnerofGame = WINNER_OF_GAME.PLAYER_ONE
        }
        else if (player2Options.contains(4)&&player2Options.contains(5)&&player2Options.contains(6)){
            winnerofGame = WINNER_OF_GAME.PLAYER_TWO
        }

        //3
        else if (player1Options.contains(7)&&player1Options.contains(8)&&player1Options.contains(9)){
            winnerofGame = WINNER_OF_GAME.PLAYER_ONE
        }
        else if (player2Options.contains(7)&&player2Options.contains(8)&&player2Options.contains(9)){
            winnerofGame = WINNER_OF_GAME.PLAYER_TWO
        }

        //4
        else if (player1Options.contains(1)&&player1Options.contains(4)&&player1Options.contains(7)){
            winnerofGame = WINNER_OF_GAME.PLAYER_ONE
        }
        else if (player2Options.contains(1)&&player2Options.contains(4)&&player2Options.contains(7)){
            winnerofGame = WINNER_OF_GAME.PLAYER_TWO
        }

        //5
        else if (player1Options.contains(2)&&player1Options.contains(5)&&player1Options.contains(8)){
            winnerofGame = WINNER_OF_GAME.PLAYER_ONE
        }
        else if (player2Options.contains(2)&&player2Options.contains(5)&&player2Options.contains(8)){
            winnerofGame = WINNER_OF_GAME.PLAYER_TWO
        }

        //6
        else if (player1Options.contains(3)&&player1Options.contains(6)&&player1Options.contains(9)){
            winnerofGame = WINNER_OF_GAME.PLAYER_ONE
        }
        else if (player2Options.contains(3)&&player2Options.contains(6)&&player2Options.contains(9)){
            winnerofGame = WINNER_OF_GAME.PLAYER_TWO
        }

        //7
        else if (player1Options.contains(3)&&player1Options.contains(5)&&player1Options.contains(7)){
            winnerofGame = WINNER_OF_GAME.PLAYER_ONE
        }
        else if (player2Options.contains(3)&&player2Options.contains(5)&&player2Options.contains(7)){
            winnerofGame = WINNER_OF_GAME.PLAYER_TWO
        }

        //8
        else if (player1Options.contains(1)&&player1Options.contains(5)&&player1Options.contains(9)){
            winnerofGame = WINNER_OF_GAME.PLAYER_ONE
        }
        else if (player2Options.contains(1)&&player2Options.contains(5)&&player2Options.contains(9)){
            winnerofGame = WINNER_OF_GAME.PLAYER_TWO
        }

         if(winnerofGame == WINNER_OF_GAME.PLAYER_ONE){
             flag=1
// put button on alert message is button positive
             // and ok is the text on button
             // false is for reset condition
             // not allow user to remove/cancled alert message
             createAlert("Player one Wins","Congratulations to player 1",AlertDialog.BUTTON_POSITIVE,"ok", false)
             return
         }
         else if(winnerofGame == WINNER_OF_GAME.PLAYER_TWO){
// for player two
             flag=1
                 createAlert("Player one Wins","Congratulations to player 2",AlertDialog.BUTTON_POSITIVE,"ok", false)
                 return
             }

         checkDrawState()

    }

    private fun createAlert(title: String, message:  String, whichButton: Int, buttonText: String, cancelable: Boolean) {

      val  alertDialog: AlertDialog = AlertDialog.Builder(this@TicTacToeActivity).create()
        // create alert dialog
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)

        // all this for text of button
        alertDialog.setButton(whichButton,buttonText, {
            dialog: DialogInterface?, which: Int ->

            resetGame()

        })                                           // this is the condition on clicking on alert the game reset
        alertDialog.setCancelable(cancelable)       // for cancle
        alertDialog.show()                         // for visiblity of alert
    }

    private fun resetGame() {

        // clear all the data of array one,two and their images
        // and no one is winner of game
        flag=0
        player1Options.clear()
        player2Options.clear()
        allDisabledImages.clear()
        winnerofGame = WINNER_OF_GAME.NO_ONE

        //restore orignal image of all button
        imgButton1.setImageResource(R.drawable.toe)
        imgButton2.setImageResource(R.drawable.toe)
        imgButton3.setImageResource(R.drawable.toe)
        imgButton4.setImageResource(R.drawable.toe)
        imgButton5.setImageResource(R.drawable.toe)
        imgButton6.setImageResource(R.drawable.toe)
        imgButton7.setImageResource(R.drawable.toe)
        imgButton8.setImageResource(R.drawable.toe)
        imgButton9.setImageResource(R.drawable.toe)

        // allow to image button to change the image
        // allow to intract the button
        imgButton1.isEnabled = true
        imgButton2.isEnabled = true
        imgButton3.isEnabled = true
        imgButton4.isEnabled = true
        imgButton5.isEnabled = true
        imgButton6.isEnabled = true
        imgButton7.isEnabled = true
        imgButton8.isEnabled = true
        imgButton9.isEnabled = true

    }

    private fun checkDrawState() {

        if(allDisabledImages.size == 9 && winnerofGame != WINNER_OF_GAME.PLAYER_ONE && winnerofGame != WINNER_OF_GAME.PLAYER_TWO)

            createAlert("Draw!!!","No on Won The Game", AlertDialog.BUTTON_POSITIVE,"OK",false)
        // reset for all images fill but no one win0
    }

    private fun randomfirst(){
        var notSelectedImageNumber = ArrayList<Int>()
        for (imageNumber in 1..9) {
            if (!(player1Options.contains(imageNumber))) {
                if (!player2Options.contains(imageNumber)) {
                    // notSelectedImageNumbers is created in order to hold
                    // the image number of the image buttons that are not selectted
                    notSelectedImageNumber.add(imageNumber)
                }
            }
        }
        try {
            // to pass value by randon on remaining images
            var randomNumber = ((Math.random() * notSelectedImageNumber.size)).toInt()
            var imageNumber = notSelectedImageNumber[randomNumber]
            when (imageNumber) {
                1 -> selectedImageButton = imgButton1
                2 -> selectedImageButton = imgButton2
                3 -> selectedImageButton = imgButton3
                4 -> selectedImageButton = imgButton4
                5 -> selectedImageButton = imgButton5
                6 -> selectedImageButton = imgButton6
                7 -> selectedImageButton = imgButton7
                8 -> selectedImageButton = imgButton8
                9 -> selectedImageButton = imgButton9
            }
            selectedImageButton?.setImageResource(R.drawable.tic)    // add omage for second player
            player1Options.add(imageNumber)                        // add position of image in array
            selectedImageButton?.isEnabled = false                 // not allow to change the image furthur
            allDisabledImages.add(selectedImageButton)
            playingPlayer = PLAYINGPLAYER.SECOND_PLAYER           // for moves to player one
        } catch (e: Exception) {
            e.printStackTrace()
        }
        resetTimer()
        startTimer()
    }

    private  fun randomsecond(){

        var notSelectedImageNumber = ArrayList<Int>()
        for (imageNumber in 1..9) {
            if (!(player1Options.contains(imageNumber))) {
                if (!player2Options.contains(imageNumber)) {
                    // notSelectedImageNumbers is created in order to hold
                    // the image number of the image buttons that are not selectted
                    notSelectedImageNumber.add(imageNumber)
                }
            }
        }
        try {
            // to pass value by randon on remaining images
            var randomNumber = ((Math.random() * notSelectedImageNumber.size)).toInt()
            var imageNumber = notSelectedImageNumber[randomNumber]
            when (imageNumber) {
                1 -> selectedImageButton = imgButton1
                2 -> selectedImageButton = imgButton2
                3 -> selectedImageButton = imgButton3
                4 -> selectedImageButton = imgButton4
                5 -> selectedImageButton = imgButton5
                6 -> selectedImageButton = imgButton6
                7 -> selectedImageButton = imgButton7
                8 -> selectedImageButton = imgButton8
                9 -> selectedImageButton = imgButton9
            }
            selectedImageButton?.setImageResource(R.drawable.tac)    // add omage for second player
            player2Options.add(imageNumber)                        // add position of image in array
            selectedImageButton?.isEnabled = false                 // not allow to change the image furthur
            allDisabledImages.add(selectedImageButton)
            playingPlayer = PLAYINGPLAYER.FIRST_PLAYER           // for moves to player one
        } catch (e: Exception) {
            e.printStackTrace()
        }
        resetTimer()
        startTimer()
    }
}
