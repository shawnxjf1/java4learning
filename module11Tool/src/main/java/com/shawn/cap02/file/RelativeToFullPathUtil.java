package com.shawn.cap02.file;

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
    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }
}
