package com.example.ahndwon.draganddraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class BoxDrawingView(context: Context, attrs: AttributeSet? = null)
    : View(context, attrs) {

    private var mCurrentBox : Box? = null
    private var mBoxen = ArrayList<Box>()
    val mBoxPaint = Paint().apply { this.color = 0x22ff0000 }
    val mBackgroundPaint = Paint().apply { this.color = 0xfff8efe}


    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        var action = ""
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"

                mCurrentBox = Box(current, current)
                mCurrentBox?.let { mBoxen.add(it) }
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"

                mCurrentBox?.let {
                    it.mCurrent = current
                    invalidate()
                }

            }
            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
                mCurrentBox = null
            }
            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEL"
                mCurrentBox = null
            }

        }

        Log.i(BoxDrawingView::class.java.name, "$action at x= ${current.x}, y=${current.y}")
        return true
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPaint(mBackgroundPaint)

        for (box in mBoxen) {
            val left = Math.min(box.mOrigin.x, box.mCurrent.x)
            val right = Math.max(box.mOrigin.x, box.mCurrent.x)
            val top = Math.min(box.mOrigin.y, box.mCurrent.y)
            val bottom = Math.max(box.mOrigin.y, box.mCurrent.y)

            canvas.drawRect(left, top, right, bottom, mBoxPaint)
        }
    }
}