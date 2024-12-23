package org.example.config.saga;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;

public class OrderProcessingSaga extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("saga:orderProcessingSaga")
                .log("Starting Order Processing Saga")
                .saga()
                .propagation(SagaPropagation.REQUIRED)
                .option("reserveStock", constant("direct:reserveStock"))
                .compensation("direct:compensateReserveStock")
                .option("chargePayment", constant("direct:chargePayment"))
                .compensation("direct:compensateChargePayment")
                .option("shipOrder", constant("direct:shipOrder"))
                .compensation("direct:compensateShipOrder")
                .end()
                .log("Order Processing Saga Completed");
//stock
        from("direct:reserveStock")
                .log("Reserving Stock")
                .to("bean:stockService?method=reserveStock")
                .log("Stock Reserved");
        from("direct:compensateReserveStock")
                .log("Compensating Stock Reservation")
                .to("bean:stockService?method=compensateReserveStock")
                .log("Stock Reservation Compensated");

//paymet
        from("direct:chargePayment")
                .log("Charging Payment")
                .to("bean:paymentService?method=chargePayment")
                .log("Payment Charged");
        from("direct:compensateChargePayment")
                .log("Compensating Payment Charging and reserved both")
                .to("bean:paymentService?method=compensateChargePayment")
                .to("direct:compensateReserveStock")
                .log("Payment Charging Compensated");

//ship
        from("direct:shipOrder")
                .log("Shipping Order")
                .to("bean:shippingService?method=shipOrder")
                .log("Order Shipped");
        from("direct:compensateShipOrder")
                .log("Compensating Order Shipping , charge and reserved ")
                .to("bean:shippingService?method=compensateShipOrder")
                .to("direct:compensateChargePayment")
                .log("Order Shipping Compensated");


    }
}
