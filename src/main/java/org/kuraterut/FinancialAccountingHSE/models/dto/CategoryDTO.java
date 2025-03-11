package org.kuraterut.FinancialAccountingHSE.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import org.kuraterut.FinancialAccountingHSE.utils.CategoryType;
import org.kuraterut.FinancialAccountingHSE.utils.OperationType;

import java.util.UUID;

@Getter
@Setter
public class CategoryDTO {
    @JsonProperty("id")
    @JacksonXmlProperty(localName = "id")
    private Long id;

    @JsonProperty("categoryType")
    @JacksonXmlProperty(localName = "categoryType")
    private CategoryType categoryType;

    @JsonProperty("name")
    @JacksonXmlProperty(localName = "name")
    private String name;
}