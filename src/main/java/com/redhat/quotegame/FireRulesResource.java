package com.redhat.quotegame;

import com.redhat.quotegame.model.Order;
import com.redhat.quotegame.model.OrderType;
import com.redhat.quotegame.model.Quote;
import org.jboss.logging.Logger;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.kogito.rules.KieRuntimeBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/fire")
public class FireRulesResource {

    private final Logger LOGGER = Logger.getLogger(getClass());
    private KieSession kieSession;

    @Inject
    @Named("myStatefulKieSession")
    FireRulesResource( KieRuntimeBuilder runtimeBuilder ) {
        kieSession = runtimeBuilder.newKieSession();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello()
    {
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