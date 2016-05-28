package li.com.recylerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import li.com.recylerviewdemo.R;

/**
 * Created by Li on 2016/5/26.
 */
public class RecyclerViewAdapter extends RecyclerView
        .Adapter<RecyclerView.ViewHolder> {

    private List<String> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    private final static int TYPE_ITEM = 0;//普通View
    private final static int TYPE_FOOTER = 1;//底部View
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //上拉加载更多状态-默认为0
    private int load_more_status = 0;

    public void setmDatas(List<String> mDatas) {
        this.mDatas = mDatas;
    }

    public RecyclerViewAdapter(List<String> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        //根據當前位置若是最後一個則顯示底部加載更多的View
        return position + 1 == getItemCount() ? TYPE_FOOTER : TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int
            viewType) {
        mInflater = LayoutInflater.from(mContext);
        View view;
        if (viewType == TYPE_ITEM) {
            view = mInflater.inflate(R.layout.item_layout, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        } else if (viewType == TYPE_FOOTER) {
            view = mInflater.inflate(R.layout.fooler_layout, parent, false);
            FooterHodler footerHodler = new FooterHodler(view);
            return footerHodler;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).textView.setText(mDatas.get(position));
        } else if (holder instanceof FooterHodler) {
            FooterHodler footerHodler = (FooterHodler) holder;
            if (load_more_status == PULLUP_LOAD_MORE) {
                footerHodler.footer_textView.setText("上拉加載更多...");
            } else if (load_more_status == LOADING_MORE) {
                footerHodler.footer_textView.setText("正在加載更多數據...");
            }
        }
    }

    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_textView);
        }
    }

    class FooterHodler extends RecyclerView.ViewHolder {

        TextView footer_textView;

        public FooterHodler(View itemView) {
            super(itemView);
            footer_textView = (TextView) itemView.findViewById(R.id
                    .footer_text);
        }
    }
}
