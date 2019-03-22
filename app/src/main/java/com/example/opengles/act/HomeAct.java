package com.example.opengles.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.opengles.R;
import com.example.opengles.adapter.HomeAdapter;
import com.example.opengles.models.RvData;

import java.util.ArrayList;
import java.util.List;

public class HomeAct extends Activity {
    private List<RvData> rvDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);

        initData();
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.gl_rv);
        HomeAdapter homeAdapter = new HomeAdapter(R.layout.item_home_adapter, rvDataList);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(homeAdapter);

        homeAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(HomeAct.this, HomeSimpleAct.class));
                        break;
                    case 1:
                        startActivity(new Intent(HomeAct.this, TriangleAct.class));
                        break;
                    case 2:
                        startActivity(new Intent(HomeAct.this, SixPointStarAct.class));
                        break;
                    case 3:
                        startActivity(new Intent(HomeAct.this, CubeAct.class));
                        break;
                }
            }
        });
    }

    private void initData() {
        String[] names = {
                "Simple RecyclerView", "Draw Triangle", "Draw SixPointStar",
                "平移变换"
        };
        rvDataList = new ArrayList<>();
        for (String name : names) {
            RvData rvData = new RvData();
            rvData.setName(name);

            String url = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=532808234,3202881386&fm=27&gp=0.jpg";
            rvData.setUrl(url);
            rvDataList.add(rvData);
        }
    }
}
