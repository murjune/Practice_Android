package org.techtown.myrecycleview

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MyDecoration(val context: Context) : RecyclerView.ItemDecoration() {

    // onDraw(): 항목이 배치되기 전에 호출된다.

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        c.drawBitmap(
            BitmapFactory.decodeResource(context.resources, R.drawable.background),
            0f,
            0f,
            null
        )
    }

    // OnDrawOver() : 항목이 모두 배치된 후 호출된다.
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }

    // getItemOffsets 개별 항목을 꾸밀 때 호출
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val idx = parent.getChildAdapterPosition(view) + 1
        if (idx % 3 == 0) {
            outRect.set(10, 10, 10, 100)
        } else {
            outRect.set(10, 10, 10, 10)
        }
    }
}
