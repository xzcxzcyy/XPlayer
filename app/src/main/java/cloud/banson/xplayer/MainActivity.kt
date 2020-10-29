package cloud.banson.xplayer

import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import cloud.banson.xplayer.data.Video
import cloud.banson.xplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ACTIVITY_MAIN"
    }

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        checkAndApplyForPermissions()
    }

    private fun checkAndApplyForPermissions() {
        val permissionsStorage = arrayOf(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.READ_EXTERNAL_STORAGE"
                ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.WRITE_EXTERNAL_STORAGE"
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(permissionsStorage, 101)
            }
        }
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}