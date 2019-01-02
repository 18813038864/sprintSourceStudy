package sourceStudy.springSourceStudy.com.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.StringValueResolver;

import java.util.HashSet;
import java.util.Set;

public class ObscenityRemoveBeanFactoryProcessor implements BeanFactoryPostProcessor {
    private Set<String> obscenities;

    public ObscenityRemoveBeanFactoryProcessor(){
        obscenities = new HashSet<String>();
    }
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        StringValueResolver valueResolver = new StringValueResolver() {
            public String resolveStringValue(String strVal) {
                if (isObscene(strVal)){
                    return "******";
                }
                return strVal;
            }
        };

        BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(valueResolver);

        String[] beanNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            BeanDefinition bf = beanFactory.getBeanDefinition(beanName);
            visitor.visitBeanDefinition(bf);
        }
    }

    public boolean isObscene(Object value){
        String  potentialObscenity = value.toString().toUpperCase();
        return this.obscenities.contains(potentialObscenity);
    }
    public void setObscenities(Set<String> obscenities){
        this.obscenities.clear();
        for (String obscenity : obscenities) {
            this.obscenities.add(obscenity.toUpperCase());
        }

    }


}
