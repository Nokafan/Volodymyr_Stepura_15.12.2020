package file.sorter.config;

import file.sorter.service.FileSorterService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class EventListenerBean implements ApplicationListener<ApplicationStartedEvent> {
    private final FileSorterService fileSorterService;

    @Autowired
    public EventListenerBean(FileSorterService fileSorterService) {
        this.fileSorterService = fileSorterService;
    }

    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        fileSorterService.getFiles();
        log.info("ApplicationStartedEvent has finished all tasks!");
    }
}
