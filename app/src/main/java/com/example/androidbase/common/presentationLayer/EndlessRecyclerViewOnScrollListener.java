package com.example.androidbase.common.presentationLayer;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public abstract class EndlessRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

    /**
     * The total number of items in the dataset after the last load
     */
    private int mPreviousTotal = 0;
    /**
     * True if we are still waiting for the last set of data to load.
     */
    private boolean mLoading = true;


    public abstract RecyclerView.LayoutManager getLayoutManager();

    public abstract void onLoadMore();

    private int getFirstVisibleItemPosition() {
        int lastVisibleItemPosition = 0;
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(null);
            lastVisibleItemPosition = getFirstVisibleItem(lastVisibleItemPositions);
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        return lastVisibleItemPosition;
    }

    public int getFirstVisibleItem(int[] lastVisibleItemPositions) {
        int minPosition = lastVisibleItemPositions[0];
        for (int i = 1; i < lastVisibleItemPositions.length; i++) {
            if (lastVisibleItemPositions[i] < minPosition) {
                minPosition = lastVisibleItemPositions[i];
            }
        }
        return minPosition;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) {
            int visibleItemCount = recyclerView.getChildCount();
            int totalItemCount = recyclerView.getLayoutManager().getItemCount();
            int firstVisibleItem = getFirstVisibleItemPosition();

            if (mLoading) {
                if (totalItemCount > mPreviousTotal) {
                    mLoading = false;
                    mPreviousTotal = totalItemCount;
                }
            }
            int visibleThreshold = 3;
            if (!mLoading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {
                // End has been reached

                onLoadMore();

                mLoading = true;
            }
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState)
    {
        super.onScrollStateChanged(recyclerView, newState);
        // Don't do any work until after the ScrollEvent has ended, so do the following check.
        if (newState == RecyclerView.SCROLL_STATE_IDLE)
        {
            // supply a positive number to recyclerView.canScrollVertically(int direction) to check if scrolling down.
            boolean canScrollDownMore = recyclerView.canScrollVertically(1);
            // If recyclerView.canScrollVertically(1) returns false it means you're at the end of the list.
            if (!canScrollDownMore)
            {
                //call the overridden onScrolled() method in our EndlessRecyclerViewScrollListener class.
                // supply any positive number to the third argument to indicate that we've scrolled downward.
                onScrolled(recyclerView, 0, 1);
            }
        }
    }

    public void restState() {
        mPreviousTotal = 0;
        mLoading = true;
    }
}