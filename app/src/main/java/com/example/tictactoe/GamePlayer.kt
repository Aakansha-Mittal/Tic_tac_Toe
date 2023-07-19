package com.example.tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.tictactoe.databinding.ActivityGamePlayerBinding
import com.example.tictactoe.databinding.ActivityMainBinding
import kotlin.properties.Delegates
import kotlin.system.exitProcess

class GamePlayer : AppCompatActivity() {

    lateinit var binding : ActivityGamePlayerBinding

    var playerTurn = true

    var player1count = 0
    var player2count = 0
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var robotArr = ArrayList<Int>()
    var activeUser = 1
    var cellId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamePlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReset.setOnClickListener {
            reset()
        }

        binding.btnExit.setOnClickListener {
            finish()
        }

        var singleUser = intent.getBooleanExtra("singleUser", false)

    }

    fun buttonClick(view : View){
        if(playerTurn) {
            val btn = view as Button
            when(btn.id) {
                R.id.btn1 -> cellId = 1
                R.id.btn2 -> cellId = 2
                R.id.btn3 -> cellId = 3
                R.id.btn4 -> cellId = 4
                R.id.btn5 -> cellId = 5
                R.id.btn6 -> cellId = 6
                R.id.btn7 -> cellId = 7
                R.id.btn8 -> cellId = 8
                R.id.btn9 -> cellId = 9
            }
            playerTurn = false
            Handler().postDelayed(Runnable { playerTurn = true }, 600)
            playNow(btn, cellId)
        }

    }

    private fun playNow(btn: Button, cellId: Int) {
        if(activeUser==1){
            btn.text = "X"
            btn.setTextColor(Color.parseColor("#6B41B6"))
            btn.setBackgroundColor(Color.parseColor("#FFFFFF"))
            player1.add(cellId)
            emptyCells.add(cellId)
            btn.isEnabled = false
            Handler().postDelayed(Runnable {  }, 200)
            val checkWinner = checkWinner()
            if(checkWinner == 1) {
                Handler().postDelayed(Runnable {  reset() }, 2000)
            } else if(singlePlayer) {
                Handler().postDelayed(Runnable { robot() }, 500)
            } else {
                activeUser = 2
            }
        }

        else {
            btn.text = "0"
            btn.setTextColor(Color.parseColor("#6B41B6"))
            btn.setBackgroundColor(Color.parseColor("#FFFFFF"))
            player2.add(cellId)
            emptyCells.add(cellId)
            btn.isEnabled = false
            val checkWinner = checkWinner()
            if(checkWinner == 1) {
                Handler().postDelayed(Runnable {  reset() }, 2000)
            }
            else {
                activeUser = 1
            }
        }
    }

    private fun robot() {
        val rnd = (1..9).random()
        if(emptyCells.contains(rnd)) {
            robot()
        } else {
            var btnSelected : Button?
            btnSelected = when(rnd) {
                1 -> binding.btn1
                2 -> binding.btn2
                3 -> binding.btn3
                4 -> binding.btn4
                5 -> binding.btn5
                6 -> binding.btn6
                7 -> binding.btn7
                8 -> binding.btn8
                9 -> binding.btn9
                else -> {
                    binding.btn1
                }
            }
            btnSelected.text = "0"
            btnSelected.setTextColor(Color.parseColor("#6B41B6"))
            btnSelected.setBackgroundColor(Color.parseColor("#FFFFFF"))
            emptyCells.add(rnd)
            robotArr.add(rnd)
            btnSelected.isEnabled = false
            val checkWinner = checkWinner()
            if(checkWinner == 1) {
                Handler().postDelayed(Runnable {  reset() }, 2000)
            }

        }
    }

    private fun checkWinner(): Int {
        if((player1.contains(1) && player1.contains(2) && player1.contains(3)) ||
            (player1.contains(4) && player1.contains(5) && player1.contains(6)) ||
            (player1.contains(7) && player1.contains(8) && player1.contains(9))||
            (player1.contains(1) && player1.contains(4) && player1.contains(7))||
            (player1.contains(2) && player1.contains(5) && player1.contains(8))||
            (player1.contains(3) && player1.contains(6) && player1.contains(9))||
            (player1.contains(1) && player1.contains(5) && player1.contains(9))||
            (player1.contains(3) && player1.contains(5) && player1.contains(7)) )
        {
             player1count +=1

            reset()
            disableReset()

            Handler().postDelayed(Runnable {  }, 3000)
            val build = AlertDialog.Builder(this)
            build.setTitle("GameOver!!")
            build.setMessage("Player1 wins !! \n\n"+"Do you want to play again? ")
            build.setPositiveButton("ok"){dialog, which ->
                reset()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                exitProcess(1)
            }
            build.show()
            return 1
        }
        else if ((player2.contains(1) && player2.contains(2) && player2.contains(3)) ||
            (player2.contains(4) && player2.contains(5) && player2.contains(6)) ||
            (player2.contains(7) && player2.contains(8) && player2.contains(9))||
            (player2.contains(1) && player2.contains(4) && player2.contains(7))||
            (player2.contains(2) && player2.contains(5) && player2.contains(8))||
            (player2.contains(3) && player2.contains(6) && player2.contains(9))||
            (player2.contains(1) && player2.contains(5) && player2.contains(9))||
            (player2.contains(3) && player2.contains(5) && player2.contains(7)))
        {
            player2count +=1

            reset()
            disableReset()

            Handler().postDelayed(Runnable {  }, 3000)
            val build = AlertDialog.Builder(this)
            build.setTitle("GameOver!!")
            build.setMessage("Player2 wins !! \n\n"+"Do you want to play again? ")
            build.setPositiveButton("ok"){dialog, which ->
                reset()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                exitProcess(1)
            }
            build.show()
            return 1
        }
        else if ((robotArr.contains(1) && robotArr.contains(2) && robotArr.contains(3)) ||
            (robotArr.contains(4) && robotArr.contains(5) && robotArr.contains(6)) ||
            (robotArr.contains(7) && robotArr.contains(8) && robotArr.contains(9))||
            (robotArr.contains(1) && robotArr.contains(4) && robotArr.contains(7))||
            (robotArr.contains(2) && robotArr.contains(5) && robotArr.contains(8))||
            (robotArr.contains(3) && robotArr.contains(6) && robotArr.contains(9))||
            (robotArr.contains(1) && robotArr.contains(5) && robotArr.contains(9))||
            (robotArr.contains(3) && robotArr.contains(5) && robotArr.contains(7)))
        {
            player2count +=1

            reset()
            disableReset()

            Handler().postDelayed(Runnable {  }, 3000)
            val build = AlertDialog.Builder(this)
            build.setTitle("GameOver!!")
            build.setMessage("Computer wins !! \n\n"+"Do you want to play again? ")
            build.setPositiveButton("ok"){dialog, which ->
                reset()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                exitProcess(1)
            }
            build.show()
            return 1

        }
        else if(emptyCells.contains(1) && emptyCells.contains(2) && emptyCells.contains(3) &&
            emptyCells.contains(4) && emptyCells.contains(5) && emptyCells.contains(6) &&
            emptyCells.contains(7) && emptyCells.contains(8) && emptyCells.contains(9) )
        {
            reset()
            disableReset()

            Handler().postDelayed(Runnable {  }, 3000)

            val build = AlertDialog.Builder(this)
            build.setTitle("GameOver!!")
            build.setMessage("Game Draw !! \n\n"+"Do you want to play again? ")
            build.setPositiveButton("ok"){dialog, which ->
                reset()
            }
            build.setNegativeButton("Exit") { dialog, which ->
                exitProcess(1)
            }
            build.show()
            return 1
        }

        return 0
    }

    private fun disableReset() {
        binding.btnReset.isEnabled = false
        Handler().postDelayed(Runnable { binding.btnReset.isEnabled = true  }, 2200)
    }


    private fun reset() {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        robotArr.clear()
        for (i in 1..9){
            var btnSelected : Button?
            btnSelected = when(i) {
                1 -> binding.btn1
                2 -> binding.btn2
                3 -> binding.btn3
                4 -> binding.btn4
                5 -> binding.btn5
                6 -> binding.btn6
                7 -> binding.btn7
                8 -> binding.btn8
                9 -> binding.btn9
                else -> {
                    binding.btn1
                }
            }

            btnSelected.isEnabled = true
            btnSelected.text = ""
            btnSelected.setBackgroundColor(Color.parseColor("#6B41B6"))
            binding.tvPlayer1.text = "Player 1 : $player1count"
            binding.tvPlayer2.text = "player 2 : $player2count"
        }
    }

}