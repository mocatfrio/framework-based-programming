package com.mocatfrio.service;

import java.util.List;

import com.mocatfrio.model.Ticket;
import com.mocatfrio.model.User;

public interface TicketService {
	// READ
	List<Ticket> getAllTickets();
	List<Ticket> getMyTickets(Integer creatorid);
	Ticket getTicket(Integer creatorid, Integer ticketid);
	Ticket getTicket(Integer ticketid);
	
	// CREATE
	void addTicket(Integer creatorid, String content, Integer severity, Integer status);	
	
	// UPDATE
	void updateTicket(Integer ticketid, String content, Integer severity, Integer status);
	
	// DELETE
	void deleteMyTicket(Integer userid, Integer ticketid);
	void deleteTickets(User user, String ticketids);
}
