package com.example.springcameljpademo.routes;

import com.example.springcameljpademo.domain.TransactionCsvItem;
import com.example.springcameljpademo.service.ClientService;
import com.example.springcameljpademo.service.UserService;
import com.example.springcameljpademo.utils.CsvRecordToTransactionMapper;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by MD3431 on 12/08/2020
 */
@Component
public class CamelRoute extends RouteBuilder {
    @Autowired
    Environment environment;

    @Autowired
    private CsvRecordToTransactionMapper mapper;

    @Override
    public void configure() throws Exception {
        DataFormat bindy = new BindyCsvDataFormat(TransactionCsvItem.class);

        from("{{fromRoute}}")
                .to("{{toRoute}}")
                .unmarshal(bindy)
                    .log("The unmarshalled object is ${body}")
                .split(body())
                .streaming()
                    .log("Record after splitting is ${body}")
                .bean(mapper, "convertAndTransform")
                    .log("Record after CSV mapping is ${body}")
                .to("jpa:com.example.springcameljpademo.model.TransactionEntity")
                    .log("Record is ${body}")
                ;
    }
}
