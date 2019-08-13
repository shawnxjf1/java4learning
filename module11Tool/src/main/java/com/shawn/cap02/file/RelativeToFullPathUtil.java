package com.shawn.cap02.file;

import java.io.File;
import java.util.regex.Matcher;

public class RelativeToFullPathUtil {
    String  fileSeparator = System.getProperty("file.separator");

    public static String getFullPathByPackage(Class clz,String fileName)
    {
        String packageUrl = clz.getPackage().getName();
        String packagePath = packageConvertPath(packageUrl);
        String userDir = System.getProperty("user.dir");
        String fileSeparator = System.getProperty("file.separator");
        return userDir + fileSeparator+ "src" +fileSeparator + "test" + fileSeparator + "java" + packagePath +fileSeparator + fileName;
    }

    /**
     * 自定义方法
     * 将获取到的包路径中的点号换成斜杠
     * @param packageName 传入的包路径
     * @return 路径前后都加上斜杠中间也替换成斜杠返回
     */
    public static String packageConvertPath(String packageName) {
        String fileSeparator = System.getProperty("file.separator");
/**
 * 错误的写法，参考：https://www.liangzl.com/get-article-detail-39961.html<br>
 * return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
 */
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", Matcher.quoteReplacement(File.separator)) : packageName);
    }

    public static String getPackagePath(Class clz)
    {
        String packageUrl = clz.getPackage().getName();
        String packagePath = packageConvertPath(packageUrl);
        return packagePath;
    }
}
