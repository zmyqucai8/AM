package com.qiaotouxi.am.business.main;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiaotouxi.am.R;
import com.qiaotouxi.am.framework.base.BaseActivity;
import com.qiaotouxi.am.framework.utils.AmUtlis;
import com.qiaotouxi.am.framework.utils.BitmapUtils;
import com.qiaotouxi.am.framework.view.DividerLine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by zmy.
 * @Date 2017/3/13 0013.
 * 相册下的
 */

public class MyAlbumActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.img_ccbh)
    ImageView imgCcbh;
    @BindView(R.id.img_fdjbh)
    ImageView imgFdjbh;
    @BindView(R.id.img_khtx)
    ImageView imgKhtx;
    @BindView(R.id.img_rjhy)
    ImageView imgRjhy;
    @BindView(R.id.ll_item)
    LinearLayout llItem;
    private File file;//图片文件夹
    private MyAlbumAdapter mAdapter;
    //四种类型照片的list集合
    private List<String> mCcbhList = new ArrayList<String>();
    private List<String> mFdjbhList = new ArrayList<String>();
    private List<String> mKhtxList = new ArrayList<String>();
    private List<String> mRjhyList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_activity);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        String sdCardDir = BitmapUtils.getPhotoPath();
        file = new File(sdCardDir);
        if (!file.exists()) {
            AmUtlis.showLog(file + " not exists");
            return;
        }
        File fa[] = file.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (!fs.isDirectory()) {
                String name = fs.getName();
                if (name.contains(BitmapUtils.IMG_TYPE_CCBH)) {
                    mCcbhList.add(fs.getAbsolutePath());
                } else if (name.contains(BitmapUtils.IMG_TYPE_FDJBH)) {
                    mFdjbhList.add(fs.getAbsolutePath());
                } else if (name.contains(BitmapUtils.IMG_TYPE_KHTX)) {
                    mKhtxList.add(fs.getAbsolutePath());
                } else if (name.contains(BitmapUtils.IMG_TYPE_RJHY)) {
                    mRjhyList.add(fs.getAbsolutePath());
                }


                AmUtlis.showLog(fs.getAbsolutePath() + "文件名");


            }
        }

    }

    /**
     * 初始化view 与data
     */
    private void initView() {
        //设置adapter
        mAdapter = new MyAlbumAdapter(MyAlbumActivity.this, mCcbhList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(MyAlbumActivity.this, 3));
        mAdapter.isFirstOnly(true);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.setEmptyView(AmUtlis.getEmptyView(MyAlbumActivity.this, "暂无照片"));
        //设置分割线
        DividerLine dividerLine = new DividerLine();
        dividerLine.setColor(Color.WHITE);
        dividerLine.setSize(1);
        mRecyclerView.addItemDecoration(dividerLine);
        mRecyclerView.setAdapter(mAdapter);
        if (mCcbhList.size() > 0) {
            imgCcbh.setImageBitmap(BitmapUtils.getDiskBitmap(mCcbhList.get(0)));
        }
        if (mFdjbhList.size() > 0) {
            imgFdjbh.setImageBitmap(BitmapUtils.getDiskBitmap(mFdjbhList.get(0)));
        }
        if (mKhtxList.size() > 0) {
            imgKhtx.setImageBitmap(BitmapUtils.getDiskBitmap(mKhtxList.get(0)));
        }
        if (mRjhyList.size() > 0) {
            imgRjhy.setImageBitmap(BitmapUtils.getDiskBitmap(mRjhyList.get(0)));
        }

        imgCcbh.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        imgFdjbh.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        imgKhtx.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        imgRjhy.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);


        imgCcbh.setOnClickListener(this);
        imgFdjbh.setOnClickListener(this);
        imgKhtx.setOnClickListener(this);
        imgRjhy.setOnClickListener(this);
        tv_title.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_ccbh:
                switchAlbum(BitmapUtils.IMG_TYPE_CCBH);
                break;
            case R.id.img_fdjbh:
                switchAlbum(BitmapUtils.IMG_TYPE_FDJBH);
                break;
            case R.id.img_khtx:
                switchAlbum(BitmapUtils.IMG_TYPE_KHTX);
                break;
            case R.id.img_rjhy:
                switchAlbum(BitmapUtils.IMG_TYPE_RJHY);
                break;
            case R.id.tv_title: //TODO：删除测试按钮 记得注释
                File fa[] = file.listFiles();
                for (int i = 0; i < fa.length; i++) {
                    File fs = fa[i];
                    if (fs.isDirectory()) {

                        AmUtlis.showLog(fs.getName() + " [目录]");
                    } else {
                        fs.delete();
                        AmUtlis.showLog(fs.getAbsolutePath() + "文件名-删除");
                    }
                }
                break;
        }

    }

    /**
     * 切换相册
     *
     * @param imgTypeCcbh
     */
    private void switchAlbum(String imgTypeCcbh) {

        switch (imgTypeCcbh) {

            case BitmapUtils.IMG_TYPE_CCBH:
                mAdapter.setNewData(mCcbhList);
                tv_title.setText("出厂编号照片");
                break;
            case BitmapUtils.IMG_TYPE_FDJBH:
                mAdapter.setNewData(mFdjbhList);
                tv_title.setText("发动机编号照片");
                break;
            case BitmapUtils.IMG_TYPE_KHTX:
                mAdapter.setNewData(mKhtxList);
                tv_title.setText("客户照片");
                break;
            case BitmapUtils.IMG_TYPE_RJHY:
                mAdapter.setNewData(mRjhyList);
                tv_title.setText("人机合影");
                break;
        }
        mRecyclerView.setVisibility(View.VISIBLE);
        llItem.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        //返回按钮的处理
        if (llItem.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        } else {
            llItem.setVisibility(View.VISIBLE);
            tv_title.setText("所有照片");
            mRecyclerView.setVisibility(View.GONE);
        }
    }
}
