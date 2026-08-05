package router;

import dto.AdminModeResponse;
import dto.MerchantModeResponse;
import dto.UserModeResponse;
import lombok.RequiredArgsConstructor;
import manager.AdminModeManager;
import manager.MerchantModeManager;
import manager.UserModeManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReverseProxy {

    private final UserModeManager userModeManager;
    private final MerchantModeManager merchantModeManager;
    private final AdminModeManager adminModeManager;

    @PostMapping("/user-mode")
    public UserModeResponse sendToUserMode(){
       return userModeManager.startUserMode();
    }

    @PostMapping("/merchant-mode")
    public MerchantModeResponse sendToMerchantMode(){
        return merchantModeManager.startMerchandMode();
    }

    @PostMapping("/admin-mode")
    public AdminModeResponse sentToAdminMode(){
        return adminModeManager.startAdminMode();
    }
}
