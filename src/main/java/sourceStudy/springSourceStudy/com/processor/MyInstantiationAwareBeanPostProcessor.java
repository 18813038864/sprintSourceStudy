package sourceStudy.springSourceStudy.com.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import sourceStudy.springSourceStudy.com.model.TestFb;

import java.beans.PropertyDescriptor;

public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.print("beanName:"+beanName+"执行..postProcessBeforeInstantiation\n");
        //如果此处返回了对象（实例），
        // 将跳过postProcessAfterInstantiation（实例化之后），
        // postProcessBeforeInitialization（初始化之前）
        //  直接到postProcessAfterInitialization（初始化之后）
        //因为此处已经返回的实例代表已经初始化过了


        //利用 其 生成动态代理
//        if(beanClass==TestFb.class){
//            Enhancer enhancer = new Enhancer();
//            enhancer.setSuperclass(beanClass);
//            enhancer.setCallback(new MyMethodInterceptor());
//            TestFb testFb = (TestFb)enhancer.create();
//            System.out.print("返回动态代理\n");
//            return testFb;
//        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.print("beanName:"+beanName+"执行..postProcessAfterInstantiation\n");
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        System.out.print("beanName:"+beanName+"执行..postProcessPropertyValues\n");
        if(bean instanceof TestFb){
            //修改bean中a 的属性值
            PropertyValue value = pvs.getPropertyValue("a");
            System.out.print("修改之前 a 的value是："+value.getValue()+"\n");
            value.setConvertedValue("我修改啦");
            return pvs;
        }
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.print("beanName:"+beanName+"执行..postProcessBeforeInitialization\n");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.print("beanName:"+beanName+"执行..postProcessAfterInitialization\n");
        return bean;
    }
}
