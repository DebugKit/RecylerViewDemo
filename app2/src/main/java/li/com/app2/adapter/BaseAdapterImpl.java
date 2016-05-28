package li.com.app2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import li.com.app2.R;
import li.com.app2.help.OnItemTouchCallback;

/**
 * Created by Li on 2016/5/27.
 */
public class BaseAdapterImpl extends BaseAdapter implements
        OnItemTouchCallback.OnItemAdapter {

    private List<String> mData;

    public BaseAdapterImpl(List<String> mData) {
        this.mData = mData;
    }

    public List<String> getmData() {
        return mData;
    }

    public void setmData(List<String> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ITEM;
        } else {
            return position + 1 == getItemCount() ? TYPE_FOOTER : TYPE_ITEM;
        }
    }

    @Override
    public void changeMoreStatus(int status) {
        this.status = status;
        notifyDataSetChanged();
    }

    @Override
    RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int
            viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R
                .layout.item_layout, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).textView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        if (mData.size() == 0)
            return mData.size();
        return mData.size() + 1;
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition > toPosition) {//向上拖拽
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mData, i, i - 1);
            }
        } else {//向下拖拽
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mData, i, i + 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {

    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = findView(itemView, R.id.item_textView);
        }
    }
}
