package com.shawn.html2pdf;

import java.io.*;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.net.FileRetrieve;
import com.itextpdf.tool.xml.net.ReadingProcessor;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.*;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName HtmlConvPdf
 * @Date 2019/5/23 15:51
 * @Description 【html摸版转pdf】
 */
@Slf4j
@Component
public class HtmlConvPdf {

    /**
     * @param fontPath:字体路径
     * @param htmlStr:html string内容
     * @param outFilePath: 输出pdf路径
     * @return void :
     * @Description 【生成pdf】
     */
    public static void createPdf(String htmlStr, String outFilePath,String fontPath) throws IOException, DocumentException {
        final String charsetName = "UTF-8";

        htmlStr = JsoupUtils.clearHtmlToXhtml(htmlStr);

        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        document.setMargins(30, 30, 30, 30);

        OutputStream out = new FileOutputStream(outFilePath);
        PdfWriter writer = PdfWriter.getInstance(document, out);
        document.open();

        // html内容解析 使用系统字体
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(
                new CssAppliersImpl(new XMLWorkerFontProvider() {
                    @Override
                    public Font getFont(String fontname, String encoding, float size, final int style) {
                        Font font = null;
                        if (fontname == null) {
                            String fontCn = getChineseFont(fontPath);
                            BaseFont bf;
                            try {
                                bf = BaseFont.createFont(fontCn+",1",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                                font = new Font(bf, size, style);
                            } catch (DocumentException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        return font;
                    }
                })) {
                    @Override
                    public HtmlPipelineContext clone()
                            throws CloneNotSupportedException {
                        HtmlPipelineContext context = super.clone();
                        try {
                            ImageProvider imageProvider = this.getImageProvider();
                            context.setImageProvider(imageProvider);
                        } catch (NoImageProviderException e) {
                        }
                        return context;
                    }
                };

        // 图片解析
        htmlContext.setImageProvider(new AbstractImageProvider() {

            public String getImageRootPath() {
                return "";
            }

            @Override
            public Image retrieve(String src) {
                if (StringUtils.isEmpty(src)) {
                    return null;
                }
                try {
                    Image image = Image.getInstance(new File(src).toURI().toString());
                    image.setAbsolutePosition(400, 400);
                    if (image != null) {
                        store(src, image);
                        return image;
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                return super.retrieve(src);
            }
        });

        htmlContext.setAcceptUnknown(true).autoBookmark(true).setTagFactory(Tags.getHtmlTagProcessorFactory());

        // css解析
        CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
        cssResolver.setFileRetrieve(new FileRetrieve() {

            public void processFromStream(InputStream in,ReadingProcessor processor) throws IOException {
                try {
                    InputStreamReader reader = new InputStreamReader(in, charsetName);
                    int i = -1;
                    while (-1 != (i = reader.read())) {
                        processor.process(i);
                    }
                } catch (Throwable e) {
                }
            }

            // 解析href
            public void processFromHref(String href, ReadingProcessor processor) throws IOException {
                InputStream is = HtmlConvPdf.class.getResourceAsStream("/" + href);
                try{
                    InputStreamReader reader = new InputStreamReader(is,charsetName);
                    int i = -1;
                    while (-1 != (i = reader.read())) {
                        processor.process(i);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });

        HtmlPipeline htmlPipeline = new HtmlPipeline(htmlContext, new PdfWriterPipeline(document, writer));
        Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
        XMLWorker worker = null;
        worker = new XMLWorker(pipeline, true);
        XMLParser parser = new XMLParser(true, worker, Charset.forName(charsetName));
        try {
            InputStream inputStream = new ByteArrayInputStream(htmlStr.getBytes());
            parser.parse(inputStream, Charset.forName(charsetName));
        }catch (Exception e){
            e.getStackTrace();
            System.out.println(e.getMessage());
        }
        document.close();
    }



    //获取字体
    private static String getChineseFont(String fontPath) {
        String chineseFont = fontPath;
        if(!new File(chineseFont).exists()){
            throw new RuntimeException("字体文件不存在,影响导出pdf中文显示！"+chineseFont);
        }

        return chineseFont;
    }
}
