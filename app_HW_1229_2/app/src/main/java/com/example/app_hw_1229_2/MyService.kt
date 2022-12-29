package com.example.app_hw_1229_2

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.*

class MyService : Service() {
    private var channel = ""
    private var jobco: Job?=null;
    private lateinit var co:CoroutineScope;
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.extras?.let {
            channel = it.getString("channel", "")
        }
        broadcast(
            when(channel) {
                "music" -> "歡迎來到音樂頻道"
                "new" -> "歡迎來到新聞頻道"
                "sport" -> "歡迎來到體育頻道"
                else -> "頻道錯誤"
            }
        )

     co=CoroutineScope(CoroutineName("first"))

    if(jobco?.isActive == true){
        jobco?.cancel();
    }

    jobco=co.launch {
        try {
            delay(3000)
            broadcast(
                when(channel) {
                    "music" -> "即將播放本月 TOP10 音樂"
                    "new" -> "即將為您提供獨家新聞"
                    "sport" -> "即將播報本週 NBA 賽事"
                    else -> "頻道錯誤"
                }
            )
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

        return START_STICKY
    }
    override fun onBind(intent: Intent): IBinder? = null

    private fun broadcast(msg: String) =
        sendBroadcast(Intent(channel).putExtra("msg", msg))
}

