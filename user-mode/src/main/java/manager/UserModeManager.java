package manager;

import dto.UserModeResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class UserModeManager {

    public UserModeResponse startUserMode(){
       UserModeResponse response = new UserModeResponse("started");
       return response;
    }
}
