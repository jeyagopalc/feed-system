package com.example.userService.services.impl;

import com.example.shared.model.User;
import com.example.userService.repository.UsersRepository;
import com.example.userService.utils.UserTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.example.shared.error.ErrorCodes.FEED_010;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class UsersServiceImplTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private Environment environment;

    @InjectMocks
    private UsersServiceImpl usersService;

    @Test
    public void createUser() {
        User userDetails = UserTestUtils.createUser();
        Mockito.when(usersRepository.save(Mockito.any())).thenReturn(userDetails);
        User response = usersService.createUser(userDetails);
        assertEquals(response.getFirstName(), userDetails.getFirstName());
        Mockito.verify(usersRepository).save(Mockito.any());
    }

    @Test
    public void testLoadUserByUsername() {
        User userDetails = UserTestUtils.createUser();
        Mockito.when(usersRepository.findByEmail(Mockito.anyString())).thenReturn(userDetails);

        UserDetails response = usersService.loadUserByUsername(userDetails.getEmail());
        assertEquals(response.getUsername(), userDetails.getEmail());
        Mockito.verify(usersRepository).findByEmail(Mockito.anyString());
    }

    @Test
    public void testLoadUserByUsernameWithException() {
        User userDetails = UserTestUtils.createUser();

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            usersService.loadUserByUsername(userDetails.getEmail());
        });

        assertTrue(userDetails.getEmail().contains(exception.getMessage()));
    }

    @Test
    public void testLoadUserByEmail() {
        User userDetails = UserTestUtils.createUser();
        Mockito.when(usersRepository.findByEmail(Mockito.anyString())).thenReturn(userDetails);

        User response = usersService.getUserDetailsByEmail(userDetails.getEmail());
        assertEquals(response.getEmail(), userDetails.getEmail());
        Mockito.verify(usersRepository).findByEmail(Mockito.anyString());
    }

    @Test
    public void testLoadUserByEmailWithException() {
        User userDetails = UserTestUtils.createUser();

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            usersService.getUserDetailsByEmail(userDetails.getEmail());
        });

        assertTrue(userDetails.getEmail().contains(exception.getMessage()));
    }


    @Test
    public void getUserByUserId() {
        User userDetails = UserTestUtils.createUser();
        Mockito.when(usersRepository.findByUserId(Mockito.anyString())).thenReturn(userDetails);

        User response = usersService.getUserByUserId(userDetails.getUserId());
        assertEquals(response.getFirstName(), userDetails.getFirstName());
        Mockito.verify(usersRepository).findByUserId(Mockito.anyString());
    }

    @Test
    public void getUserByUserIdWithException() {
        User userDetails = UserTestUtils.createUser();

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            usersService.getUserByUserId(userDetails.getUserId());
        });

        assertTrue(FEED_010.getDescription().contains(exception.getMessage()));

    }
}