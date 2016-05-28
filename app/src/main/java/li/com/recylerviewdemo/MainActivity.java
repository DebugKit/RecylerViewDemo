package li.com.recylerviewdemo;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import li.com.recylerviewdemo.adapter.RecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;
    private LinearLayoutManager manager;

    private List<String> mDatas;
    private RecyclerViewAdapter mAdapter;

    private int index = 1;
    private int lastVisibleItem;

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
                onRefresh();
            }
        });

    }

    private void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            mDatas.add("new item" + i);
        }
        mAdapter = new RecyclerViewAdapter(mDatas, this);
        mRecyclerView.setAdapter(mAdapter);


    }

    private void initEvent() {
        mSwipeRefresh.setOnRefreshListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int
                    newState) {
                //recyclerView.getScrollState();
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    mAdapter.changeMoreStatus(RecyclerViewAdapter
                            .LOADING_MORE);
                    mSwipeRefresh.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            try {
//                                Thread.sleep(3000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                            for (int i = 0; i < 20; i++) {
                                mDatas.add("more item" + i);
                            }
                            mAdapter.setmDatas(mDatas);
                            mAdapter.changeMoreStatus(RecyclerViewAdapter
                                    .PULLUP_LOAD_MORE);
//                            mAdapter.notifyDataSetChanged();
                            mSwipeRefresh.setRefreshing(false);
                            Toast.makeText(MainActivity.this, "上拉加载了一次数据！",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }, 3000);
                }
                Log.d("MainActivity", "1111111111");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = manager.findLastVisibleItemPosition();
//                if (recyclerView.getScrollState() == RecyclerView
//                        .SCROLL_STATE_IDLE&& )
                Log.d("MainActivity", "22222222222");
            }
        });
    }

    private void initView() {
        mRecyclerView = findView(R.id.recyclerView);
        mSwipeRefresh = findView(R.id.swipeRefresh);

        manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        //設置刷新時動畫，最多可以設置4個
        mSwipeRefresh.setProgressBackgroundColorSchemeResource(android.R
                .color.white);
        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_dark);


    }

    @Override
    public void onRefresh() {
//        mSwipeRefresh.postDelayed(new Runnable() {
//            @Override
//            public void run() {

        mDatas.clear();
        for (int i = 0; i < 20; i++) {
            mDatas.add("new item" + i + "  " + index);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mAdapter.setmDatas(mDatas);
        mAdapter.notifyDataSetChanged();

        index++;
        Toast.makeText(MainActivity.this, "刷新了一次数据！", Toast
                .LENGTH_SHORT).show();

        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(false);
            }
        });
//            }
//        },0);
    }


}
