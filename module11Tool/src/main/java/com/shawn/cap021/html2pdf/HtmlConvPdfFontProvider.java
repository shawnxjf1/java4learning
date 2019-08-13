package com.shawn.cap021.html2pdf;

import java.io.File;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;

public class HtmlConvPdfFontProvider extends XMLWorkerFontProvider {
    String fontPath = "";

    public HtmlConvPdfFontProvider(String fontPath) {
        super();
        this.fontPath = fontPath;
    }

    @Override
    public Font getFont(String fontname, String encoding, float size, final int style) {
        Font font = null;
        if (fontname == null) {
            String fontCn = getChineseFont(fontPath);
            BaseFont bf;
            try {
                bf = BaseFont.createFont(fontCn + ",1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                font = new Font(bf, size, style);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return font;
    }

    // 获取字体
    private static String getChineseFont(String fontPath) {
        String chineseFont = fontPath;
        if (!new File(chineseFont).exists()) {
            throw new RuntimeException("字体文件不存在,影响导出pdf中文显示！" + chineseFont);
        }
        return chineseFont;
    }
}
