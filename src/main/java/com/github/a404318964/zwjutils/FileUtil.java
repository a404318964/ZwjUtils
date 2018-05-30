package com.github.a404318964.zwjutils;

import java.io.*;

/**
 * Created by zwj on 2016/11/21.
 */
public class FileUtil {

    /**
     * 将数据流写入文件
     *
     * @param is         读数据流
     * @param folderPath 目录路径
     * @param fileName   文件名称(全称,包含文件后缀)
     * @return
     */
    public static String createFile(InputStream is, String folderPath, String fileName) {
        boolean flag = true;   // 用来标志是否写入成功
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File desFile = new File(folder, fileName);
        OutputStream os = null;
        try {
            os = new FileOutputStream(desFile);

            // 一次读多个字节
            byte[] tempbytes = new byte[1024];
            int len = 0;

            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((len = is.read(tempbytes)) != -1) {
                os.write(tempbytes, 0, len);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            flag = false;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (flag) {
            return desFile.getAbsolutePath();
        } else {
            return null;
        }
    }

    /**
     * 根据指定文件名生成新文件名
     *
     * @param path   原文件完整路径
     * @param suffix 后缀
     * @return 新的文件名（含路径）
     */
    public static String generatorSmallImagePath(String path, String suffix) {
        String[] paths = path.split("\\.");
        String newpath = "";
        for (int i = 0; i < paths.length - 1; i++) {
            if (i > 0) {
                newpath += ".";
            }
            newpath += paths[i];
        }
        newpath += suffix + "." + paths[paths.length - 1];
        return newpath;
    }

    /**
     * 将数据流写入文件
     *
     * @param data       数据
     * @param folderPath 目录路径
     * @param fileName   文件名称(全称,包含文件后缀)
     * @return
     */
    public static String createFile(byte[] data, String folderPath, String fileName) {
        boolean flag = true;   // 用来标志是否写入成功
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File desFile = new File(folder, fileName);
        OutputStream os = null;
        try {
            os = new FileOutputStream(desFile);
            os.write(data, 0, data.length);
        } catch (Exception e1) {
            e1.printStackTrace();
            flag = false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (flag) {
            return desFile.getAbsolutePath();
        } else {
            return null;
        }
    }
}
