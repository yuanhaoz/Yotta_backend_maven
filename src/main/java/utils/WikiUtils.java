package utils;

import domain.bean.Domain;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import spider.spiders.SpidersRun;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author yuanhao
 * @date 2018/4/1 21:02
 */
public class WikiUtils {

    public static void main(String[] args) {
        try {
            getDomainsEnFromDomainsCn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 翻译中文维基课程名为英文
     * @throws Exception
     */
    public static void getDomainsEnFromDomainsCn() throws Exception {
        String targetExcelPath = "F:\\domains-en.xls";
        WritableWorkbook workbook = Workbook.createWorkbook(new File(targetExcelPath));
        WritableSheet sheet = workbook.createSheet("英文维基",0);
        Label label1 = new Label(0, 0, "学科");
        Label label2 = new Label(1, 0, "课程名");
        sheet.addCell(label1);
        sheet.addCell(label2);
        int row = 1;

        String excelPath = SpidersRun.class.getClassLoader().getResource("").getPath() + "domains.xls";
        List<Domain> domainList = SpidersRun.getDomainFromExcel(excelPath);
        for (int i = 0; i < domainList.size(); i++) {
            Domain domain = domainList.get(i);
            String domainName = domain.getClassName();
            String domain_url = "https://zh.wikipedia.org/wiki/Category:" + URLEncoder.encode(domainName ,"UTF-8");

            String html = SpiderUtils.seleniumWikiCN(domain_url); // Selenium方式获取
            Document doc = JsoupDao.parseHtmlText(html);
            String domain_en = "";

            Elements elements = doc.select("#p-lang");
            if (elements.size() > 0) {
//                System.out.println(elements);
                Elements elements1 = elements.select("div.body").select("li").select("a[lang=en]");
                if (elements1.size() > 0) {
                    domain_en = elements1.get(0).attr("href").split("Category:")[1];
                    Label labelDomain = new Label(1, row, domain_en);
                    sheet.addCell(labelDomain);
                    row++;
                }
            }
            System.out.println(domain_en);
        }
        workbook.write();
        workbook.close();
    }
}
