package final_project.travel_agency.aop;

import final_project.travel_agency.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class OrderErrorAdvice {
    private final LogService logService;

    public OrderErrorAdvice(LogService logService) {
        this.logService = logService;
    }

    @Pointcut("execution(* final_project.travel_agency.web.CartController.createOrder(..) )")
    public void OrderPointCut(){

    }

    @AfterThrowing(value = "OrderPointCut()", throwing = "ex")
    public void handleOrderEx(JoinPoint jp, Throwable ex){
        this.logService.createLogForOrderErrorAdvice(jp,ex);
    }
}
