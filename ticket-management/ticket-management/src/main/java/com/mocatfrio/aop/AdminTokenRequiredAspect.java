package com.mocatfrio.aop;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mocatfrio.service.SecurityServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Aspect
@Component
public class AdminTokenRequiredAspect {
	@Before("@annotation(adminTokenRequired)")
	public void adminTokenRequiredWithAnnotation(AdminTokenRequired adminTokenRequired) throws Throwable {
		ServletRequestAttributes reqAttributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = reqAttributes.getRequest();
		
		// Checks for token in request header
		String tokenInHeader = request.getHeader("token");
		if(StringUtils.isEmpty(tokenInHeader)){
			throw new IllegalArgumentException("Empty token");
		}		
		
		// Parse the token
		Claims claims = Jwts.parser()         
			       .setSigningKey(DatatypeConverter.parseBase64Binary(SecurityServiceImpl.secretKey))
			       .parseClaimsJws(tokenInHeader)
			       .getBody();
		if(claims == null || claims.getSubject() == null){
			throw new IllegalArgumentException("Token Error : Claim is null");
		}
		
		// Get the subject
		String subject = claims.getSubject();
		// Check if the token length is not 2 and the usertype is not 3 (Admin)
		System.out.println(" usertype : "+subject.split("=")[1]);
		if(subject.split("=").length != 2 || Integer.parseInt(subject.split("=")[1]) != 3) {
			throw new IllegalArgumentException("User is not authorized");
		}		
	}
}