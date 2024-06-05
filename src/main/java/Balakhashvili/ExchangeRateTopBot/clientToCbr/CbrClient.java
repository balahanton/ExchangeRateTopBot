package Balakhashvili.ExchangeRateTopBot.clientToCbr;

import Balakhashvili.ExchangeRateTopBot.exception.ServiceException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CbrClient {

    @Autowired
    private OkHttpClient client;

    @Value("${cbr.currency.rates.xml.url}")
    private String urlRates;

    public String getCurrencyRatesXML() throws ServiceException {
        var request = new Request.Builder()
                .url(urlRates)
                .build();

        try (var executed = client.newCall(request).execute();) {
            var body = executed.body();
            return  body == null ? null : body.string();
        } catch (IOException e) {
            throw new ServiceException("Ошибка получения курсов валют", e);
        }
    }











}
