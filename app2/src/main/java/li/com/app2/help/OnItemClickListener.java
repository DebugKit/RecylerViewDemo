package li.com.app2.help;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Li on 2016/5/27.
 */
public abstract class OnItemClickListener implements RecyclerView
        .OnItemTouchListener {

    private GestureDetectorCompat mGestureDetector;
    private RecyclerView mRecyclerView;

    public OnItemClickListener(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        mGestureDetector = new GestureDetectorCompat(mRecyclerView.getContext
                (), new RecyclerViewItemClick());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    class RecyclerViewItemClick extends GestureDetector
            .SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                RecyclerView.ViewHolder vh = mRecyclerView.getChildViewHolder
                        (child);
                setOnItemClickListener(vh);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                RecyclerView.ViewHolder vh = mRecyclerView.getChildViewHolder
                        (child);
                setOnItemLongClickListener(vh);
            }
        }
    }

    public abstract void setOnItemClickListener(RecyclerView.ViewHolder vh);

    public abstract void setOnItemLongClickListener(RecyclerView.ViewHolder vh);
}
