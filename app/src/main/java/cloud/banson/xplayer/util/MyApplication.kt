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
        lateinit var flutterEngine: FlutterEngine

        val context: Context
            get() {
                return myContext
            }

        const val flutterEngineId = "info_page_flutter_engine"
        const val flutterChannelId = "banson.cloud/info"
    }

    override fun onCreate() {
        super.onCreate()
        myContext = applicationContext
        initFlutter()
    }

    private fun initFlutter() {
        flutterEngine = FlutterEngine(MyApplication.context)
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
        FlutterEngineCache.getInstance().put(flutterEngineId, flutterEngine)
    }
}
