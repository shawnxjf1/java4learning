package com.shawn.cap01.html2pdf;

import cn.hutool.core.date.DateUtil;
import com.shawn.cap02.file.RelativeToFullPathUtil;
import com.shawn.cap021.html2pdf.HtmlConvPdfWithBase64;
import org.junit.Test;

import java.util.Date;

public class HtmlConvPdfWithBase64Test {
    /**
     * 文字和图片都可以出来<br>
     */
    @Test
    public void testHtmlConvToPdfWithBase64() {

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<");
        String fontPath =  RelativeToFullPathUtil.getFullPathByPackage(HtmlConvPdfWithBase64Test.class,"SIMSUN.TTC");
        String sourcePath =  RelativeToFullPathUtil.getFullPathByPackage(HtmlConvPdfWithBase64Test.class,"htmlConvPdfTest.html");
        String pdfPath = RelativeToFullPathUtil.getFullPathByPackage(HtmlConvPdfWithBase64Test.class, DateUtil.format(new Date(),"yyyyMMddHHmmss") + ".pdf");
        // 加上font才能够把中文输出出来<br>
        HtmlConvPdfWithBase64.convertHtmlToPdf(sourcePath, pdfPath, fontPath);
        /**
         * 1. fontPath :D:\500-java\j2seCode\module11Tool\src\test\java/com/shawn/cap01/html2pdf/\SIMSUN.TTC 里面有/也有\不影响文件生成<br>
         * 2.该测试用例是可以生成pdf*
         */


        /**
         * 待完善，log4j.xml还没有配置上<br>
         */
    }
}
