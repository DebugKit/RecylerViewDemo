package li.com.app2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import li.com.app2.R;

/**
 * Created by Li on 2016/5/27.
 */
public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView
        .ViewHolder> {

    public final static int TYPE_ITEM = 0;//普通View
    public final static int TYPE_FOOTER = 1;//底部View
    //上拉加载更多
    public static final int PULL_UP_LOAD_MORE = 0x0;
    //正在加载中
    public static final int LOADING_MORE = 0x1;
    //暂无更多数据
    public static final int NO_MORE = 0x2;
    //上拉加载更多状态-默认为0
    public int status = 0x0;

    public static final int COUNT = 10;

    /**
     * 改变加载视图的文字显示
     * @param status
     */
    public abstract void changeMoreStatus(int status);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int
            viewType) {
        //返回底部加载更多的View视图
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R
                    .layout.fooler_layout, parent, false);
            FooterViewHolder footerViewHolder = new FooterViewHolder(view);
            return footerViewHolder;
        } else if (viewType == TYPE_ITEM) {
            return onCreateItemViewHolder(parent, viewType);
        }
        return null;
    }

    /**
     * 创建Item的VIew
     * @param parent
     * @param viewType
     * @return
     */
    abstract RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent,
                                                            int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            if (status == LOADING_MORE) {
                footerViewHolder.footer_textView.setText("正在加载更多数据...");
            } else if (status == PULL_UP_LOAD_MORE) {
                footerViewHolder.footer_textView.setText("加载更多...");
            }else if (status == NO_MORE){
                footerViewHolder.footer_textView.setText("暂无更多");
                footerViewHolder.progressbar.setVisibility(View.GONE);
            }
        } else {
            onBindItemViewHolder(holder, position);
        }
    }

    /**
     * 绑定Item的数据
     * @param holder
     * @param position
     */
    abstract void onBindItemViewHolder(RecyclerView.ViewHolder holder, int
            position);


    class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView footer_textView;
        ProgressBar progressbar;

        public FooterViewHolder(View footerView) {
            super(footerView);
            footer_textView = findView(footerView, R.id.footer_text);
            progressbar = findView(footerView, R.id.progressbar);
        }
    }

    public <T extends View> T findView(View view, int resId) {
        return (T) view.findViewById(resId);
    }
}
