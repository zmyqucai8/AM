package com.qiaotouxi.am.framework.utils;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @Created by zmy.
 * @Date 2017/3/15 0015.
 * @Describe 文件操作工具类
 */
public class FileUtils {
    /**
     * 新建目录
     *
     * @param folderPath String  如  c:/fqf
     * @return boolean
     */
    public static void newFolder(String folderPath) {
        try {
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.mkdir();
            }
        } catch (Exception e) {
            System.out.println("新建目录操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 新建文件
     *
     * @param filePathAndName String  文件路径及名称  如c:/fqf.txt
     * @param fileContent     String  文件内容
     * @return boolean
     */
    public static void newFile(String filePathAndName, String fileContent) {

        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();  //取的路径及文件名
            File myFilePath = new File(filePath);
            /**如果文件不存在就建一个新文件*/
            if (!myFilePath.exists()) {
                myFilePath.createNewFile();
            }
            FileWriter resultFile = new FileWriter(myFilePath);  //用来写入字符文件的便捷类, 在给出 File 对象的情况下构造一个 FileWriter 对象
            PrintWriter myFile = new PrintWriter(resultFile);  //向文本输出流打印对象的格式化表示形式,使用指定文件创建不具有自动行刷新的新 PrintWriter。
            String strContent = fileContent;
            myFile.println(strContent);
            resultFile.close();

        } catch (Exception e) {
            System.out.println("新建文件操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 删除文件
     *
     * @param filePathAndName String  文件路径及名称  如c:/fqf.txt
     * @return boolean
     */
    public static void delFile(String filePathAndName) {
        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            java.io.File myDelFile = new java.io.File(filePath);
            myDelFile.delete();

        } catch (Exception e) {
            System.out.println("删除文件操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 删除文件夹
     *
     * @return boolean
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath);  //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete();  //删除空文件夹

        } catch (Exception e) {
            System.out.println("删除文件夹操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 删除文件夹里面的所有文件
     *
     * @param path String  文件夹路径  如  c:/fqf
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
            }
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String  原文件路径  如：c:/fqf.txt
     * @param newPath String  复制后路径  如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
//           int  bytesum  =  0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {  //文件存在时
                InputStream inStream = new FileInputStream(oldPath);  //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
//               int  length;
                while ((byteread = inStream.read(buffer)) != -1) {
//                   bytesum  +=  byteread;  //字节数  文件大小
//                   System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String  原文件路径  如：c:/fqf
     * @param newPath String  复制后路径  如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs();  //如果文件夹不存在  则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 移动文件到指定目录
     *
     * @param oldPath String  如：c:/fqf.txt
     * @param newPath String  如：d:/fqf.txt
     */
    public static void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        delFile(oldPath);

    }

    /**
     * 移动文件到指定目录
     *
     * @param oldPath String  如：c:/fqf.txt
     * @param newPath String  如：d:/fqf.txt
     */
    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);

    }

    // 拷贝文件
    private static void copyFile2(String source, String dest) {
        try {
            File in = new File(source);
            File out = new File(dest);
            FileInputStream inFile = new FileInputStream(in);
            FileOutputStream outFile = new FileOutputStream(out);
            byte[] buffer = new byte[10240];
            int i = 0;
            while ((i = inFile.read(buffer)) != -1) {
                outFile.write(buffer, 0, i);
            }//end while
            inFile.close();
            outFile.close();
        }//end try
        catch (Exception e) {

        }//end catch
    }//end copyFile


    /**
     * 写入txt文件， 如果不存在则创建写入，如果存在则覆盖写入
     *
     * @param file
     * @param text
     */
    public static void writeTxt(String file, String text) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "UTF-8"));
            out.write(text + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 写入txt文件， 追加写入txt
     *
     * @param file
     * @param text
     */
    public static void writeAddTxt(String file, String text) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true), "UTF-8"));
            out.write(text + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 读TXT文件内容
     *
     * @param fileName
     * @return
     */
    public static String readTxt(File fileName) {
        String result = "";
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            try {
                String read = null;
                while ((read = bufferedReader.readLine()) != null) {
                    result = result + read + "\r\n";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        AmUtlis.showLog("读取出来的文件内容是：" + "\r\n" + result);
        return result;
    }

}