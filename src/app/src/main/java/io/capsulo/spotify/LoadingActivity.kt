package io.capsulo.spotify

import android.app.Activity
import android.content.Intent
import android.os.Bundle

/**
 * Display logo during few seconds.
 */
class LoadingActivity : Activity() {


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