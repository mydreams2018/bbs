package cn.kungreat.bbs.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class SchedulingJob {

    @Scheduled(fixedDelay = 1000)
    public void first() {
        System.out.println("第一个定时任务开始 : " + Thread.currentThread().getName());
    }

    @Scheduled(fixedDelay = 1000)
    public void second() {
        System.out.println("第2个定时任务开始 : " + Thread.currentThread().getName());
    }
}
