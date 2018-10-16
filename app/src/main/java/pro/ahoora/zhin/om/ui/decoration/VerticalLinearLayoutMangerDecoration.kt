package pro.ahoora.zhin.om.ui.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import pro.ahoora.zhin.om.util.Converter

class VerticalLinearLayoutMangerDecoration : RecyclerView.ItemDecoration {

    private var offset = 0
    lateinit var context: Context
    private var leftOffset = 8
    private var rightOffset = 8
    private var topOffset = 8
    private var bottomOffset = 8

    constructor(context: Context, itemOffsetDp: Int) {
        this.context = context
        if (itemOffsetDp == 0)
            throw Exception("zero offset is not accepted")
        offset = Converter.pxFromDp(context, itemOffsetDp.toFloat()).toInt()
    }

    constructor(context: Context, leftOffsetDp: Int, topOffsetDp: Int, rightOffsetDp: Int, bottomOffsetDp: Int) {
        this.context = context
        if (leftOffsetDp == 0 || rightOffsetDp == 0 || topOffsetDp == 0 || bottomOffsetDp == 0)
            throw Exception("zero offset is not accepted")
        this.leftOffset = Converter.pxFromDp(context, leftOffsetDp.toFloat()).toInt()
        this.topOffset = Converter.pxFromDp(context, topOffsetDp.toFloat()).toInt()
        this.rightOffset = Converter.pxFromDp(context, rightOffsetDp.toFloat()).toInt()
        this.bottomOffset = Converter.pxFromDp(context, bottomOffsetDp.toFloat()).toInt()
    }


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemCount = parent.adapter?.itemCount
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition

        if (offset != 0) {
            outRect.top = offset / 2
            if (itemPosition == 0) {
                outRect.top = offset
            }
            outRect.left = offset
            outRect.right = offset
            outRect.bottom = offset / 2

            if (itemPosition == itemCount?.minus(1)) {
                outRect.bottom = offset
            }

        } else {
            outRect.top = topOffset / 2
            if (itemPosition == 0) {
                outRect.top = topOffset
            }
            outRect.left = leftOffset
            outRect.right = rightOffset
            outRect.bottom = bottomOffset / 2

            if (itemPosition == itemCount?.minus(1)) {
                outRect.bottom = bottomOffset
            }
        }


    }

}