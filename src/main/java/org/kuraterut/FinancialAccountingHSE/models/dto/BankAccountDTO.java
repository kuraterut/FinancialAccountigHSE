package org.kuraterut.FinancialAccountingHSE.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
//@JsonIgnoreProperties(ignoreUnknown = true) // Игнорирует лишние поля в JSON
public class BankAccountDTO {
    @JsonProperty("id")
    @JacksonXmlProperty(localName = "id")
    private Long id;

    @JsonProperty("name")
    @JacksonXmlProperty(localName = "name")
    private String name;

    @JsonProperty("balance")
    @JacksonXmlProperty(localName = "balance")
    private Double balance;
}