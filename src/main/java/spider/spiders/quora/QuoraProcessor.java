package spider.spiders.quora;

import app.Config;
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
import utils.Log;
import utils.Translate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuoraProcessor implements PageProcessor{

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    public Site getSite() {
        return site;
    }

    //判断是不是目录
    private String content_regex = ".+?search\\?q=.+";
    private String domain = "https://www.quora.com";
    // 下拉式加载 只爬取当前页面的所有答案
    public void process(Page page) {
        Html html = page.getHtml();

        if (page.getUrl().regex(content_regex).match())
        {
            List<String> links = html.xpath("//a[@class='question_link']/@href").all();
            Log.log("-->" + links.size());
            for (String l : links) {
                Request request = new Request();
                request.setUrl(domain + l);
                request.setExtras(page.getRequest().getExtras());
                page.addTargetRequest(request);
            }
        }
        else
        {
            String title_p = html.xpath("//span[@class='QuestionText']/span[@class='rendered_qtext']/text()").get();
            List<String> answers_p = html.xpath("//div[@class='ui_qtext_expanded']/allText()").all();

            String title = html.xpath("//span[@class='QuestionText']/span[@class='rendered_qtext']").get();
            List<String> answers = html.xpath("//div[@class='ui_qtext_expanded']").all();

            List<String> fragments = new ArrayList<>();
            List<String> fragmentsPureText = new ArrayList<>();
            if (answers.size() > 0)
            {
                fragments.add(title + "\n" + answers.get(0));
                fragmentsPureText.add(title_p + "\n" + answers_p.get(0));
            }
            FragmentContent fragmentContent = new FragmentContent(fragments, fragmentsPureText);
            page.putField("fragment",fragmentContent);
        }


    }
    public void quoraAnswerCrawl(String courseName){

        //1.获取分面名
        ProcessorSQL processorSQL = new ProcessorSQL();
        List<Map<String, Object>> allFacetsInformation = processorSQL.getAllFacets(Config.FACET_TABLE,courseName);
        //2.添加连接请求
        List<Request> requests = new ArrayList<Request>();
        for(Map<String, Object> facetInformation:allFacetsInformation){
            Request request = new Request();

            String url = "https://www.quora.com/search?q="
                    //+facetInformation.get("ClassName")+" "
                    + facetInformation.get("TermName") + " "
                    + facetInformation.get("FacetName")
                    + "&type=question";
            //添加链接;设置额外信息
            facetInformation.put("SourceName", "Quora");
            requests.add(request.setUrl(url).setExtras(facetInformation));
        }

        YangKuanSpider.create(new QuoraProcessor())
                .addRequests(requests)
                .thread(Config.THREAD)
                .addPipeline(new SqlPipeline())
//                .addPipeline(new ConsolePipeline())
                .runAsync();
    }

    private List<String> reConstruct(String title, List<String> qas) {
        List<String> ans = new ArrayList<>();
        int len = qas.size();
        for (int i = 0; i < len; ++i) {
            ans.add(title + "\n" + qas.get(i));
        }
        return ans;
    }

//    public static void main(String[] args) {
//        new QuoraProcessor().quoraAnswerCrawl("Source_code_generation");
//    }
}
