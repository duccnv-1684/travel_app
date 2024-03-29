package vn.sunasterisk.travelapp.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.widget.NestedScrollView
import vn.sunasterisk.travelapp.R

class MaxHeightScrollView : NestedScrollView {

    private var maxHeight: Int = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val styledAttrs = context.obtainStyledAttributes(it, R.styleable.MaxHeightScrollView)
            maxHeight = styledAttrs.getDimensionPixelSize(
                R.styleable.MaxHeightScrollView_maxHeight,
                200
            )
            styledAttrs.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val h: Int = View.MeasureSpec.makeMeasureSpec(maxHeight, View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, h)
    }
}
