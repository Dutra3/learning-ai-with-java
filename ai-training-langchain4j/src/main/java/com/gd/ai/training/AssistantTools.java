package com.gd.ai.training;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AssistantTools {

    // In memory table ("DB")
    private static final Map<String, Double> DAILY_BASE_PRICE = Map.of(
            "economy", 150.0,
            "suv",       280.0,
            "premium",   420.0
    );

    private static final Map<String, Double> INSURANCE_RATE = Map.of(
            "economy", 0.05,
            "suv",       0.08,
            "premium",   0.12
    );

    @Tool("Calcula o valor total do aluguel corporativo com base na categoria do carro e número de dias.")
    public String calculateQuotation(String category, int days) {
        Double base = DAILY_BASE_PRICE.get(category.toLowerCase());
        Double rate = INSURANCE_RATE.get(category.toLowerCase());

        double total = (base * days) * (1 + rate);

        return String.format("Cotação: %s por %d dias → R$ %.2f (inclui seguro %.0f%%)", category, days, total, rate * 100);
    }
}
