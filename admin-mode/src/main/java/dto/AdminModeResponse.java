package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminModeResponse {
    String message;

    public AdminModeResponse(String message) {
        this.message = message;
    }
}
