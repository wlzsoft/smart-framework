package com.smartframe.basics.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class FileUtil {
	
	/** 
     * MultipartFile 转换成File 
     *  
     * @param multfile 原文件类型 
     * @return File 
     * @throws IOException 
     */  
    public static File multipartToFile(MultipartFile multfile) throws IOException {  
        CommonsMultipartFile cf = (CommonsMultipartFile)multfile;   
        //这个myfile是MultipartFile的  
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();  
        File file = fi.getStoreLocation();  
        //手动创建临时文件  
        if(null!=multfile||!multfile.equals("")){  
            File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +   
                    file.getName());  
            multfile.transferTo(tmpFile);  
            return tmpFile;  
        }  
        return file;  
    }
    
    
    


    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return true;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }

    /**
     * 图片转化成base64字符串
     * @param imgFile
     * @return
     */
    public static String getImageStr(String imgFile)
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
       // String imgFile = "D:\\a.jpg";//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    /**
     * base64字符串转化成图片
     * @param imgStr
     * @param imgFilePath
     * @return
     */
    public static boolean generateImage(String imgStr,String imgFilePath)
    {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) { //图像数据为空
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            // String imgFilePath =filePath+fileName;// "D:\\new.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }




}
