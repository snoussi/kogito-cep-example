package com.redhat.quotegame;

import com.redhat.quotegame.model.Order;
import com.redhat.quotegame.model.OrderType;
import com.redhat.quotegame.model.Quote;
import org.jboss.logging.Logger;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.kogito.rules.KieRuntimeBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;


@ApplicationScoped
public class QuotePriceUpdater {
    private final Logger LOGGER = Logger.getLogger(getClass());
    private KieSession kieSession;

    @Named("myStatefulKieSession")
    QuotePriceUpdater( KieRuntimeBuilder runtimeBuilder ) {
        kieSession = runtimeBuilder.newKieSession();
    }

    public String run() {

        Quote quote = new Quote("RHT",187.71);
        LOGGER.info("insert Quote");
        kieSession.insert(quote);

        EntryPoint orderStream = kieSession.getEntryPoint("orderStream");

        LOGGER.info("insert BUY Order");
        orderStream.insert(getOrderBUY());
        LOGGER.info("fire.");
        kieSession.fireAllRules();

        LOGGER.info("insert BUY Order");
        orderStream.insert(getOrderBUY());
        LOGGER.info("fire.");
        kieSession.fireAllRules();

        LOGGER.info("insert BUY Order");
        orderStream.insert(getOrderBUY());
        LOGGER.info("fire.");
        kieSession.fireAllRules();

        LOGGER.info("insert SELL Order");
        orderStream.insert(getOrderSELL());
        LOGGER.info("fire.");
        kieSession.fireAllRules();

        LOGGER.info("insert BUY Order");
        orderStream.insert(getOrderBUY());
        LOGGER.info("fire.");
        kieSession.fireAllRules();

        LOGGER.info("insert BUY Order");
        orderStream.insert(getOrderBUY());
        LOGGER.info("fire.");
        kieSession.fireAllRules();

        LOGGER.info("insert BUY Order");
        orderStream.insert(getOrderBUY());
        LOGGER.info("fire.");
        kieSession.fireAllRules();

        LOGGER.info(kieSession.getFactCount()+" Facts in WM");

        return "result : FactCounts = "+ kieSession.getFactCount();
    }

    private static Order getOrderBUY() {
        Order o = new Order();
        o.setOorderType(OrderType.BUY);
        o.setTimestamp(System.currentTimeMillis());
        o.setQuote("RHT");
        o.setNumber(5);
        return o;
    }

    private static Order getOrderSELL() {
        Order o = new Order();
        o.setOorderType(OrderType.SELL);
        o.setTimestamp(System.currentTimeMillis());
        o.setQuote("RHT");
        o.setNumber(5);
        return o;
    }

}
