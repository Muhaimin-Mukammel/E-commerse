package dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserModeResponse {
    String message;

    public UserModeResponse(String message) {
        this.message = message;
    }
}
