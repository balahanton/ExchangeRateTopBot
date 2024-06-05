package Balakhashvili.ExchangeRateTopBot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import Balakhashvili.ExchangeRateTopBot.exception.ServiceException;
import Balakhashvili.ExchangeRateTopBot.service.ExchangeRateService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Component
public class ExchangeRateTopBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateTopBot.class);

    private static final String START = "/start";
    private static final String USD = "/usd";
    private static final String EUR = "/eur";
    private static final String GEL = "/gel";
    private static final String TRY = "/try";
    private static final String GBP = "/gbp";
    private static final String AED = "/aed";
    private static final String EGP = "/egp";
    private static final String TJS = "/tjs";
    private static final String CNY = "/cny";
    private static final String THB = "/thb";
    private static final String JPY = "/jpy";
    private static final String AMD = "/amd";
    private static final String BYN = "/byn";
    private static final String KZT = "/kzt";
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
            case GBP -> gbpCommand(chatId);
            case AED -> aedCommand(chatId);
            case EGP -> egpCommand(chatId);
            case TJS -> tjsCommand(chatId);
            case CNY -> cnyCommand(chatId);
            case THB -> thbCommand(chatId);
            case JPY -> jpyCommand(chatId);
            case AMD -> amdCommand(chatId);
            case BYN -> bynCommand(chatId);
            case KZT -> kztCommand(chatId);
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
                /gbp - курс фунта стерлинга
                /aed - курс дирхама ОАЭ
                /egp - курс египетского фунта
                /tjs - курс таджикского сомони
                /cny - курс китайского юаня
                /thb - курс таиландского бата
                /jpy - курс японской иены
                /amd - курс армянских драмов
                /byn - курс белорусского рубля
                /kzt - курс казахстанских тенге
                                
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
            var rate = exchangeRateService.getUSDExchangeRate();
            var text = "Курс доллара на %s составляет %s рублей";
            formattedText = String.format(text, fld, rate);
        } catch (ServiceException e) {
            LOGGER.error("Ошибка получения курса доллара", e);
            formattedText = "Не удалось получить текущий курс доллара. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);
    }

    private void eurCommand(Long chatId) {
        String formattedText;
        try {
            var rate = exchangeRateService.getEURExchangeRate();
            var text = "Курс евро на %s составляет %s рублей";
            formattedText = String.format(text, fld, rate);
        } catch (ServiceException e) {
            LOGGER.error("Ошибка получения курса евро", e);
            formattedText = "Не удалось получить текущий курс евро. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);
    }

    private void gelCommand(Long chatId) {
        String formattedText;
        try {
            var rate = exchangeRateService.getGELExchangeRate();
            var text = "Курс грузинского лари на %s составляет %s рублей";
            formattedText = String.format(text, fld, rate);
        } catch (ServiceException e) {
            LOGGER.error("Ошибка получения курса лари", e);
            formattedText = "Не удалось получить текущий курс лари. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);
    }

    private void tryCommand(Long chatId) {
        String formattedText;
        try {
            var rate = exchangeRateService.getTRYExchangeRate();
            var text = "Курс турецкой лиры на %s составляет %s рублей";
            formattedText = String.format(text, fld, rate);
        } catch (ServiceException e) {
            LOGGER.error("Ошибка получения курса лиры", e);
            formattedText = "Не удалось получить текущий курс лиры. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);
    }

    private void gbpCommand(Long chatId) {
        String formattedText;
        try {
            var rate = exchangeRateService.getGBPExchangeRate();
            var text = "Курс фунта стерлинга на %s составляет %s рублей";
            formattedText = String.format(text, fld, rate);
        } catch (ServiceException e) {
            LOGGER.error("Ошибка получения курса фунта", e);
            formattedText = "Не удалось получить текущий курс фунта. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);
    }

    private void aedCommand(Long chatId) {
        String formattedText;
        try {
            var rate = exchangeRateService.getAEDExchangeRate();
            var text = "Курс дирхам ОАЭ на %s составляет %s рублей";
            formattedText = String.format(text, fld, rate);
        } catch (ServiceException e) {
            LOGGER.error("Ошибка получения курса дирхам", e);
            formattedText = "Не удалось получить текущий курс дирхам. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);
    }

    private void egpCommand(Long chatId) {
        String formattedText;
        try {
            var rate = exchangeRateService.getEGPExchangeRate();
            var text = "Курс египетского фунта на %s составляет %s рублей";
            formattedText = String.format(text, fld, rate);
        } catch (ServiceException e) {
            LOGGER.error("Ошибка получения курса фунта", e);
            formattedText = "Не удалось получить текущий курс фунта. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);
    }

    private void tjsCommand(Long chatId) {
        String formattedText;
        try {
            var rate = exchangeRateService.getTJSExchangeRate();
            var text = "Курс таджикского сомони на %s составляет %s рублей";
            formattedText = String.format(text, fld, rate);
        } catch (ServiceException e) {
            LOGGER.error("Ошибка получения курса сомони", e);
            formattedText = "Не удалось получить текущий курс сомони. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);
    }

    private void cnyCommand(Long chatId) {
        String formattedText;
        try {
            var rate = exchangeRateService.getCNYExchangeRate();
            var text = "Курс китайского юаня на %s составляет %s рублей";
            formattedText = String.format(text, fld, rate);
        } catch (ServiceException e) {
            LOGGER.error("Ошибка получения курса юаня", e);
            formattedText = "Не удалось получить текущий курс юаня. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);
    }

    private void thbCommand(Long chatId) {
        String formattedText;
        try {
            var rate = exchangeRateService.getTHBExchangeRate();
            var text = "Курс таиландского бата на %s составляет %s рублей";
            formattedText = String.format(text, fld, rate);
        } catch (ServiceException e) {
            LOGGER.error("Ошибка получения курса бата", e);
            formattedText = "Не удалось получить текущий курс бата. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);
    }

    private void jpyCommand(Long chatId) {
        String formattedText;
        try {
            var rate = exchangeRateService.getJPYExchangeRate();
            var text = "Курс японской иены на %s составляет %s рублей";
            formattedText = String.format(text, fld, rate);
        } catch (ServiceException e) {
            LOGGER.error("Ошибка получения курса иены", e);
            formattedText = "Не удалось получить текущий курс иены. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);
    }

    private void amdCommand(Long chatId) {
        String formattedText;
        try {
            var rate = exchangeRateService.getAMDExchangeRate();
            var text = "Курс армянских драм на %s составляет %s рублей";
            formattedText = String.format(text, fld, rate);
        } catch (ServiceException e) {
            LOGGER.error("Ошибка получения курса драм", e);
            formattedText = "Не удалось получить текущий курс драм. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);
    }

    private void bynCommand(Long chatId) {
        String formattedText;
        try {
            var rate = exchangeRateService.getBYNExchangeRate();
            var text = "Курс белорусского рубля на %s составляет %s рублей";
            formattedText = String.format(text, fld, rate);
        } catch (ServiceException e) {
            LOGGER.error("Ошибка получения курса бел. рубля", e);
            formattedText = "Не удалось получить текущий курс бел. рубля. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);
    }

    private void kztCommand(Long chatId) {
        String formattedText;
        try {
            var rate = exchangeRateService.getKZTExchangeRate();
            var text = "Курс казахстанского тенге на %s составляет %s рублей";
            formattedText = String.format(text, fld, rate);
        } catch (ServiceException e) {
            LOGGER.error("Ошибка получения курса тенге", e);
            formattedText = "Не удалось получить текущий курс тенге. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);
    }

    private void helpCommand(Long chatId) {
        var text = """
                Справочная информация по боту:
                При выборе валюты, Вы получите текущий курс установленный ЦБ РФ, 
                1 у.е. = х руб. (где х это полученное число).
                                
                Для получения текущих курсов валют воспользуйтесь коммандами:
                /usd - курс доллара
                /eur - курс евро
                /gel - курс грузинского лари
                /try - курс турецкой лиры
                /gbp - курс фунта стерлинга
                /aed - курс дирхама ОАЭ
                /egp - курс египетского фунта
                /tjs - курс таджикского сомони
                /cny - курс китайского юаня
                /thb - курс таиландского бата
                /jpy - курс японской иены
                /amd - курс армянских драмов
                /byn - курс белорусского рубля
                /kzt - курс казахстанских тенге
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
            LOGGER.error("Ошибка отправки сообщения", e);
        }
    }
}
