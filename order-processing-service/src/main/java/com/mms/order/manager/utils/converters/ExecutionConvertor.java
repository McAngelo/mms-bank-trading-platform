package com.mms.order.manager.utils.converters;

import com.mms.order.manager.dtos.internal.ExecutionDto;
import com.mms.order.manager.models.Execution;
import com.mms.order.manager.models.OrderSplit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ExecutionConvertor implements Converter<ExecutionDto, Execution> {
    @Override
    public Execution convert(ExecutionDto executionDto) {
        return Execution.builder()
                .price(BigDecimal.valueOf(executionDto.price()))
                .quantity(executionDto.quantity())
                .dateTime(executionDto.dateTime())
                .build();
    }

    public ExecutionDto convert(Execution execution) {
        return ExecutionDto.builder()
                .price(execution.getPrice().doubleValue())
                .quantity(execution.getQuantity())
                .dateTime(execution.getDateTime())
                .build();
    }

    public Execution convert(ExecutionDto executionDto, OrderSplit orderSplit) {
        return Execution.builder()
                .price(BigDecimal.valueOf(executionDto.price()))
                .quantity(executionDto.quantity())
                .dateTime(executionDto.dateTime())
                .orderSplit(orderSplit)
                .build();
    }
}
