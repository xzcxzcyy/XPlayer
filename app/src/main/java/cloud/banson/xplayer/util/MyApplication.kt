package cloud.banson.xplayer.util

import android.app.Application
import android.content.Context
import cloud.banson.xplayer.ui.play.PlayVideoFragment
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

class MyApplication : Application() {
    companion object {
        private lateinit var myContext: Context
        val context: Context
            get() {
                return myContext
            }

        const val flutterEngineId = "info_page_flutter_engine"
        const val flutterChannelId = "banson.cloud/info"
    }

    lateinit var flutterEngine: FlutterEngine
    override fun onCreate() {
        super.onCreate()
        myContext = applicationContext
        initFlutter()
    }

    private fun initFlutter() {
        flutterEngine = FlutterEngine(this)
        FlutterEngineCache.getInstance().put(flutterEngineId, flutterEngine)
    }
}
