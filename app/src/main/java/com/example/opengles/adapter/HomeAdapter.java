package com.example.opengles.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.opengles.R;
import com.example.opengles.models.RvData;

import java.util.List;

public class HomeAdapter extends BaseQuickAdapter<RvData, BaseViewHolder> {

    public HomeAdapter(int layoutResId, List<RvData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RvData item) {
        helper.setText(R.id.id_text, item.getName());

        Glide.with(mContext).load(item.getUrl()).into((ImageView) helper.getView(R.id.iv_item));
    }
}
