package Balakhashvili.ExchangeRateTopBot.configuration;

import Balakhashvili.ExchangeRateTopBot.bot.ExchangeRateTopBot;
import okhttp3.OkHttpClient;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@EnableCaching
@EnableScheduling
public class ExchangeRateTopBotConfiguration {

    @Bean
    public TelegramBotsApi telegramBotsApi(ExchangeRateTopBot exchangeRateTopBot) throws TelegramApiException {
        var botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(exchangeRateTopBot);
        return botsApi;
    }

    @Bean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient();
    }




}
