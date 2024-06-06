package Balakhashvili.ExchangeRateTopBot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import Balakhashvili.ExchangeRateTopBot.clientToCbr.CbrClient;
import Balakhashvili.ExchangeRateTopBot.exception.ServiceException;
import Balakhashvili.ExchangeRateTopBot.service.ExchangeRateService;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.time.LocalDateTime;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

    private static final String USD_XPATH = "/ValCurs//Valute[@ID='R01235']/Value";
    private static final String EUR_XPATH = "/ValCurs//Valute[@ID='R01239']/Value";
    private static final String GEL_XPATH = "/ValCurs//Valute[@ID='R01210']/Value";
    private static final String TRY_XPATH = "/ValCurs//Valute[@ID='R01700J']/VunitRate";
    private static final String GBP_XPATH = "/ValCurs//Valute[@ID='R01035']/Value";
    private static final String AED_XPATH = "/ValCurs//Valute[@ID='R01230']/Value";
    private static final String EGP_XPATH = "/ValCurs//Valute[@ID='R01240']/VunitRate";
    private static final String TJS_XPATH = "/ValCurs//Valute[@ID='R01670']/VunitRate";
    private static final String CNY_XPATH = "/ValCurs//Valute[@ID='R01375']/Value";
    private static final String THB_XPATH = "/ValCurs//Valute[@ID='R01675']/VunitRate";
    private static final String JPY_XPATH = "/ValCurs//Valute[@ID='R01820']/VunitRate";
    private static final String AMD_XPATH = "/ValCurs//Valute[@ID='R01060']/VunitRate";
    private static final String BYN_XPATH = "/ValCurs//Valute[@ID='R01090B']/Value";
    private static final String KZT_XPATH = "/ValCurs//Valute[@ID='R01335']/VunitRate";

    @Autowired
    private CbrClient client;


    @Cacheable(value = "usd", unless = "#result == null or #result.isEmpty()")
    @Override
    public String getUSDExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, USD_XPATH);
    }

    @Cacheable(value = "eur", unless = "#result == null or #result.isEmpty()")
    @Override
    public String getEURExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, EUR_XPATH);
    }


    @Cacheable(value = "gel", unless = "#result == null or #result.isEmpty()")
    @Override
    public String getGELExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, GEL_XPATH);
    }


    @Cacheable(value = "try", unless = "#result == null or #result.isEmpty()")
    @Override
    public String getTRYExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, TRY_XPATH);
    }


    @Cacheable(value = "gbp", unless = "#result == null or #result.isEmpty()")
    @Override
    public String getGBPExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, GBP_XPATH);
    }

    @Cacheable(value = "aed", unless = "#result == null or #result.isEmpty()")
    @Override
    public String getAEDExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, AED_XPATH);
    }


    @Cacheable(value = "egp", unless = "#result == null or #result.isEmpty()")
    @Override
    public String getEGPExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, EGP_XPATH);
    }

    @Cacheable(value = "tjs", unless = "#result == null or #result.isEmpty()")
    @Override
    public String getTJSExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, TJS_XPATH);
    }

    @Cacheable(value = "cny", unless = "#result == null or #result.isEmpty()")
    @Override
    public String getCNYExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, CNY_XPATH);
    }


    @Cacheable(value = "thb", unless = "#result == null or #result.isEmpty()")
    @Override
    public String getTHBExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, THB_XPATH);
    }


    @Cacheable(value = "jpy", unless = "#result == null or #result.isEmpty()")
    @Override
    public String getJPYExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, JPY_XPATH);
    }

    @Cacheable(value = "amd", unless = "#result == null or #result.isEmpty()")
    @Override
    public String getAMDExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, AMD_XPATH);
    }


    @Cacheable(value = "byn", unless = "#result == null or #result.isEmpty()")
    @Override
    public String getBYNExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, BYN_XPATH);
    }

    @Cacheable(value = "kzt", unless = "#result == null or #result.isEmpty()")
    @Override
    public String getKZTExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, KZT_XPATH);
    }

    private static String extractCurrencyValueFromXML (String xml, String xpathExpression) throws ServiceException {
        var inputSource = new InputSource(new StringReader(xml));
        try {
            var xpath = XPathFactory.newInstance().newXPath();
            var document = (Document) xpath.evaluate("/", inputSource, XPathConstants.NODE);
            return xpath.evaluate(xpathExpression, document);
        } catch (XPathExpressionException e) {
            throw new ServiceException("Не удалось выполнить парсинг XML", e);
        }
    }

    @CacheEvict("usd")
    @Override
    public void clearUSDCache() {LOGGER.info("Кэш \"usd\" очищен!");}

    @CacheEvict("eur")
    @Override
    public void clearEURCache() {LOGGER.info("Кэш \"eur\" очищен!");}

    @CacheEvict("gel")
    @Override
    public void clearGELCache() {LOGGER.info("Кэш \"gel\" очищен!");}

    @CacheEvict("try")
    @Override
    public void clearTRYCache() {LOGGER.info("Кэш \"try\" очищен!");}

    @CacheEvict("aed")
    @Override
    public void clearAEDCache() {LOGGER.info("Кэш \"aed\" очищен!");}

    @CacheEvict("egp")
    @Override
    public void clearEGPCache() {LOGGER.info("Кэш \"egp\" очищен!");}

    @CacheEvict("tjs")
    @Override
    public void clearTJSCache() {LOGGER.info("Кэш \"tjs\" очищен!");}

    @CacheEvict("cny")
    @Override
    public void clearCNYCache() {LOGGER.info("Кэш \"cny\" очищен!");}

    @CacheEvict("thb")
    @Override
    public void clearTHBCache() {LOGGER.info("Кэш \"thb\" очищен!");}

    @CacheEvict("jpy")
    @Override
    public void clearJPYCache() {LOGGER.info("Кэш \"jpy\" очищен!");}

    @CacheEvict("amd")
    @Override
    public void clearAMDCache() {LOGGER.info("Кэш \"amd\" очищен!");}

    @CacheEvict("byn")
    @Override
    public void clearBYNCache() {LOGGER.info("Кэш \"byn\" очищен!");}

    @CacheEvict("kzt")
    @Override
    public void clearKZTCache() {LOGGER.info("Кэш \"kzt\" очищен!");}

}
