package gov.nih.nci.nbia.restSecurity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.BadCredentialsException;

import gov.nih.nci.nbia.security.NCIASecurityManager;
import gov.nih.nci.nbia.util.NCIAConfig;
import gov.nih.nci.nbia.util.SpringApplicationContext;


public class CsmAuthenticationProviderForOauth2 implements AuthenticationProvider {
	 
	    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	        String name = authentication.getName();
	        String password = authentication.getCredentials().toString();
	        String guestAccount  = NCIAConfig.getEnabledGuestAccount();
	        System.out.println("--------"+NCIAConfig.getEnabledGuestAccount());
	        System.out.println("--------"+NCIAConfig.getGuestUsername());
	        if (guestAccount.equalsIgnoreCase("yes")){
	        	if(NCIAConfig.getGuestUsername().equalsIgnoreCase(name)){
		        	List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
		            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
		            //Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
		            Authentication auth = new UsernamePasswordAuthenticationToken(name, null, grantedAuths);
		            return auth;
	        	}
	        }
	        try {
	        NCIASecurityManager mgr = (NCIASecurityManager)SpringApplicationContext.getBean("nciaSecurityManager");
	        if(mgr.login(name, password)) {
	        	List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
	            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
	            //Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
	            Authentication auth = new UsernamePasswordAuthenticationToken(name, null, grantedAuths);
	            if (NCIAConfig.getProductVariation().toUpperCase().equals("TCIA")) {
	            	mgr.syncDBWithLDAP(name);
					System.out.println("Sync performed");
	            }
	            return auth;
	        } else {
	        	throw new BadCredentialsException("Bad User Credentials.");
	        }
	        }
	        catch (Exception ex) {
	        	ex.printStackTrace();
	        	return null;
	        }
	        
//	        if (name.equals("admin") && password.equals("system")) {
//	            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
//	            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
//	            //grantedAuths.add(new SwitchUserGrantedAuthority("ROLE_USER",
//                //        authentication));
//	            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
//	            return auth;
//	        } else {
//	            return null;
//	        }
	    }
	 
	
	    public boolean supports(Class<?> arg0) {
	        return true;
	    }
}
