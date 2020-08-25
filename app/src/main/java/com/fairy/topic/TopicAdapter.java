package com.fairy.topic;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * @author : heshuai
 * @date : 2020/8/25 3:37 PM
 * @email : daokouzhangheshuai@163.com
 * @qq :892541548
 * @description :
 */
public class TopicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TopicAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv_Topic, s);
    }
}
