package com.mocatfrio.restapp;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mocatfrio.aop.AdminTokenRequired;
import com.mocatfrio.aop.CSRTokenRequired;
import com.mocatfrio.aop.UserTokenRequired;
import com.mocatfrio.model.User;
import com.mocatfrio.service.TicketService;
import com.mocatfrio.service.UserService;
import com.mocatfrio.util.Util;

@RestController
@RequestMapping("/ticket")

public class TicketController {
	@Autowired
	TicketService ticketService;
	
	@Autowired
	UserService userService;
	
	// READ
	/*
	 * Get all tickets by Admin
	 */
	@ResponseBody
	@AdminTokenRequired
	@RequestMapping("/by/admin")
	public <T> T getAllTickets(HttpServletRequest request) {
		return (T) Util.getSuccessResult(ticketService.getAllTickets());
	}	
	/*
	 * Get all tickets by CSR
	 */
	@ResponseBody
	@CSRTokenRequired
	@RequestMapping("/by/csr")
	public <T> T getAllTicketsByCSR(HttpServletRequest request) {
		return (T) Util.getSuccessResult(ticketService.getAllTickets());
	}			
	/*
	 * Get specific ticket using its ID by all users
	 */
	@ResponseBody
	@UserTokenRequired
	@RequestMapping("/{ticketid}")
	public <T> T getTicket(
			@PathVariable("ticketid") final Integer ticketid,		
			HttpServletRequest request
		) {	
		return (T) Util.getSuccessResult(ticketService.getTicket(ticketid));
	}
	/*
	 * Get my ticket by specific Customer token
	 */
	@ResponseBody
	@RequestMapping("/my/tickets")
	public Map<String, Object> getMyTickets(
			HttpServletRequest request
		) {
		User user = userService.getUserByToken(request.getHeader("token"));
		if(user == null) {
			return Util.getUserNotAvailableError();
		}
		return Util.getSuccessResult(ticketService.getMyTickets(user.getUserId()));
	}
	
	
	// CREATE
	/*
	 * Rule: only user can create a ticket
	 */
	@ResponseBody
	@UserTokenRequired
	@RequestMapping(value = "", method = RequestMethod.POST)
	public <T> T addTicket(			
				@RequestParam(value="content") String content,			
				HttpServletRequest request
		) {
		User user = userService.getUserByToken(request.getHeader("token"));		
		ticketService.addTicket(user.getUserId(), content, 2, 1);		
		return Util.getSuccessResult(); 
	}
	
	// UPDATE
	/*
	 * Update ticket by Customer
	 * Rule: can only update content
	 */
	@ResponseBody
	@RequestMapping(value = "/{ticketid}", method = RequestMethod.PUT)
	public <T> T updateTicketByCustomer (
			@PathVariable("ticketid") final Integer ticketid,
			@RequestParam(value="content") String content,
			HttpServletRequest request			
		) {
		User user = userService.getUserByToken(request.getHeader("token"));
		if(user == null) {
			return Util.getUserNotAvailableError();
		}
		ticketService.updateTicket(ticketid, content, 2, 1);
		return Util.getSuccessResult(); 
	}
	/*
	 * Update ticket by CSR
	 * Rule: can update content, severity, and status
	 */
	@ResponseBody
	@CSRTokenRequired
	@RequestMapping(value = "/by/csr", method = RequestMethod.PUT)
	public <T> T updateTicketByCSR (
			@RequestParam("ticketid") final Integer ticketid,
			@RequestParam(value="content") String content,
			@RequestParam(value="severity") Integer severity,
			@RequestParam(value="status") Integer status,
			HttpServletRequest request
		) {
		ticketService.updateTicket(ticketid, content, severity, status);
		return Util.getSuccessResult(); 
	}
	/*
	 * Update ticket by Admin
	 * Rule: can update content, severity, and status
	 */
	@ResponseBody
	@AdminTokenRequired
	@RequestMapping(value = "/by/admin", method = RequestMethod.PUT)
	public <T> T updateTicketByAdmin (
			@RequestParam("ticketid") final Integer ticketid,
			@RequestParam(value="content") String content,
			@RequestParam(value="severity") Integer severity,
			@RequestParam(value="status") Integer status,
			HttpServletRequest request
		) {
		ticketService.updateTicket(ticketid, content, severity, status);
		return Util.getSuccessResult(); 
	}
	
	// DELETE
	/*
	 * Delete ticket by User
	 * Rule: can only one ticket at a time
	 */
	@ResponseBody
	@UserTokenRequired
	@RequestMapping(value = "/{ticketid}", method = RequestMethod.DELETE)
	public <T> T deleteTicketByUser (
			@PathVariable("ticketid") final Integer ticketid,
			HttpServletRequest request			
		) {
		User user = userService.getUserByToken(request.getHeader("token"));
		ticketService.deleteMyTicket(user.getUserId(), ticketid);
		return Util.getSuccessResult(); 
	}
	/*
	 * Delete ticket by CSR
	 * Rule: can delete many tickets at once
	 */
	@ResponseBody
	@CSRTokenRequired
	@RequestMapping(value = "/by/csr", method = RequestMethod.DELETE)
	public <T> T deleteTicketsByCSR (
			@RequestParam("ticketids") final String ticketids,
			HttpServletRequest request
		) {
		User user = userService.getUserByToken(request.getHeader("token"));
		ticketService.deleteTickets(user, ticketids);
		return Util.getSuccessResult(); 
	}
	/*
	 * Delete ticket by Admin
	 * Rule: can delete many tickets at once
	 */
	@ResponseBody
	@AdminTokenRequired
	@RequestMapping(value = "/by/admin", method = RequestMethod.DELETE)
	public <T> T deleteTicketsByAdmin (			
			@RequestParam("ticketids") final String ticketids,
			HttpServletRequest request
		) {
		User user = userService.getUserByToken(request.getHeader("token"));
		ticketService.deleteTickets(user, ticketids);
		return Util.getSuccessResult(); 
	}
}
