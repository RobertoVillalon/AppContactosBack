package cl.beto.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.beto.entity.Contact;
import cl.beto.entity.User;
import cl.beto.repository.IContactRepo;

@Service
public class ContactServiceImpl implements IContactService{

	@Autowired
	private IContactRepo contactRepo;
	
	@Override
	public List<Integer> getContactsByUser(User user) {
		List<Integer> listIdContacts = contactRepo.findContactsByUser(user);

		return listIdContacts;
	}

	@Override
	public void insertContact(User user, int idContacto) {
		Contact newContact = new Contact();
		
		newContact.setUser(user);
		newContact.setFriendID(idContacto);
		
		contactRepo.save(newContact);		
	}
}
