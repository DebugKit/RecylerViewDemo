package li.com.app2.help;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Li on 2016/5/27.
 */
public class OnItemTouchCallback extends ItemTouchHelper.Callback {

    private OnItemAdapter onItemAdapter;

    public OnItemTouchCallback(OnItemAdapter onItemAdapter) {
        this.onItemAdapter = onItemAdapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView
            .ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            int dragFlags = ItemTouchHelper.DOWN | ItemTouchHelper.UP;
            int swipeFlags = ItemTouchHelper.LEFT;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            int dragFlags = ItemTouchHelper.DOWN | ItemTouchHelper.UP
                    | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
            int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);

        }
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder
            viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getLayoutPosition();
        int toPosition = target.getLayoutPosition();
        onItemAdapter.onMove(fromPosition, toPosition);
        return true;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder
            viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (background != null)
            viewHolder.itemView.setBackgroundDrawable(background);
        if (color != -1) {
            viewHolder.itemView.setBackgroundColor(color);
        }
    }

    private Drawable background;
    private int color = -1;

    //长按时变色
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int
            actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (background == null && color == -1) {
                Drawable drawable = viewHolder.itemView.getBackground();
                if (drawable == null) {
                    color = 0;
                } else {
                    background = drawable;
                }
            }
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    public interface OnItemAdapter {
        void onMove(int fromPosition, int toPosition);

        void onSwiped(int position);
    }
}
