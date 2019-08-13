package com.shawn.cap02.file;

import cn.hutool.core.io.FileUtil;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Properties;

/**
 *
 */
public class SystemPropertiesTest {

    @Test
     public void testPrintSystemProperties()
     {
         Properties properties = System.getProperties();
         Enumeration pnames = properties.propertyNames();
         while (pnames.hasMoreElements()) {
             String pname = (String) pnames.nextElement();
             System.out.print(pname + ":");
             System.out.println(properties.getProperty(pname));
         }
         /**
          * user.dir:D:\500-java\j2seCode\module11Tool  工程当前目录<br>
          * 你也可以通过relativelyPath=System.getProperty("user.dir");来执行<br>
          * 对于不同平台我们可以通过user.dir + 包名来获取真实路径;<br>
          */
     }

     @Test
     public void testFileDefaultPath(){
        //new File("src/test/java")默认从工程路径下
         File f=new File("src/test/java/" + RelativeToFullPathUtil.getPackagePath(SystemPropertiesTest.class) + SystemPropertiesTest.class.getSimpleName() + ".java");
         String fileStr = FileUtil.readString(f, Charset.forName("UTF-8"));
         System.out.println(fileStr);
         /**
          * 执行成功，把java文件内容打印出来了<br>
          * 注意：SystemPropertiesTest.class.getSimpleName()打印简单的文字<br>
          *     SystemPropertiesTest.class.getName()打印带有package路径的文件名<br>
          */
     }
}
