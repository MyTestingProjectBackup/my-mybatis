package cn.hel.mapper;

import java.util.List;

import cn.hel.entity.User;

public interface UserMapper {
	
	public List<User> getAllUsers();
	
	public User getUserById(int id);
}
