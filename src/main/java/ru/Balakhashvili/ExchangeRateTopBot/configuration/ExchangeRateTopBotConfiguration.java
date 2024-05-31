package ru.Balakhashvili.ExchangeRateTopBot.configuration;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.Balakhashvili.ExchangeRateTopBot.bot.ExchangeRateTopBot;

@Configuration
public class ExchangeRateTopBotConfiguration {

    @Bean
    public TelegramBotsApi telegramBotsApi(ExchangeRateTopBot exchangeRateTopBot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(exchangeRateTopBot);
        return api;
    }

    @Bean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient();
    }




}
