package com.smartframe.basics.util;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONException;

/**
 * <p><b>Title:</b><i>${TODO}</i></p>
 * <p>Desc: ${TODO}</p>
 * <p>source folder:{@docRoot}</p>
 * <p>Copyright:Copyright(c)2014</p>
 * <p>Company:meizu</p>
 * <p>Create Date:2017/12/4 13:37</p>
 * <p>Modified By:gavin.chen</p>
 * <p>Modified Date:2017/12/4 13:37</p>
 *
 * @author <a href="mailto:qigeng@meizu.com">gavin.chen</a>
 * @version Version 0.1
 */
public class RedPackUtil {

    /**
     * 获取订单号
     * @return
     */
    public static String getOrderNum(){

        return String.valueOf(System.currentTimeMillis())+getRandom4();

    }


    /**
     * 获取一个4位随机数
     * @return
     */
    public static String getRandom4(){
        Integer code=0;
        for(int i=0; i<4; i++) {
            code += (int)(Math.random() * 9);
        }
        return code.toString();
    }

    /**
     * 获取UUID
     * @return
     */
    public static String returnUUID() {
        return parseStrToMd5L32(UUID.randomUUID().toString());
    }

    /**
     * 获取md5
     * @param str
     * @return
     */
    public static String parseStrToMd5L32(String str) {
        return parseStrToMd5L32(str,"utf-8");
    }

    public static String parseStrToMd5L32(String str,String charset) {
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes(charset));
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return reStr;
    }

    /**
     * 将map转换成url
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        map = new TreeMap<String, String>(map);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if(entry.getValue() == null){
                continue;
            }
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }

    /**
     * Sha1加密方法
     */
    public static String getSha1(String decript) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static Map toMap(Object bean) {
        Class<? extends Object> clazz = bean.getClass();
        Map<Object, Object> returnMap = new HashMap<Object, Object>();
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = null;
                    result = readMethod.invoke(bean, new Object[0]);
                    if (null != propertyName) {
                        propertyName = propertyName.toString();
                    }
                    if (null != result) {
                        result = result.toString();
                        returnMap.put(propertyName, result);
                    }
                }
            }
        } catch (IntrospectionException e) {
            System.out.println("分析类属性失败");
        } catch (IllegalAccessException e) {
            System.out.println("实例化 JavaBean 失败");
        } catch (IllegalArgumentException e) {
            System.out.println("映射错误");
        } catch (InvocationTargetException e) {
            System.out.println("调用属性的 setter 方法失败");
        }
        return returnMap;
    }

    /**
     * 凑成xml格式字符串
     *
     * @return
     */
    public static String getSoapRequestData(Map<String, String> map,boolean isAddCDATA) throws JSONException {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (isAddCDATA) {
                sb.append("<"+entry.getKey()+">"+"<![CDATA["+entry.getValue()+"]]></"+entry.getKey()+">");
            }else {
                sb.append("<"+entry.getKey()+">"+entry.getValue()+"</"+entry.getKey()+">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }


    public static Map<String, String> parseXml2Map(String msg) {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 从request中取得输入流
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(msg.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList){
              map.put(e.getName(), e.getText());
         }
        // 释放资源
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputStream = null;
        return map;
    }





}
