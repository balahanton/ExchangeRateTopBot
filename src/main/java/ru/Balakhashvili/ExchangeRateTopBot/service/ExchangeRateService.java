package ru.Balakhashvili.ExchangeRateTopBot.service;

import ru.Balakhashvili.ExchangeRateTopBot.exception.ServiceException;

public interface ExchangeRateService {

    String getUSDExchangeRate() throws ServiceException;
    String getEURExchangeRate() throws ServiceException;
}
