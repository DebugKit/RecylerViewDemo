package li.com.app2.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import li.com.app2.adapter.BaseAdapter;

/**
 * Created by Li on 2016/5/27.
 */
public class LoadMoreRecyclerView extends RecyclerView {

    private int lastVisiblePosition;
    private BaseAdapter mAdapter;


    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet
            attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int
                    newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //每页10条，如果上次获取的是10条，则表示还有数据，就可以继续获取
                if ((mAdapter.getItemCount() - 1) % BaseAdapter.COUNT == 0) {
                    mAdapter.changeMoreStatus(BaseAdapter.LOADING_MORE);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE
                            && lastVisiblePosition + 1 == mAdapter
                            .getItemCount()) {
                        loadMoreListener.onLoadMoreListener();
                    }
                } else {
                    mAdapter.changeMoreStatus(BaseAdapter.NO_MORE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisiblePosition = ((LinearLayoutManager) recyclerView
                        .getLayoutManager()).findLastVisibleItemPosition();
            }
        });
    }

    /**
     * 将Adapter作为参数传进来，在调用setAdapter()方法
     *
     * @param mAdapter
     */
    public void addAdapter(BaseAdapter mAdapter) {
        this.mAdapter = mAdapter;
        setAdapter(mAdapter);
    }


    private LoadMoreListener loadMoreListener;

    /**
     * 设置加载更多
     *
     * @param loadMoreListener
     */
    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    /**
     * 加载更多监听
     */
    public interface LoadMoreListener {
        void onLoadMoreListener();
    }
}
