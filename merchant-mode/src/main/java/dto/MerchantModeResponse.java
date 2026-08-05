package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantModeResponse {
    String message;

    public MerchantModeResponse(String message) {
        this.message = message;
    }
}
