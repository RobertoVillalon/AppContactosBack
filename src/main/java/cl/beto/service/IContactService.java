package cl.beto.service;

import java.util.List;
import cl.beto.entity.User;

public interface IContactService {
	public abstract List<Integer> getContactsByUser(User user);
	public abstract void insertContact(User user, int idContacto);
}
