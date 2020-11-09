package cloud.banson.xplayer.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import cloud.banson.xplayer.util.MyApplication

class LinearWrapper(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        performClick()
        Toast.makeText(MyApplication.context, "LinearWrapper CLICKED", Toast.LENGTH_LONG).show()
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}