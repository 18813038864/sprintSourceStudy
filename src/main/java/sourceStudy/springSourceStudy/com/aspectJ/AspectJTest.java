package sourceStudy.springSourceStudy.com.aspectJ;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import static java.lang.Thread.sleep;

@Aspect
public class AspectJTest {
    @Pointcut("execution(* *.test(..))")
    public void test(){}

    //此处的test()指pointcut定义的test()
    @Before("test()")
    public void beforeTest(){
        System.out.println("beforeTest");
    }

    @After("test()")
    public void afterTest(){
        System.out.println("AfterTest");
    }

    @Around("test()")
    public Object aroundTest(ProceedingJoinPoint p){
        System.out.println("before1");
        Object obj = null;
        try {
            obj = p.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("after1");

        return obj;
    }
}
