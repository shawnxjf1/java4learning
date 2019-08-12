package com.shawn.html2pdf;

import java.io.*;
import java.nio.charset.Charset;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFilesImpl;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.HTML;
import com.itextpdf.tool.xml.html.TagProcessorFactory;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HtmlConvPdfWithBase64 {
    public static void convertHtmlToPdf(String sourceFilePath, String destFilePath, String fontPath) {
        try {
            final OutputStream file = new FileOutputStream(new File(destFilePath));
            final Document document = new Document();
            final PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
            final TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
            tagProcessorFactory.removeProcessor(HTML.Tag.IMG);
            tagProcessorFactory.addProcessor(new ImageTagProcessor(), HTML.Tag.IMG);

            final CssFilesImpl cssFiles = new CssFilesImpl();
            cssFiles.add(XMLWorkerHelper.getInstance().getDefaultCSS());
            final StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver(cssFiles);
            final HtmlPipelineContext hpc =
                new HtmlPipelineContext(new CssAppliersImpl(new HtmlConvPdfFontProvider(fontPath)));
            hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
            final HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, new PdfWriterPipeline(document, writer));
            final Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
            final XMLWorker worker = new XMLWorker(pipeline, true);
            final Charset charset = Charset.forName("UTF-8");
            final XMLParser xmlParser = new XMLParser(true, worker, charset);

            String htmlStr = FileUtil.readString(sourceFilePath, "UTF-8");
            //把html标签转换成xhtml,ItextPdf5会对标签严格校验<br>
            htmlStr = JsoupUtils.clearHtmlToXhtml(htmlStr);

            InputStream inputStream = new ByteArrayInputStream(htmlStr.getBytes());
            xmlParser.parse(inputStream, charset);

            // 2019-7-30 比对了一下HtmlConvPdf，确定了配置整体差不多，比如配置了tagProcessorFactory，
            document.close();
            file.close();
        } catch (Exception e) {
            log.error("error occur when handle htmlConfPdf",e);
        }
        //java 1.7之后不需要手动写finally来释放资源了<br>
    }

    public static void convertHtmlStrToPdf(String htmlStr, String destFullPdfFileName, String fontPath) {
        try {
            final OutputStream file = new FileOutputStream(new File(destFullPdfFileName));
            final Document document = new Document();
            final PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
            final TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
            tagProcessorFactory.removeProcessor(HTML.Tag.IMG);
            tagProcessorFactory.addProcessor(new ImageTagProcessor(), HTML.Tag.IMG);

            final CssFilesImpl cssFiles = new CssFilesImpl();
            cssFiles.add(XMLWorkerHelper.getInstance().getDefaultCSS());
            final StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver(cssFiles);
            final HtmlPipelineContext hpc =
                    new HtmlPipelineContext(new CssAppliersImpl(new HtmlConvPdfFontProvider(fontPath)));
            hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
            final HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, new PdfWriterPipeline(document, writer));
            final Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
            final XMLWorker worker = new XMLWorker(pipeline, true);
            final Charset charset = Charset.forName("UTF-8");
            final XMLParser xmlParser = new XMLParser(true, worker, charset);

            //把html标签转换成xhtml,ItextPdf5会对标签严格校验<br>
            htmlStr = JsoupUtils.clearHtmlToXhtml(htmlStr);

            InputStream inputStream = new ByteArrayInputStream(htmlStr.getBytes());
            xmlParser.parse(inputStream, charset);

            // 2019-7-30 比对了一下HtmlConvPdf，确定了配置整体差不多，比如配置了tagProcessorFactory，
            document.close();
            file.close();
        } catch (Exception e) {
            log.error("error occur when handle htmlConfPdf",e);
        }
        //java 1.7之后不需要手动写finally来释放资源了<br>
    }
}