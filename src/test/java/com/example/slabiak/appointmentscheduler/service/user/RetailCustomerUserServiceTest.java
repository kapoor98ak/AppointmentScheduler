package com.example.slabiak.appointmentscheduler.service.user;

import com.example.slabiak.appointmentscheduler.dao.RoleRepository;
import com.example.slabiak.appointmentscheduler.dao.user.customer.RetailCustomerRepository;
import com.example.slabiak.appointmentscheduler.entity.user.Role;
import com.example.slabiak.appointmentscheduler.entity.user.customer.RetailCustomer;
import com.example.slabiak.appointmentscheduler.model.UserForm;
import com.example.slabiak.appointmentscheduler.service.RetailCustomerService;
import com.example.slabiak.appointmentscheduler.service.impl.RetailCustomerServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RetailCustomerServiceTest {

    @Mock
    private RetailCustomerRepository retailCustomerRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RetailCustomerService retailCustomerService;

    @BeforeEach
    void setUp() {
        // No need for explicit setup
    }

    @Test
    public void testSaveNewRetailCustomer() {
        // Arrange
        UserForm userForm = new UserForm();
        userForm.setUserName("testuser");
        userForm.setPassword("password");

        Role roleRetailCustomer = new Role("ROLE_CUSTOMER_RETAIL");
        Role roleCustomer = new Role("ROLE_CUSTOMER");
        when(roleRepository.findByName("ROLE_CUSTOMER_RETAIL")).thenReturn(roleRetailCustomer);
        when(roleRepository.findByName("ROLE_CUSTOMER")).thenReturn(roleCustomer);

        RetailCustomer savedRetailCustomer = new RetailCustomer();
        when(retailCustomerRepository.save(any(RetailCustomer.class))).thenReturn(savedRetailCustomer);

        // Act
        retailCustomerService.saveNewRetailCustomer(userForm);

        // Assert
        verify(passwordEncoder).encode("password");
        verify(retailCustomerRepository).save(any(RetailCustomer.class));
    }

    @Test
    public void testGetAllRetailCustomers() {
        // Arrange
        RetailCustomer retailCustomer1 = new RetailCustomer();
        RetailCustomer retailCustomer2 = new RetailCustomer();
        when(retailCustomerRepository.findAll()).thenReturn(List.of(retailCustomer1, retailCustomer2));

        // Act
        List<RetailCustomer> retailCustomers = retailCustomerService.getAllRetailCustomers();

        // Assert
        assertEquals(2, retailCustomers.size());
    }

    @Test
    public void testGetRolesForRetailCustomer() {
        // Arrange
        Role roleRetailCustomer = new Role("ROLE_CUSTOMER_RETAIL");
        Role roleCustomer = new Role("ROLE_CUSTOMER");
        when(roleRepository.findByName("ROLE_CUSTOMER_RETAIL")).thenReturn(roleRetailCustomer);
        when(roleRepository.findByName("ROLE_CUSTOMER")).thenReturn(roleCustomer);

        // Act
        Collection<Role> roles = retailCustomerService.getRolesForRetailCustomer();

        // Assert
        assertEquals(2, roles.size());
        assertTrue(roles.contains(roleRetailCustomer));
        assertTrue(roles.contains(roleCustomer));
    }

    @Test
    public void testGetRetailCustomerById() {
        // Arrange
        RetailCustomer retailCustomer = new RetailCustomer();
        when(retailCustomerRepository.findById(1)).thenReturn(Optional.of(retailCustomer));

        // Act
        RetailCustomer retrievedRetailCustomer = retailCustomerService.getRetailCustomerById(1);

        // Assert
        assertNotNull(retrievedRetailCustomer);
        assertEquals(retailCustomer, retrievedRetailCustomer);
    }

    @Test
    public void testGetRetailCustomerById_ThrowsExceptionWhenNotFound() {
        // Arrange
        when(retailCustomerRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> retailCustomerService.getRetailCustomerById(1));
    }

}


