package cloud.banson.xplayer.util

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    companion object {
        private lateinit var myContext: Context

        val context: Context
            get() {
                return myContext
            }
    }

    override fun onCreate() {
        super.onCreate()
        myContext = applicationContext
    }
}
