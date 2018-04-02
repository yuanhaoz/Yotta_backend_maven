package spider.spiders.stackoverflow;

import app.Config;
import spider.spiders.webmagic.FragmentContent;
import spider.spiders.webmagic.ProcessorSQL;
import spider.spiders.webmagic.SqlPipeline;
import spider.spiders.webmagic.YangKuanSpider;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import utils.Translate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StackoverflowProcessor implements PageProcessor {
//    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000)
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
            .addHeader("Origin", "https://stackoverflow.com")
            .addHeader("Hosts", "as-sec.casalemedia.com")
            .addHeader("Accept", "*/*");
    //判断是不是目录
    private String content_regex = ".+?search\\?q=.+";

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        String domain = "https://stackoverflow.com";

        // 如果是目录
        if (page.getUrl().regex(content_regex).match()) {
            // 每一页的链接
            List<String> questions = html.xpath("//div[@class='result-link']//a/@href").all();
            // 下一页
            String next = html.xpath("//a[@rel='next']/@href").get();
            // 加入队列
            for (String str : questions) {
                Request request = new Request();
                request.setUrl(domain + str);
                request.setExtras(page.getRequest().getExtras());
                page.addTargetRequest(request);
            }
            Map<String,Object> extras = page.getRequest().getExtras();
            int currentPage = (int)extras.get("page");
            // 页面多于5个不再爬取
            if (currentPage < 5)
            {
                extras.put("page", currentPage + 1);
                Request request = new Request();
                request.setUrl(domain + next);
                request.setExtras(extras);
                page.addTargetRequest(request);
            }


        } else {
            String title = html.xpath("//div[@id='question-header']/h1/a").get();
            List<String> qas = html.xpath("//div[@class='post-text']").all();
            // 标题
            String title_p = html.xpath("//div[@id='question-header']/h1/a/text()").get();
            // 第一个是问题描述 其余的是答案
            List<String> qas_p = html.xpath("//div[@class='post-text']/allText()").all();

            List<String> fragments = new ArrayList<>();
            List<String> fragmentsPureText = new ArrayList<>();
            if (qas.size() > 0)
            {
                fragments.add(title + "\n" + qas.get(0));
                fragmentsPureText.add(title_p + "\n" + qas_p.get(0));
            }
            FragmentContent fragmentContent = new FragmentContent(fragments, fragmentsPureText);
            page.putField("fragmentContent", fragmentContent);

        }

    }

    public void StackoverflowCrawl(String courseName) {
        //1.获取分面名
        ProcessorSQL processorSQL = new ProcessorSQL();
        List<Map<String, Object>> allFacetsInformation = processorSQL.getAllFacets(Config.FACET_TABLE, courseName);
        //2.添加连接请求
        List<Request> requests = new ArrayList<>();
        for (Map<String, Object> facetInformation : allFacetsInformation) {
            Request request = new Request();

//            String termName = Translate.translateCE2En((String) facetInformation.get("TermName"));
//            String facetName = Translate.translateCE2En((String) facetInformation.get("FacetName"));

            String url = "https://stackoverflow.com/search?q="
                    + facetInformation.get("TermName") + " "
                    + facetInformation.get("FacetName");
            //添加链接;设置额外信息
            facetInformation.put("page", 1);
            facetInformation.put("SourceName", "Stackoverflow");
            requests.add(request.setUrl(url).setExtras(facetInformation));
        }

        YangKuanSpider.create(new StackoverflowProcessor())
                .addRequests(requests)
                .thread(Config.THREAD)
                .addPipeline(new SqlPipeline())
//                .addPipeline(new ConsolePipeline())
                .runAsync();

    }

    // ceshi
//    public static void main(String[] args) {
//        new StackoverflowProcessor().StackoverflowCrawl("test");
//    }


    private List<String> reConstruct(String title, List<String> qas) {
        List<String> ans = new ArrayList<>();
        int len = qas.size();
        for (int i = 1; i < len; ++i) {
            ans.add(title + "\n" + qas.get(0) + "\n" + qas.get(i));
        }
        return ans;
    }

    @Override
    public Site getSite() {
        return site;
    }
}
