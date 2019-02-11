package si.test.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import si.test.service.CacheService;

@RestController
@RequestMapping(value = "/si-test/cache")
public class SiTestCacheController {

    private final CacheService cacheService;

    @Autowired
    public SiTestCacheController(final CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @GetMapping(value = "/clear")
    @ApiOperation(value = "Clears cache")
    @Scheduled(cron = "${cache.cron}")
    public void clear() {
        cacheService.clear();
    }
}
