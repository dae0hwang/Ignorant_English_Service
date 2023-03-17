package hello.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockChange {

    String yyyymmdd;
    String skuCd;
    String fieldName;
    int diff;

    @Builder
    public StockChange(String yyyymmdd, String skuCd, String fieldName, int diff) {
        this.yyyymmdd = yyyymmdd;
        this.skuCd = skuCd;
        this.fieldName = fieldName;
        this.diff = diff;
    }
}