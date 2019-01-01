import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;
import sourceStudy.springSourceStudy.com.model.MyTestBean;

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
}
