import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import javax.swing.text.Style;
import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by khk on 2017-01-12.
 */
public class Main {
    public static void main(String[] args) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter writer = null;
        try {


            writer = PdfWriter.getInstance(document, new FileOutputStream("READMEforTypora.pdf"));
            document.open();
            XMLWorkerHelper helper = XMLWorkerHelper.getInstance();

            //CSS
            StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver();


            //HTML
            XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            fontProvider.register("./font/NanumBarunGothic.ttf", "Open Sans");
            CssAppliersImpl cssAppliers = new CssAppliersImpl(fontProvider);

            HtmlPipelineContext context = new HtmlPipelineContext(cssAppliers);
            context.setTagFactory(Tags.getHtmlTagProcessorFactory());

            //Pipelines
            PdfWriterPipeline pipeline = new PdfWriterPipeline(document, writer);
            HtmlPipeline htmlPipeline = new HtmlPipeline(context, pipeline);
            CssResolverPipeline cssPipeline = new CssResolverPipeline(cssResolver, htmlPipeline);

            XMLWorker xmlWorker = new XMLWorker(cssPipeline, true);
            XMLParser xmlParser = new XMLParser(xmlWorker, Charset.forName("UTF-8"));

            //

            File file = new File("src/main/resources/READMEforTypora.html");
            System.out.println(file.getAbsolutePath());
            FileInputStream fileReader = new FileInputStream("src/main/resources/READMEforTypora.html");
            xmlParser.parse(fileReader);

            System.out.println(document.toString());

            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
