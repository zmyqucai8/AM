package com.qiaotouxi.am.business.main;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiaotouxi.am.R;
import com.qiaotouxi.am.framework.base.BaseActivity;
import com.qiaotouxi.am.framework.utils.AmUtlis;
import com.qiaotouxi.am.framework.utils.BitmapUtils;
import com.qiaotouxi.am.framework.utils.SDCardUtils;
import com.qiaotouxi.am.framework.view.DividerLine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by zmy.
 * @Date 2017/3/13 0013.
 * 我的文件查看页面
 */

public class MyFileActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_path)
    TextView tvPath;
    @BindView(R.id.folder)
    RecyclerView mFolderView;
    @BindView(R.id.files)
    RecyclerView mFileView;
    //文件存储总路径
    private String filePath;
    //AM文件夹下的所有文件夹，即所有客户和设备文件夹
    private List<File> filesList = new ArrayList<File>();
    //文件夹adapter
    private MyFolderAdapter mMyFolderAdapter;
    //文件adapter
    private MyFileAdapter mMyFileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_activity);
        ButterKnife.bind(this);
        initData();
        test();
    }

    /**
     * 初始化数据 设置文件夹adapter
     */
    private void initData() {
        filePath = BitmapUtils.getFilePath();
        setPathText(filePath);
        File path = new File(filePath);
        File[] files = path.listFiles();//返回目录下所有文件
        if (files != null) {
            for (File f : files) {
                filesList.add(f);
            }
        }
        mMyFolderAdapter = new MyFolderAdapter(MyFileActivity.this, filesList);
        mFolderView.setLayoutManager(new GridLayoutManager(MyFileActivity.this, 3));
        mMyFolderAdapter.isFirstOnly(true);
        mMyFolderAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mMyFolderAdapter.setEmptyView(AmUtlis.getEmptyView(MyFileActivity.this, "暂无设备或客户"));
        //设置分割线
        DividerLine dividerLine = new DividerLine();
        dividerLine.setColor(Color.WHITE);
        dividerLine.setSize(1);
        mFolderView.addItemDecoration(dividerLine);
        mFolderView.setAdapter(mMyFolderAdapter);
    }

    /**
     * 测试方法
     */
    private void test() {
        AmUtlis.showLog(" 获取SD卡的可用空间大小=" + SDCardUtils.getSDCardAvailableSize());
        AmUtlis.showLog(" 获取SD卡的根目录=" + SDCardUtils.getSDCardBaseDir());
        AmUtlis.showLog(" 获取SD卡私有Cache目录的路径=" + SDCardUtils.getSDCardPrivateCacheDir(MyFileActivity.this));
        AmUtlis.showLog(" 获取SD卡公有目录的路径 图片 =" + SDCardUtils.getSDCardPublicDir(Environment.DIRECTORY_PICTURES));
        AmUtlis.showLog(" 获取SD卡私有Cache目录的路径 图片=" + SDCardUtils.getSDCardPrivateFilesDir(MyFileActivity.this, Environment.DIRECTORY_PICTURES));
    }


    @Override
    public void onBackPressed() {
        //返回按钮的处理
        if (!AmUtlis.isShowAlert) {
            return;
        }
        if (mFolderView.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        } else {
            mFileView.setVisibility(View.GONE);
            mFolderView.setVisibility(View.VISIBLE);
            setPathText(filePath);
        }
    }


    /**
     * 文件夹被点击，打开对应文件夹 设置adapter
     *
     * @param files
     */
    public void setViewShowOpenFolder(File[] files) {

        List<File> filesList = new ArrayList<File>();
        for (File f : files) {
            filesList.add(f);
        }
        mMyFileAdapter = new MyFileAdapter(MyFileActivity.this, filesList);
        mFileView.setLayoutManager(new GridLayoutManager(MyFileActivity.this, 3));
        mMyFileAdapter.isFirstOnly(true);
        mMyFileAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mMyFileAdapter.setEmptyView(AmUtlis.getEmptyView(MyFileActivity.this, "暂无文件"));
        //设置分割线
        DividerLine dividerLine = new DividerLine();
        dividerLine.setColor(Color.WHITE);
        dividerLine.setSize(1);
        mFileView.addItemDecoration(dividerLine);
        mFileView.setAdapter(mMyFileAdapter);
        mFolderView.setVisibility(View.GONE);
        mFileView.setVisibility(View.VISIBLE);

    }

    /**
     * 设置文件路径
     *
     * @param pathText
     */
    public void setPathText(String pathText) {
        tvPath.setText("文件路径：" + pathText);
    }
}
