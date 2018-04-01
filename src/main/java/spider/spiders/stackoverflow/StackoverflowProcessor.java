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
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
    //判断是不是目录
    private String content_regex = ".+?search.+";

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
                // page.addTargetRequest(domain + str);
                request.setExtras(page.getRequest().getExtras());
                page.addTargetRequest(request);
            }
            page.addTargetRequest(domain + next);

        } else {
            String title = html.xpath("//div[@id='question-header']/h1/a").get();
            List<String> qas = html.xpath("//div[@class='post-text']").all();
            // 标题
            String title_p = html.xpath("//div[@id='question-header']/h1/a/text()").get();
            // 第一个是问题描述 其余的是答案
            List<String> qas_p = html.xpath("//div[@class='post-text']/allText()").all();

            List<String> fragments = reConstruct(title, qas);
            List<String> fragmentsPureText = reConstruct(title_p, qas_p);
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
