package com.example.userService.services.impl;

import com.example.shared.model.User;
import com.example.userService.repository.UsersRepository;
import com.example.userService.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

import static com.example.shared.error.ErrorCodes.FEED_010;

@Service
public class UsersServiceImpl implements UsersService {

	private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

	private final UsersRepository usersRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final Environment environment;

	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository,
                            BCryptPasswordEncoder bCryptPasswordEncoder,
                            Environment environment)
	{
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.environment = environment;
	}
 
	@Override
	public User createUser(final User userDetails) {
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

		User returnValue = usersRepository.save(userDetails);
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = usersRepository.findByEmail(username);

		if(user == null) {
			logger.info(FEED_010.getDescription());
			throw new UsernameNotFoundException(username);
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getEncryptedPassword(), true, true, true,
				true, new ArrayList<>());
	}

	@Override
	public User getUserDetailsByEmail(final String email) {
		User user = usersRepository.findByEmail(email);

		if(user == null) {
			logger.info(FEED_010.getDescription());
			throw new UsernameNotFoundException(email);
		}

		return user;
	}

	@Override
	public User getUserByUserId(String userId) {
		
        User user = usersRepository.findByUserId(userId);

		if(user == null) {
			logger.info(FEED_010.getDescription());
			throw new UsernameNotFoundException(FEED_010.getDescription());
		}
		
		return user;
	}

}
