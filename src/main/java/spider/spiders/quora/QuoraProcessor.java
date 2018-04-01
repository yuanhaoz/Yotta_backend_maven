package spider.spiders.quora;

import app.Config;
import spider.spiders.webmagic.FragmentContent;
import spider.spiders.webmagic.ProcessorSQL;
import spider.spiders.webmagic.SqlPipeline;
import spider.spiders.webmagic.YangKuanSpider;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import utils.Translate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuoraProcessor implements PageProcessor{

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
    public Site getSite() {
        return site;
    }
    public void process(Page page) {
        Html html = page.getHtml();
        List<String> fragments = html.xpath("div[@class='truncated_q_text search_result_snippet']").all();
        List<String> fragments_p = html.xpath("div[@class='truncated_q_text search_result_snippet']/allText()").all();
        FragmentContent fragmentContent = new FragmentContent(fragments, fragments_p);
        //提交数据
        page.putField("fragment",fragmentContent);
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
                    + facetInformation.get("FacetName");
            //添加链接;设置额外信息
            facetInformation.put("SourceName", "Quora");
            requests.add(request.setUrl(url).setExtras(facetInformation));
        }

        YangKuanSpider.create(new QuoraProcessor())
                .addRequests(requests)
                .thread(5)
                .addPipeline(new SqlPipeline())
                .runAsync();
    }

//    public static void main(String[] args) {
//        new QuoraProcessor().quoraAnswerCrawl("test");
//    }
}
