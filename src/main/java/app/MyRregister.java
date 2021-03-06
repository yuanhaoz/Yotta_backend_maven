package app;
/**
 * 
 * @author 郑元浩
 * @date 2016年11月15日08:45:02
 * @description 注册api的类
 * 
 */

import assemble.AssembleAPI;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import dependency.DependencyAPI;
import domain.DomainAPI;
import domainTopic.DomainTopicAPI;
import facet.FacetAPI;
import io.swagger.jaxrs.config.BeanConfig;
import login.LoginAPI;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import questionQuality.QuestionQualityAPI;
import source.SourceAPI;
import spider.SpiderAPI;
import statistics.StatisticsAPI;
import subject.SubjectAPI;

import java.util.HashSet;
import java.util.Set;

public class MyRregister extends ResourceConfig {
    public MyRregister() {
		/**
		 * swagger文件信息,swagger  注册服务 。 调试地址：***
		 */
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setTitle("Yotta知识森林模块 集成API");
		beanConfig.setVersion("1.0.5");
		beanConfig.setSchemes(new String[]{"http"});
		beanConfig.setHost(Config.SWAGGERHOST);  // 需要修改
		beanConfig.setBasePath(Config.SWAGGERBASEPATH);  // 需要修改
		beanConfig.setLicense(getApplicationName());
		beanConfig.setContact("郑元浩       Email：994303805@qq.com");
		beanConfig.setResourcePackage("domain,domainTopic,facet,spider,assemble,dependency,statistics,subject,source,questionQuality");  // 需要修改
		beanConfig.setScan(true);
		//swagger  注册服务
		Set<Class<?>> resources = new HashSet<>();
		resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        registerClasses(resources);


		//加载拦截器
		register(CorsFilter.class);//自己实现过滤器来实现

		/**
		 * 	下面的是个人资源类      Spider  郑元浩
		 */
		register(DomainAPI.class);
		register(DomainTopicAPI.class);
		register(FacetAPI.class);
		register(SpiderAPI.class);
		register(AssembleAPI.class);
		register(DependencyAPI.class);
		register(MultiPartFeature.class);
		register(JacksonJsonProvider.class);
		register(StatisticsAPI.class);
		register(SubjectAPI.class);
		register(SourceAPI.class);
		register(QuestionQualityAPI.class);


		/**
		 * 用户登陆
		 */
		register(LoginAPI.class);

    }
}  