package com.example.opengles.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.opengles.R;

import java.util.List;

public class SimpleHomeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SimpleHomeAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String text) {
        helper.setText(R.id.id_num, text)
                .setText(R.id.id_text, "第" + helper.getAdapterPosition() + "个数据")
                .addOnClickListener(R.id.id_num);

        if (helper.getAdapterPosition() % 2 == 0) {
            helper.setTextColor(R.id.id_num, Color.GREEN);
        } else {
            helper.setTextColor(R.id.id_num, Color.BLUE);
        }
    }
}
