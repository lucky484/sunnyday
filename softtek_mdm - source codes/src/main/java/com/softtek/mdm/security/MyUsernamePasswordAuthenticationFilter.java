package com.softtek.mdm.security;


import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.util.Assert;

import jodd.util.StringUtil;

public class MyUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	@Resource(name="messageSourceService")
	private MessageSource messageSource;
	//~ Static fields/initializers =====================================================================================

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "j_username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "j_password";
    //~ Customer Data
    public static final String SPRING_SECURITY_FORM_ORG_KEY = "j_organization";
    //public static final String SPRING_SECURITY_FORM_VALIDATE_KEY = "j_validate_code";
    public static final String USERNAME_ORGID_SPLIT = "/";
    
    
    /**
     * @deprecated If you want to retain the username, cache it in a customized {@code AuthenticationFailureHandler}
     */
    @Deprecated
    public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private boolean postOnly = true;
    //~ Customer Data
    private String organizationParameter = SPRING_SECURITY_FORM_ORG_KEY;

    //~ Constructors ===================================================================================================

    public MyUsernamePasswordAuthenticationFilter() {
        super("/j_spring_security_check");
    }

    //~ Methods ========================================================================================================

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, UnsupportedEncodingException {
        if (postOnly && !request.getMethod().equals("POST")) {
        	
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        request.setCharacterEncoding("UTF-8");

        String username = obtainUsername(request);
        String password = obtainPassword(request);
      //~ Customer Data
        String organization=obtainOrganization(request);
        
        //validate code
        String code=(String) request.getSession().getAttribute("code_validate");
		String code_input=StringUtil.isBlank(request.getParameter("validatecode"))==true?"":request.getParameter("validatecode");
		request.getSession().removeAttribute("code_validate");
		
		SimpleUrlAuthenticationFailureHandler sUAH=(SimpleUrlAuthenticationFailureHandler)getFailureHandler();
		sUAH.setDefaultFailureUrl("/manager");
		
		if(!code_input.toLowerCase().equals(code.toLowerCase())){
			String mString=messageSource.getMessage("security.usernamepasswordauthenticationfilter.attemptauthentication.validatecode", null, LocaleContextHolder.getLocale());
			throw new AuthenticationServiceException(mString);
		}
		

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }
        
        //~ Customer Data
        if (organization == null) {
        	organization = "";
        }

        username = username.trim()+USERNAME_ORGID_SPLIT+organization;

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
    //~ Customer Data
    protected String obtainOrganization(HttpServletRequest request) {
        return request.getParameter(organizationParameter);
    }

    /**
     * Enables subclasses to override the composition of the password, such as by including additional values
     * and a separator.<p>This might be used for example if a postcode/zipcode was required in addition to the
     * password. A delimiter such as a pipe (|) should be used to separate the password and extended value(s). The
     * <code>AuthenticationDao</code> will need to generate the expected password in a corresponding manner.</p>
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the password that will be presented in the <code>Authentication</code> request token to the
     *         <code>AuthenticationManager</code>
     */
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    /**
     * Enables subclasses to override the composition of the username, such as by including additional values
     * and a separator.
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the username that will be presented in the <code>Authentication</code> request token to the
     *         <code>AuthenticationManager</code>
     */
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication request's details
     * property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details set
     */
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Sets the parameter name which will be used to obtain the username from the login request.
     *
     * @param usernameParameter the parameter name. Defaults to "j_username".
     */
    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    /**
     * Sets the parameter name which will be used to obtain the password from the login request..
     *
     * @param passwordParameter the parameter name. Defaults to "j_password".
     */
    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    /**
     * Defines whether only HTTP POST requests will be allowed by this filter.
     * If set to true, and an authentication request is received which is not a POST request, an exception will
     * be raised immediately and authentication will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
     * will be called as if handling a failed authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getUsernameParameter() {
        return usernameParameter;
    }

    public final String getPasswordParameter() {
        return passwordParameter;
    }

}
