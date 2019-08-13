package com.shawn.cap02.file;

import org.junit.Test;

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
}
