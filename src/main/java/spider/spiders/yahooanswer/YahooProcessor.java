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
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YahooProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    private String content_regex = ".+?search.+";


    @Override
    public void process(Page page) {
        Html html = page.getHtml();

        // 如果是目录
        if (page.getUrl().regex(content_regex).match()) {
            List<String> qlinks = html.xpath("//a[@class=' lh-17 fz-m']/@href").all();
            String nextPage = html.xpath("//a[@class='next']/@href").get();

            for (String str :
                    qlinks) {
                Request request = new Request();
                request.setUrl(str);
                request.setExtras(page.getRequest().getExtras());
                page.addTargetRequest(request);
            }
            Request request = new Request();
            request.setUrl(nextPage);
            request.setExtras(page.getRequest().getExtras());
            page.addTargetRequest(request);

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

            List<String> fragments = reConstruct(title, question, answers);
            List<String> fragmentsPureText = reConstruct(title_p, question_p, answers_p);
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
            requests.add(request.setUrl(url).setExtras(facetInformation));
        }

        YangKuanSpider.create(new YahooProcessor())
                .addRequests(requests)
                .thread(5)
                .addPipeline(new SqlPipeline())
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
