package org.combo.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DemoJob {

    private Logger logger = LoggerFactory.getLogger(DemoJob.class);

    @Scheduled(fixedDelay = 2000)
    public void sayHi() {
        logger.info("Hi~");
    }
}
