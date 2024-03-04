package com.example.slabiak.appointmentscheduler.kapoor98ak_test;

import com.example.slabiak.appointmentscheduler.controller.ExchangeController;
import com.example.slabiak.appointmentscheduler.entity.Appointment;
import com.example.slabiak.appointmentscheduler.service.AppointmentService;
import com.example.slabiak.appointmentscheduler.service.ExchangeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ExchangeControllerTest {

    @Mock
    private ExchangeService exchangeService;

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private Model model;

    @InjectMocks
    private ExchangeController exchangeController;

    @Test
    void showEligibleAppointmentsToExchange() {
        // Arrange
        int oldAppointmentId = 1;
        List<Appointment> eligibleAppointments = new ArrayList<>();
        eligibleAppointments.add(new Appointment()); // Add sample appointment for testing
        when(exchangeService.getEligibleAppointmentsForExchange(oldAppointmentId)).thenReturn(eligibleAppointments);

        // Act
        String viewName = exchangeController.showEligibleAppointmentsToExchange(oldAppointmentId, model);

        // Assert
        assertEquals("exchange/listProposals", viewName);
        Mockito.verify(model).addAttribute("appointmentId", oldAppointmentId);
        Mockito.verify(model).addAttribute("numberOfEligibleAppointments", eligibleAppointments.size());
        Mockito.verify(model).addAttribute("eligibleAppointments", eligibleAppointments);
    }
}
