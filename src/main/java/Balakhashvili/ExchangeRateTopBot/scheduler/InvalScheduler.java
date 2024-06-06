package Balakhashvili.ExchangeRateTopBot.scheduler;


import Balakhashvili.ExchangeRateTopBot.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InvalScheduler {

    @Autowired
    private ExchangeRateService service;

    @Scheduled(cron = "0 0 0,6,12 * * ?")
    public void invalidateCache() {
        service.clearUSDCache();
        service.clearEURCache();
        service.clearGELCache();
        service.clearTRYCache();
        service.clearAEDCache();
        service.clearEGPCache();
        service.clearTJSCache();
        service.clearCNYCache();
        service.clearTHBCache();
        service.clearJPYCache();
        service.clearAMDCache();
        service.clearBYNCache();
        service.clearKZTCache();
    }
}
