package com.ihewro.focus.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.blankj.ALog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ihewro.focus.R;
import com.ihewro.focus.activity.FeedCategoryActivity;
import com.ihewro.focus.activity.FeedListActivity;
import com.ihewro.focus.bean.Help;
import com.ihewro.focus.bean.Website;
import com.ihewro.focus.callback.ImageLoaderCallback;
import com.ihewro.focus.util.ImageLoaderManager;
import com.ihewro.focus.util.StringUtil;
import com.ihewro.focus.view.FeedListPopView;
import com.ihewro.focus.view.RequireListPopupView;
import com.lxj.xpopup.XPopup;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import skin.support.utils.SkinPreference;

/**
 * <pre>
 *     author : hewro
 *     e-mail : ihewro@163.com
 *     time   : 2019/04/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class FeedCategoryRightAdapter extends BaseQuickAdapter<Website, BaseViewHolder> {

    private Activity activity;
    public FeedCategoryRightAdapter(@Nullable List<Website> data, Activity activity) {
        super(R.layout.item_adpater_right_category,data);
        this.activity = activity;
        bindItemListener();

    }

    @Override
    protected void convert(final BaseViewHolder helper, Website item) {
        helper.setText(R.id.name,item.getName());
        if (StringUtil.trim(item.getDesc()).equals("")){
            helper.setGone(R.id.desc,false);
        }else {
            helper.setGone(R.id.desc,true);
            helper.setText(R.id.desc,item.getDesc());
        }

        if (StringUtil.trim(item.getIcon()).equals("")){
            helper.setImageResource(R.id.icon,R.drawable.ic_rss_feed_grey_24dp);
        }else {
            ALog.d("ico图片地址"+item.getIcon());

            ImageLoader.getInstance().displayImage(StringUtil.trim(String.valueOf(item.getIcon())), (ImageView) helper.getView(R.id.icon),ImageLoaderManager.getSubsciptionIconOptions(activity));


        }


    }

    private void bindItemListener(){
        this.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Website item = FeedCategoryRightAdapter.this.getData().get(position);

                Help help;
                if (!StringUtil.trim(item.getExtra()).equals("")){
                    help = new Help(true,item.getExtra());
                }else {
                    help = new Help(false);
                }

                FeedListPopView feedListPopView = new FeedListPopView(((FragmentActivity)activity).getSupportFragmentManager(),activity,item.getName(),"",help);

                new XPopup.Builder(activity)
                        .enableDrag(false)
                        .asCustom(feedListPopView)
                        .show();

            }
        });
    }

}
