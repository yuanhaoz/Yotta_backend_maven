package spider.spiders.yahooanswer;

import app.Config;
import spider.spiders.stackoverflow.StackoverflowProcessor;
import spider.spiders.webmagic.FragmentContent;
import spider.spiders.webmagic.ProcessorSQL;
import spider.spiders.webmagic.SqlPipeline;
import spider.spiders.webmagic.YangKuanSpider;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YahooProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
    private String content_regex = ".+?search\\?p=.+";


    @Override
    public void process(Page page) {
        Html html = page.getHtml();

        // 如果是目录
        if (page.getUrl().regex(content_regex).match()) {
            List<String> qlinks = html.xpath("//a[@class=' lh-17 fz-m']/@href").all();
            String nextPage = html.xpath("//a[@class='next']/@href").get();

            // 内容链接
            for (String str :
                    qlinks) {
                Request request = new Request();
                request.setUrl(str);
                request.setExtras(page.getRequest().getExtras());
                page.addTargetRequest(request);
            }

            // 下一页链接
            // 页面多于5个不再爬取
            Map<String, Object> extras = page.getRequest().getExtras();
            int currentPage = (int) extras.get("page");
            if (currentPage < 5) {
                extras.put("page", currentPage + 1);
                Request request = new Request();
                request.setUrl(nextPage);
                request.setExtras(extras);
                page.addTargetRequest(request);
            }


        } else {
            // 雅虎问答的标题
            String title_p = html.xpath("//h1[@itemprop='name']/text()").get();
            String title = html.xpath("//h1[@itemprop='name']").get();
            // 答案
            List<String> answers_p = html.xpath("//span[@class='ya-q-full-text'][@itemprop]/text()").all();
            List<String> answers = html.xpath("//span[@class='ya-q-full-text'][@itemprop]").all();
            // 问题描述
            String question_p = html.xpath("//span[@class='D-n ya-q-full-text Ol-n']/text()").get();
            String question = html.xpath("//span[@class='D-n ya-q-full-text Ol-n']").get();
            if (question_p == null || question == null) {
                question_p = html.xpath("//span[@class='ya-q-text']/text()").get();
                question = html.xpath("//span[@class='ya-q-text']").get();
            }

            List<String> fragments = new ArrayList<>();
            List<String> fragmentsPureText = new ArrayList<>();
            if (answers.size() > 0) {
                fragments.add(title + "\n" + question + "\n" + answers.get(0));
                fragmentsPureText.add(title_p + "\n" + question_p + "\n" + answers_p.get(0));
            }
            FragmentContent fragmentContent = new FragmentContent(fragments, fragmentsPureText);
            page.putField("fragmentContent", fragmentContent);


        }

    }

    public void YahooCrawl(String courseName) {
        //1.获取分面名
        ProcessorSQL processorSQL = new ProcessorSQL();
        List<Map<String, Object>> allFacetsInformation = processorSQL.getAllFacets(Config.FACET_TABLE, courseName);
        //2.添加连接请求
        List<Request> requests = new ArrayList<>();
        for (Map<String, Object> facetInformation : allFacetsInformation) {
            Request request = new Request();

//            String termName = Translate.translateCE2En((String) facetInformation.get("TermName"));
//            String facetName = Translate.translateCE2En((String) facetInformation.get("FacetName"));

            String url = "https://answers.search.yahoo.com/search?p="
                    + facetInformation.get("TermName") + " "
                    + facetInformation.get("FacetName");
            //添加链接;设置额外信息
            facetInformation.put("SourceName", "Yahoo");
            facetInformation.put("page", 1);
            requests.add(request.setUrl(url).setExtras(facetInformation));
        }

        YangKuanSpider.create(new YahooProcessor())
                .addRequests(requests)
                .thread(Config.THREAD)
                .addPipeline(new SqlPipeline())
//                .addPipeline(new ConsolePipeline())
                .runAsync();

    }

//    public static void main(String[] args) {
//        new YahooProcessor().YahooCrawl("test");
//    }

    private List<String> reConstruct(String title, String description, List<String> answers) {
        List<String> ans = new ArrayList<>();
        int len = answers.size();
        for (int i = 0; i < len; ++i) {
            ans.add(title + "\n" + description + "\n" + answers.get(i));
        }
        return ans;
    }

    @Override
    public Site getSite() {
        return site;
    }
}
