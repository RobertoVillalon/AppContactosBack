package cl.beto.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import cl.beto.entity.User;

public interface IUserService {
	public abstract void insertUser(User user);
	public abstract void updateUser(User user);
	public abstract Optional<User> getUser(int idUser);
	public abstract List<User> getUserWithSearchText(String username);
	public abstract void deleteUser(User user);
	public abstract User getAcces(String email, String password);
	public abstract void getProfileImageUser(int idUser, HttpServletResponse response);
	public abstract boolean setProfileImageUser(int idUser, MultipartFile imagen, HttpServletResponse response);
	}