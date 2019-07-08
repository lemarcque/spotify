package io.capsulo.spotify

import android.app.Activity
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import kotlinx.android.synthetic.main.player_activity.*
import android.view.Menu
import android.view.MenuItem
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory


/**
 * Responsible for displaying music controller.
 */
class PlayerActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.player_activity)
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
    }

    override
    fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.player_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when(item?.itemId) {
        android.R.id.home -> { println("Quit app"); true }
        R.id.action_like_player -> { println("Add a like"); true }
        else -> super.onOptionsItemSelected(item)
    }

    private fun loadData() {
        // Set album artwork
        val ins = assets.open("artwork_drake_scorpion.jpg")
        val drawable = RoundedBitmapDrawableFactory.create(resources, ins)
        drawable.cornerRadius = 50f
        artwork_album_player.setImageDrawable(drawable)
        ins.close()

        // Set song's information
        txt_player_title.text = "Emotionless"
        txt_player_author.text = "Drake"
    }

    fun tempPlayer() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.drake_imupset)
        mediaPlayer.start()
    }
}