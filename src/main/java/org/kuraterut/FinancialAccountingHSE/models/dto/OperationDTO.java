package org.kuraterut.FinancialAccountingHSE.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import org.kuraterut.FinancialAccountingHSE.utils.OperationType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class OperationDTO {
    @JsonProperty("id")
    @JacksonXmlProperty(localName = "id")
    private Long id;

    @JsonProperty("operationType")
    @JacksonXmlProperty(localName = "operationType")
    private OperationType operationType;

    @JsonProperty("bankAccountId")
    @JacksonXmlProperty(localName = "bankAccountId")
    private Long bankAccountId;

    @JsonProperty("amount")
    @JacksonXmlProperty(localName = "amount")
    private Double amount;

    @JsonProperty("dateTime")
    @JacksonXmlProperty(localName = "dateTime")
    private LocalDateTime dateTime;

    @JsonProperty("description")
    @JacksonXmlProperty(localName = "description")
    private String description;

    @JsonProperty("categoryId")
    @JacksonXmlProperty(localName = "categoryId")
    private Long categoryId;
}