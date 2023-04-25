package com.devjp.basic.util;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.GrantedAuthority;

public class AuthorityCheck {
	
	public static boolean hasRole(Collection<? extends GrantedAuthority> authorities, String role) {
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		
		while(iter.hasNext()) {
			String auth = iter.next().getAuthority();
			
			if(auth.startsWith("ROLE_")) {
				String authNoRole = auth.replace("ROLE_", "");
				
				if(auth.equals(role)) {
					return true;
				}
				if(authNoRole.equals(role)) {
					return true;
				}
			}else {
				continue;
			}
		}
		
		return false;
	}
	
	public static boolean hasAnyRole(Collection<? extends GrantedAuthority> authorities, String[] authorityArray) {
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		
		while(iter.hasNext()) {
			String auth = iter.next().getAuthority();
			
			if(auth.startsWith("ROLE_")) {
				String authNoRole = auth.replace("ROLE_", "");
				
				for(int i=0;i<authorityArray.length;i++) {
					if(auth.equals(authorityArray[i])) {
						return true;
					}
					if(authNoRole.equals(authorityArray[i])) {
						return true;
					}
				}
			}else {
				continue;
			}
		}
		
		return false;
	}
	
	public static boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String authority) {
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		
		while(iter.hasNext()) {
			String auth = iter.next().getAuthority();
			if(auth.startsWith("ROLE_")) {
				continue;
			}else {
				if(auth.equals(authority)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean hasAnyAuthority(Collection<? extends GrantedAuthority> authorities, String[] authorityArray) {
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		
		while(iter.hasNext()) {
			String auth = iter.next().getAuthority();
			if(auth.startsWith("ROLE_")) {
				continue;
			}else {
				for(int i=0;i<authorityArray.length;i++) {
					if(auth.equals(authorityArray[i])) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
}
