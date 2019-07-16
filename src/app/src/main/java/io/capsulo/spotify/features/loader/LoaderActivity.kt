package io.capsulo.spotify.features.loader

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import io.capsulo.spotify.features.player.PlayerActivity
import io.capsulo.spotify.R

/**
 * Display logo during few seconds.
 */
class LoaderActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.loading_activity)
        pause()
    }

    private fun pause() {
        Thread(Runnable {
            Thread.sleep(1000)
            runOnUiThread { startActivity(Intent(this, PlayerActivity::class.java)) }
        }).start()
    }

}