import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;
import sourceStudy.springSourceStudy.com.conversionService.StringToDateConverter;
import sourceStudy.springSourceStudy.com.listener.TestEvent;
import sourceStudy.springSourceStudy.com.model.HelloMessage;
import sourceStudy.springSourceStudy.com.model.MyTestBean;
import sourceStudy.springSourceStudy.com.model.SimpleBean;
import sourceStudy.springSourceStudy.com.model.TestFb;

import java.util.Date;

@SuppressWarnings("deprecation")
public class BeanFactoryTest {
    @Test
    public void testSimpleLoad(){
        BeanFactory bf = new XmlBeanFactory(new ClassPathResource("beanFactoryTest.xml"));
        MyTestBean myTestBean = (MyTestBean) bf.getBean("myTestBean");
        Assert.hasText("testStr",myTestBean.getTestStr());
    }

    @Test
    public void testCircleByConstructor() throws Throwable{
        try {
            new ClassPathXmlApplicationContext("beanFactoryTest.xml");
        }catch (Exception e){
            throw e.getCause().getCause().getCause();
        }
    }

    @Test
    public void testBeanFactory(){
        // type:1
        // 解析bean并注册进beanFactory
//        BeanFactory bf = new XmlBeanFactory(new ClassPathResource("beanFactoryTest.xml"));
        //获取bean的实例
//        HelloMessage message = (HelloMessage) bf.getBean("message");
//        System.out.println(message.getMes());
        // type:1 只是加loadbean 并获取，没有进行beanFactory后处理
        // mesage.getMes()的结果为变量${bean.message}，没有进行转换

        //type:2
        ApplicationContext context =  new ClassPathXmlApplicationContext("beanFactoryTest.xml");
        HelloMessage message = (HelloMessage) context.getBean("message");
        System.out.println(message.getMes());
    }

    @Test
    public void testCustomBeanFactory(){
        ApplicationContext context =  new ClassPathXmlApplicationContext("beanFactoryTest.xml");
        SimpleBean simpleBean = (SimpleBean) context.getBean("simpleBean");
        System.out.println(JSON.toJSONString(simpleBean));
    }

    @Test
    public void testListener(){
        ApplicationContext context =  new ClassPathXmlApplicationContext("beanFactoryTest.xml");

        TestEvent event = new TestEvent(context,"listener test");
        context.publishEvent(event);
    }

    @Test
    public void testConverter(){
//        ApplicationContext context =  new ClassPathXmlApplicationContext("beanFactoryTest.xml");

        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToDateConverter());

        String dateStr = "2018-08-08 10:08:08";
        Date date =conversionService.convert(dateStr,Date.class);
        System.out.println(date);
    }

    @Test
    public void testAop(){
        ApplicationContext context =  new ClassPathXmlApplicationContext("application.xml");

        MyTestBean myTestBean = (MyTestBean) context.getBean("myTestBean");

        myTestBean.test();
    }

    @Test
    public void testInstantionAwarePostprocessor(){
        ApplicationContext context =  new ClassPathXmlApplicationContext("application.xml");
        TestFb testFb = (TestFb) context.getBean("testFb");
        testFb.dosomething();
    }
}
