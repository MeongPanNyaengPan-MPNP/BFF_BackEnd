package com.mpnp.baechelin.api.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mpnp.baechelin.api.model.LocationKeywordSearchForm;
import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // 무시할 속성이나 속성 목록 표시
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PublicApiResponseDto {
    TouristFoodInfo touristFoodInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TouristFoodInfo {
        int list_total_count;
        @JsonProperty("RESULT")
        Result RESULT;
        List<Row> row;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Result {
        @JsonProperty("CODE")
        String CODE;
        @JsonProperty("MESSAGE")
        String MESSAGE;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Row {
        @JsonProperty("SEQ")
        int SEQ;
        String storeId;
        String latitude;
        String longitude;
        String category;
        @JsonProperty("SISULNAME")
        String SISULNAME;
        @JsonProperty("GU")
        String GU;
        @JsonProperty("ADDR")
        String ADDR;
        @JsonProperty("TEL")
        String TEL;
        @JsonProperty("HOMEPAGE")
        String HOMEPAGE;
        @JsonProperty("HIT")
        String HIT;
        @JsonProperty("ST1")
        String ST1;
        @JsonProperty("ST2")
        String ST2;
        @JsonProperty("ST3")
        String ST3;
        @JsonProperty("ST4")
        String ST4;
        @JsonProperty("ST5")
        String ST5;
        @JsonProperty("ST6")
        String ST6;
        @JsonProperty("ST7")
        String ST7;
        @JsonProperty("ST8")
        String ST8;
        @JsonProperty("ST9")
        String ST9;
        @JsonProperty("ST10")
        String ST10;
        @JsonProperty("ST11")
        String ST11;
        @JsonProperty("ST12")
        String ST12;
        public Row updateLatLng(String lat, String lng){
            this.latitude = lat;
            this.longitude = lng;
            return this;
        }
        public Row updateCategory(String category){
            this.category = category;
            return this;
        }
    }
}
