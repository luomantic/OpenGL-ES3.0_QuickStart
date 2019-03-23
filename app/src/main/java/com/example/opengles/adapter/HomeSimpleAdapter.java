package com.example.opengles.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.opengles.R;

import java.util.List;

public class HomeSimpleAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HomeSimpleAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String text) {
        helper.setText(R.id.tv_num_home_simple, text)
                .setText(R.id.tv_item_cube, "第" + helper.getAdapterPosition() + "个数据")
                .addOnClickListener(R.id.tv_num_home_simple);

        if (helper.getAdapterPosition() % 2 == 0) {
            helper.setTextColor(R.id.tv_num_home_simple, Color.GREEN);
        } else {
            helper.setTextColor(R.id.tv_num_home_simple, Color.BLUE);
        }
    }
}
