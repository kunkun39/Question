package com.changhong.common.thread;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * User: Jack Wang
 * Date: 14-5-7
 * Time: 上午11:03
 */
@Service("applicationThreadPool")
public class ApplicationThreadPool implements InitializingBean {

    private static ThreadPoolTaskExecutor pool = null;

    public void afterPropertiesSet() throws Exception {
        pool = new ThreadPoolTaskExecutor();
        pool.setQueueCapacity(1000);
        pool.setCorePoolSize(10);
        pool.setMaxPoolSize(20);
        pool.setKeepAliveSeconds(60);
        pool.initialize();
    }

    public void executeThread(Thread thread) {
        pool.execute(thread);
    }

}
