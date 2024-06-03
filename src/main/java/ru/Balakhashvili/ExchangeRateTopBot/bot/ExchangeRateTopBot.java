package ru.Balakhashvili.ExchangeRateTopBot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.Balakhashvili.ExchangeRateTopBot.exception.ServiceException;
import ru.Balakhashvili.ExchangeRateTopBot.service.ExchangeRateService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Component
public class ExchangeRateTopBot extends TelegramLongPollingBot {

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeRateTopBot.class);

    private static final String START = "/start";
    private static final String USD = "/usd";
    private static final String EUR = "/eur";
    private static final String GEL = "/gel";
    private static final String TRY = "/try";
    private static final String HELP = "/help";

    @Autowired
    private ExchangeRateService exchangeRateService;

    public ExchangeRateTopBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        var message = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();
        switch (message) {
            case START -> {
                String userName = update.getMessage().getChat().getUserName();
                startCommand(chatId, userName);
            }
            case USD -> usdCommand(chatId);
            case EUR -> eurCommand(chatId);
            case GEL -> gelCommand(chatId);
            case TRY -> tryCommand(chatId);
            case HELP -> helpCommand(chatId);
            default -> unknownCommand(chatId);
        }

    }

    @Override
    public String getBotUsername() {
        return "ExchangeRateTopBot";
    }

    private void startCommand(Long chatId, String userName) {
        var text = """
                Добро пожаловать в бот, %s!
                
                Здесь Вы сможете узнать официальные курсы валют на сегодня, установленные ЦБ РФ.
                
                Для этого воспользуйтесь командами:
                /usd - курс доллара
                /eur - курс евро
                /gel - курс грузинского лари
                /try - курс турецкой лиры
                
                Дополнительные команды:
                /help - получение справки
                """;
        var formattedText = String.format(text, userName);
        sendMessage(chatId, formattedText);

    }

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String fld = dtf.format(LocalDate.now());

    private void usdCommand(Long chatId) {
        String formattedText;
        try {
            var usd = exchangeRateService.getUSDExchangeRate();
            var text = "Курс доллара на %s составляет %s рублей";
            formattedText = String.format(text, fld, usd);
        } catch (ServiceException e) {
            LOG.error("Ошибка получения курса доллара", e);
            formattedText = "Не удалось получить текущий курс доллара. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);

    }

    private void eurCommand(Long chatId) {
        String formattedText;
        try {
            var eur = exchangeRateService.getEURExchangeRate();
            var text = "Курс евро на %s составляет %s рублей";
            formattedText = String.format(text, fld, eur);
        } catch (ServiceException e) {
            LOG.error("Ошибка получения курса евро", e);
            formattedText = "Не удалось получить текущий курс евро. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);

    }

    private void gelCommand(Long chatId) {
        String formattedText;
        try {
            var gel = exchangeRateService.getGELExchangeRate();
            var text = "Курс грузинского лари на %s составляет %s рублей";
            formattedText = String.format(text, fld, gel);
        } catch (ServiceException e) {
            LOG.error("Ошибка получения курса лари", e);
            formattedText = "Не удалось получить текущий курс лари. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);

    }

    private void tryCommand(Long chatId) {
        String formattedText;
        try {
            var tyr = exchangeRateService.getTRYExchangeRate();
            var text = "Курс турецкой лиры на %s составляет %s рублей";
            formattedText = String.format(text, fld, tyr);
        } catch (ServiceException e) {
            LOG.error("Ошибка получения курса лиры", e);
            formattedText = "Не удалось получить текущий курс лиры. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);

    }

    private void helpCommand(Long chatId) {
        var text = """
                Справочная информация по боту
                
                Для получения текущих курсов валют воспользуйтесь коммандами:
                /usd - курс доллара
                /eur - курс евро
                /gel - курс грузинского лари
                /try - курс турецкой лиры
                """;
        sendMessage(chatId, text);
    }

    private void unknownCommand(Long chatId) {
        var text = "Не удалось распознать команду!";
        sendMessage(chatId, text);
    }

    private void sendMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            LOG.error("Ошибка отправки сообщения", e);
        }
    }










}
