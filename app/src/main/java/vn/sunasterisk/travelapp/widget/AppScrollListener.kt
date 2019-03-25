package vn.sunasterisk.travelapp.widget

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class AppScrollListener : RecyclerView.OnScrollListener() {
    private var lastComleteVisibleItemPosition: Int = 0
    private var firstVisibleItemPosition: Int = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager
        val size = layoutManager!!.itemCount
        if (layoutManager is LinearLayoutManager) findFirstAndLastVisible(layoutManager)
        if (dy > 0 && size - lastComleteVisibleItemPosition <= 5) onLoadMore()
    }

    private fun findFirstAndLastVisible(layoutManager: LinearLayoutManager) {
        firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        lastComleteVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
    }

    abstract fun onLoadMore()
}
