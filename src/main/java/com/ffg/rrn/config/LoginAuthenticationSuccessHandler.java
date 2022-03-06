package com.ffg.rrn.config;

/**
 * a custom spring URL authentication handler.
 * It mainly overrides the onAuthenticationSuccess() handler to update the LAST_LOGIN column in the SQL database.
 * @author Dirk Jagdmann <doj@cubic.org>
 * @see https://stackoverflow.com/questions/6769654/how-to-get-redirected-to-a-method-at-login-logout-before-target-url-called-in-spr/6770785#6770785@Service 
 */ 
public class LoginAuthenticationSuccessHandler extends org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler {

    private com.ffg.rrn.service.UserDetailsServiceImpl userDetailsService;
    private String targetUrl;
    
    public LoginAuthenticationSuccessHandler(com.ffg.rrn.service.UserDetailsServiceImpl uds, String targetUrl_) {
    	userDetailsService = uds;
    	targetUrl = targetUrl_;
    }
    
    @Override
    public void onAuthenticationSuccess(
    		javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response,
            org.springframework.security.core.Authentication authentication)
     throws java.io.IOException,
            javax.servlet.ServletException {

    	userDetailsService.updateLastLogin(authentication.getName());
        setDefaultTargetUrl(targetUrl);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
