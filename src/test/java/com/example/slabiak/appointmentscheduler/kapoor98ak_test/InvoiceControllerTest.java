package com.example.slabiak.appointmentscheduler.kapoor98ak_test;


import com.example.slabiak.appointmentscheduler.controller.InvoiceController;
import com.example.slabiak.appointmentscheduler.entity.Invoice;
import com.example.slabiak.appointmentscheduler.security.CustomUserDetails;
import com.example.slabiak.appointmentscheduler.service.InvoiceService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class InvoiceControllerTest {

    @Mock
    private InvoiceService invoiceService;

    @Mock
    private Model model;

    @Mock
    private CustomUserDetails customUserDetails;

    @InjectMocks
    private InvoiceController invoiceController;

    @Test
    void showAllInvoices() {
        // Arrange
        // Assuming invoiceService.getAllInvoices() returns a list of invoices

        List<Invoice> mockedInvoices = Arrays.asList(Mockito.mock(Invoice.class), Mockito.mock(Invoice.class));
        when(invoiceService.getAllInvoices()).thenReturn(mockedInvoices);

        // Act
        String viewName = invoiceController.showAllInvoices(model);

        // Assert
        assertEquals("invoices/listInvoices", viewName);
        Mockito.verify(model).addAttribute("invoices",mockedInvoices);
    }

    @Test
    void changeStatusToPaid() {
        // Arrange
        int invoiceId = 1;

        // Act
        String viewName = invoiceController.changeStatusToPaid(invoiceId);

        // Assert
        assertEquals("redirect:/invoices/all", viewName);
        Mockito.verify(invoiceService).changeInvoiceStatusToPaid(invoiceId);
    }

    @Test
    void issueInvoicesManually() {
        // Act
        String viewName = invoiceController.issueInvoicesManually(model);

        // Assert
        assertEquals("redirect:/invoices/all", viewName);
        Mockito.verify(invoiceService).issueInvoicesForConfirmedAppointments();
    }

    @Test
    void downloadInvoice() throws IOException {
        // Arrange
        int invoiceId = 1;
        File mockInvoicePdf = File.createTempFile("mockInvoice", ".pdf");
        when(invoiceService.generatePdfForInvoice(invoiceId)).thenReturn(mockInvoicePdf);

        // Act
        ResponseEntity<InputStreamResource> responseEntity = invoiceController.downloadInvoice(invoiceId, customUserDetails);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_PDF, responseEntity.getHeaders().getContentType());
        assertEquals(mockInvoicePdf.length(), responseEntity.getHeaders().getContentLength());
        assertEquals("attachment; filename=" + mockInvoicePdf.getName(), responseEntity.getHeaders().getContentDisposition().toString());
        // Clean up
        mockInvoicePdf.delete();
    }
}
