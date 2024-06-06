package Balakhashvili.ExchangeRateTopBot.service;

import Balakhashvili.ExchangeRateTopBot.exception.ServiceException;

public interface ExchangeRateService {

    String getUSDExchangeRate() throws ServiceException; //Американский доллар
    String getEURExchangeRate() throws ServiceException; //Евро
    String getGELExchangeRate() throws ServiceException; //Грузинский лари
    String getTRYExchangeRate() throws ServiceException; //Турецкая лира
    String getGBPExchangeRate() throws ServiceException; //Британский фунт
    String getAEDExchangeRate() throws ServiceException; //Дирхам ОАЭ
    String getEGPExchangeRate() throws ServiceException; //Египетский фунт
    String getTJSExchangeRate() throws ServiceException; //Таджикский сомони
    String getCNYExchangeRate() throws ServiceException; //Китайский юань
    String getTHBExchangeRate() throws ServiceException; //Таиландский бат
    String getJPYExchangeRate() throws ServiceException; //Японская иена
    String getAMDExchangeRate() throws ServiceException; //Армянский драм
    String getBYNExchangeRate() throws ServiceException; //Белорусский рубль
    String getKZTExchangeRate() throws ServiceException; //Казахстанский тенге

    void clearUSDCache();
    void clearEURCache();
    void clearGELCache();
    void clearTRYCache();
    void clearAEDCache();
    void clearEGPCache();
    void clearTJSCache();
    void clearCNYCache();
    void clearTHBCache();
    void clearJPYCache();
    void clearAMDCache();
    void clearBYNCache();
    void clearKZTCache();
}
