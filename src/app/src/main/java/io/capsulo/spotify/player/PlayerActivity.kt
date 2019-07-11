package io.capsulo.spotify.player

import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import kotlinx.android.synthetic.main.player_activity.*
import java.util.*
import kotlin.math.floor
import kotlin.math.round


/**
 * Responsible for displaying music controller.
 */
class PlayerActivity : Activity() {

    private var player: MediaPlayer? = null
    private var timer: Timer? = null
    private var isFinish: Boolean = false

    //
    private var expandedHeightContainer:Int? = null
    private var shortedHeightContainer:Int? = null
    private var isExpanded = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(io.capsulo.spotify.R.layout.player_activity)
        setInterface()

        // init
        loadData()
        tempPlayer()
    }

    private fun setInterface() {
        // configuration of app bar
        setActionBar(toolbar_player)
        actionBar?.title = ""
        actionBar?.setDisplayHomeAsUpEnabled(true)
        //actionBar?.setHomeAsUpIndicator(R.drawable.left_arrow)

        // set listener for seekbar
        seekbar_player.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser && player != null) {
                    // move to a specific position
                    val milliseconds = progress * player!!.duration  / 100
                    player?.seekTo(milliseconds)
                }
            }
        })

        // handle click on play / pause button
        btn_player_pause.setOnClickListener {
            when {
                isFinish -> restart()
                player!!.isPlaying -> pause()
                else -> play()
            }
        }

        // handle click on rewind button
        btn_player_rewind.setOnClickListener { restart() }

        // configuration of main container
        expandedHeightContainer = main_container_player.height

        shortedHeightContainer = 150

        // set click event for sliding up / down
        btn_player_lyrics.setOnClickListener { if (isExpanded) slideUp(main_container_player)}
        main_container_player.setOnClickListener { if (!isExpanded) slideDown(main_container_player) }
    }

    /**
     * TODO : Create a Custom View (Slider) and deport function
     */
    private fun slideDown(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0f, // fromXDelta
            0f, // toXDelta
            - view.height.toFloat() + shortedHeightContainer!!, // fromYDelta
            0f
        )                // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        animate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                isExpanded = true

                // Switch view displayed
                //controller_expanded_player.vis
                controller_expanded_player.visibility = View.VISIBLE
                controller_shorted_player.visibility = View.GONE
            }
        })
        view.startAnimation(animate)
    }

    // slide the view from its current position to below itself
    private fun slideUp(view: View) {
        val animate = TranslateAnimation(
            0f, // fromXDelta
            0f, // toXDelta
            0f, // fromYDelta
            - view.height.toFloat() + shortedHeightContainer!!
        ) // toYDelta
        animate.duration = 500
        animate.fillAfter = true


        animate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                isExpanded = false

                // Switch view displayed
                controller_expanded_player.visibility = View.INVISIBLE
                controller_shorted_player.visibility = View.VISIBLE
            }
        })

        view.startAnimation(animate)
    }

    private fun test() {
        println("Emotion !")
    }

    override
    fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(io.capsulo.spotify.R.menu.player_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when(item?.itemId) {
        android.R.id.home -> { println("Quit app"); true }
        io.capsulo.spotify.R.id.action_like_player -> { println("Add a like"); true }
        else -> super.onOptionsItemSelected(item)
    }

    private fun loadData() {
        // Import album cover
        val ins = assets.open("artwork_drake_scorpion.jpg")
        val drawable = RoundedBitmapDrawableFactory.create(resources, ins)
        drawable.cornerRadius = 50f

        // set drawable to views
        artwork_album_player.setImageDrawable(drawable)
        thumb_artwork_album_player.setImageDrawable(drawable)

        // close stream
        ins.close()

        // Set song's information
        txt_player_title.text = "I'm upset"
        txt_player_author.text = "Drake"
        txt_player_title.text = txt_player_title.text
        txt_player_author_shorted.text = txt_player_author.text
    }

    /**
     * I'm particularly of this block of code because i used the docs of Android and
     * no code from stackoverflow ;)
     */
    private fun tempPlayer() {

        player = MediaPlayer.create(this, io.capsulo.spotify.R.raw.drake_imupset)
        player?.setOnCompletionListener { stop() }

        // set song's information

        // set duration formatted
        val minFloat = player?.duration!!.toDouble() / 1000 / 60 // 3.60065
        val float = minFloat - floor(minFloat) // .60065
        val min = (minFloat - float).toInt() // 3
        val sec = (floor(float * 60)).toInt()
        txt_player_duration_time.text = "$min:$sec"

        player?.start()
        startTimer()
    }

    private fun play() {
        btn_player_pause.setImageDrawable(getDrawable(io.capsulo.spotify.R.drawable.ic_pause_button_circle))
        player?.start()
    }

    private fun pause() {
        btn_player_pause.setImageDrawable(getDrawable(io.capsulo.spotify.R.drawable.ic_play_button_circle))
        player?.pause()
    }

    private fun stop() {
        isFinish = true
        btn_player_pause.setImageDrawable(getDrawable(io.capsulo.spotify.R.drawable.ic_play_button_circle))
        //player?.reset()
        timer?.cancel()
        timer?.purge()

    }

    private fun restart() {
        isFinish = false
        btn_player_pause.setImageDrawable(getDrawable(io.capsulo.spotify.R.drawable.ic_pause_button_circle))
        seekbar_player.progress = 0
        player?.seekTo(0)
        player?.start()
        startTimer()
    }

    // set timer for updating text
    private fun startTimer() {
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if(player != null && player!!.isPlaying) {
                    runOnUiThread {
                        val currentMinFloated = player!!.currentPosition.toDouble() / 1000 / 60
                        val currentMin = (floor(currentMinFloated)).toInt()
                        val currentSec = (round((currentMinFloated - currentMin) * 60)).toInt()

                        // update textview
                        val secText = if(currentSec < 10) "0$currentSec" else currentSec.toString()
                        txt_player_current_time.text = "$currentMin:$secText"

                        // update seekbar
                        val seekBarPosition = 100 / player!!.duration.toDouble() * player!!.currentPosition.toDouble()
                        seekbar_player.progress = seekBarPosition.toInt()
                    }
                }
            }
        }, 0, 1000)
    }
}
