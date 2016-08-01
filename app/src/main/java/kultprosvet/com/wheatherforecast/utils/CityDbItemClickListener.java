package kultprosvet.com.wheatherforecast.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Used for listening to RecyclerView item clicks.
 */
public class CityDbItemClickListener implements RecyclerView.OnItemTouchListener {
    protected OnItemClickListener mListener;
    private GestureDetector mGestureDetector;

    @Nullable
    private View mChildView;
    private int mChildViewPosition;

    public CityDbItemClickListener(Context context, OnItemClickListener listener) {
        mGestureDetector = new GestureDetector(context, new GestureListener());
        mListener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent event) {
        mChildView = view.findChildViewUnder(event.getX(), event.getY());
        mChildViewPosition = view.getChildAdapterPosition(mChildView);

        return mChildView != null && mGestureDetector.onTouchEvent(event);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        // Not needed.
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent event) {
        // Not needed.
    }

    /**
     * A click listener for items.
     */
    public interface OnItemClickListener {
        /**
         * Called when an item is clicked.
         *
         * @param childView View of the item that was clicked.
         * @param position  Position of the item that was clicked.
         */
        public void onItemClick(View childView, int position);

        /**
         * Called when an item is long pressed.
         *
         * @param childView View of the item that was long pressed.
         * @param position  Position of the item that was long pressed.
         */
        public void onItemLongPress(View childView, int position);
    }

    /**
     * A simple click listener whose methods can be overridden.
     */
    public static abstract class SimpleOnItemClickListener implements OnItemClickListener {
        /**
         * Called when an item is clicked. The default implementation is a no-op.
         *
         * @param childView View of the item that was clicked.
         * @param position  Position of the item that was clicked.
         */
        public void onItemClick(View childView, int position) {
            // Do nothing.
        }

        /**
         * Called when an item is long pressed. The default implementation is a no-op.
         *
         * @param childView View of the item that was long pressed.
         * @param position  Position of the item that was long pressed.
         */
        public void onItemLongPress(View childView, int position) {
            // Do nothing.
        }
    }

    protected class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            if (mChildView != null) {
                mListener.onItemClick(mChildView, mChildViewPosition);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent event) {
            if (mChildView != null) {
                mListener.onItemLongPress(mChildView, mChildViewPosition);
            }
        }

        @Override
        public boolean onDown(MotionEvent event) {
            // Best practice to always return true here.
            return true;
        }
    }
}