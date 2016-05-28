package li.com.app2;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import li.com.app2.adapter.BaseAdapter;
import li.com.app2.adapter.BaseAdapterImpl;
import li.com.app2.help.OnItemClickListener;
import li.com.app2.help.OnItemTouchCallback;
import li.com.app2.view.LoadMoreRecyclerView;

public class MainActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView
        .LoadMoreListener {

    private LoadMoreRecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;

    //    private OnItemClickListener onItemClickListener;
//    private OnItemTouchCallback onItemTouchCallback;

    private BaseAdapterImpl mAdapter;
    private List<String> mData;

    private int index = 0;
    private int count = 0;

    private <T extends View> T findView(int resId) {
        return (T) findViewById(resId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(true);
            }
        });
        onRefresh();
    }

    private void initView() {
        mRecyclerView = findView(R.id.recyclerView);
        mSwipeRefresh = findView(R.id.swipeRefresh);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefresh.setProgressBackgroundColorSchemeColor(Color.WHITE);
        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright);
//        mRecyclerView.set
    }

    private void initData() {
        mData = new ArrayList<String>();
        mAdapter = new BaseAdapterImpl(mData);
        mRecyclerView.addAdapter(mAdapter);
    }

    private void initEvent() {
        mSwipeRefresh.setOnRefreshListener(this);
        mRecyclerView.setLoadMoreListener(this);

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new
                OnItemTouchCallback(mAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener
                (mRecyclerView) {
            @Override
            public void setOnItemClickListener(RecyclerView.ViewHolder vh) {
                Toast.makeText(MainActivity.this, "第" + vh.getLayoutPosition
                        () + "单击事件", Toast
                        .LENGTH_SHORT)
                        .show();
            }

            @Override
            public void setOnItemLongClickListener(RecyclerView.ViewHolder vh) {
                Toast.makeText(MainActivity.this, "长按", Toast.LENGTH_SHORT)
                        .show();
                itemTouchHelper.startDrag(vh);
            }
        });


    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mSwipeRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                mData.clear();
                for (int i = 0; i < 10; i++) {
                    mData.add("new Item" + i + " " + index);
                }
                index++;
                mAdapter.setmData(mData);
                mSwipeRefresh.setRefreshing(false);
            }
        }, 2000);
    }

    //上拉加载
    @Override
    public void onLoadMoreListener() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (count > 1) {
                    for (int i = 0; i < 9; i++) {
                        mData.add("more Item" + i + " " + count);
                    }
                } else {
                    for (int i = 0; i < 10; i++) {
                        mData.add("more Item" + i + " " + count);
                    }
                }
                mAdapter.setmData(mData);
                mAdapter.changeMoreStatus(BaseAdapter.PULL_UP_LOAD_MORE);
                count++;

            }
        }, 3000);
    }
}
