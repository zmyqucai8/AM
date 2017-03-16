package com.qiaotouxi.am.framework.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 农机管理 图片处理工具类.
 */
public class BitmapUtils {

    /**
     * 存储文件夹的总目录
     */
    public final static String DIR = "/AM/";
    /**
     * 保存图片的文件名 首字母
     * 其完整名字是 IMG_TYPE_XXX + System.currentTimeMillis 拼接
     */
    public final static String IMG_TYPE_CCBH = "ccbh";
    public final static String IMG_TYPE_FDJBH = "fdjbh";
    public final static String IMG_TYPE_RJHY = "rjhy";
    public final static String IMG_TYPE_KHTX = "khtx";

    /**
     * 获取本地图片bitmap
     *
     * @param pathString
     * @return
     */
    public static Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        BitmapFactory.Options options;
//        File file = new File(pathString);
//        if (file.exists()) {
//            bitmap = BitmapFactory.decodeFile(pathString);
//        }

            try {
                options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                bitmap = BitmapFactory.decodeFile(pathString, options);
            } catch (Exception e1) {
                AmUtlis.showLog("图片解析错误");
            }


        return bitmap;
    }

    /**
     * 获取图片保存位置
     *
     * @return
     */
    public static String getFilePath() {

        return Environment.getExternalStorageDirectory() + DIR;
    }


    /**
     * 保存bitmap到sdcrad ，
     *
     * @param btImage
     * @return 返回保存路径
     * @nameLetter 图片名字的首字母
     * @dir 设备 或者 客户 的文件夹路径
     */
    public static String saveImg(Bitmap btImage, String dir, String nameLetter) {
        String path = "";
        if (SDCardUtils.isSDCardMounted()) // 判断是否可以对SDcard进行操作
        {    // 获取SDCard指定目录下

            FileOutputStream out = null;
            String sdCardDir = Environment.getExternalStorageDirectory() + DIR + dir;//单独的文件夹

            File dirFile = new File(sdCardDir);  //目录转化成文件夹
            if (!dirFile.exists()) {              //如果不存在，那就建立这个文件夹
                dirFile.mkdirs();
            }                          //文件夹有啦，就可以保存图片啦
            File file = new File(sdCardDir, nameLetter + System.currentTimeMillis() + ".jpg");// 在SDcard的目录下创建图片文,以当前时间为其命名
            path = file.getAbsolutePath();
            try {
                out = new FileOutputStream(file);
                btImage.compress(Bitmap.CompressFormat.JPEG, 100, out);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            AmUtlis.showLog("文件保存成功=" + path);
        }
        return path;
    }


}