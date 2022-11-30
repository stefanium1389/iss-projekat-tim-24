package tim24.projekat.uberapp.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim24.projekat.uberapp.exception.UserNotFoundException;
import tim24.projekat.uberapp.model.User;
import tim24.projekat.uberapp.repo.UserRepository;


@Service
public class UserService {
	
	private final UserRepository UserRepo;
	
	@Autowired
	public UserService(UserRepository UserRepo) {
		this.UserRepo = UserRepo;
	}

	public User addUser(User User) 
	{
		return UserRepo.save(User);
	}
	
	public List<User> findAllUsers()
	{
		return UserRepo.findAll();
	}
	
	public User updateUser(User User) 
	{
		return UserRepo.save(User);
	}
	
	public void deleteUser(Long id) 
	{
		UserRepo.deleteUserById(id);
	}
	
	public User findUserById (Long id) 
	{
		return UserRepo.findUserById(id).orElseThrow(()-> new UserNotFoundException("leeeel"));
	}
}
