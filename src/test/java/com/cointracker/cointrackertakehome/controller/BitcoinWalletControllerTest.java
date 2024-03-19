package com.cointracker.cointrackertakehome.controller;

import com.cointracker.cointrackertakehome.dto.AddressRequest;
import com.cointracker.cointrackertakehome.dto.AddressResponse;
import com.cointracker.cointrackertakehome.dto.BalanceResponse;
import com.cointracker.cointrackertakehome.dto.TransactionResponse;
import com.cointracker.cointrackertakehome.service.BitcoinWalletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BitcoinWalletControllerTest {

    @Mock
    private BitcoinWalletService bitcoinWalletService;

    @InjectMocks
    private BitcoinWalletController bitcoinWalletController;

    @Test
    public void getWallet_ShouldReturnWallet() {
        // Arrange
        LocalDateTime lastSynchronizedTime = LocalDateTime.now();
        List<AddressResponse> expectedResponses = Arrays.asList(
                new AddressResponse("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", new BigDecimal("1000.00"), lastSynchronizedTime),
                new AddressResponse("1BoatSLRHtKNngkdXEeobR76b53LETtpyT", new BigDecimal("500.00"), lastSynchronizedTime)
        );
        when(bitcoinWalletService.getWallet()).thenReturn(expectedResponses);
        ResponseEntity<?> result = bitcoinWalletController.getWallet();
        Assertions.assertEquals(HttpStatus.OK,result.getStatusCode());
        Assertions.assertEquals(expectedResponses ,result.getBody());
    }

    @Test
    public void addAddress_ShouldReturnConfirmationMessage() throws Exception {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setAddress("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa");

        ResponseEntity<?> result = bitcoinWalletController.addAddress(addressRequest);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals("address " + addressRequest.getAddress() + " added", result.getBody());
        verify(bitcoinWalletService, times(1)).addAddress(addressRequest);
    }

    @Test
    public void removeAddress_ShouldReturnConfirmationMessage() throws Exception {
        String address = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";

        ResponseEntity<?> result = bitcoinWalletController.removeAddress(address);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals("address " + address + " removed", result.getBody());
        verify(bitcoinWalletService, times(1)).removeAddress(address);
    }

    @Test
    public void getTransactions_ShouldReturnTransactionResponse() throws Exception {
        String address = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";
        TransactionResponse expectedResponse = new TransactionResponse(); // Initialize as needed

        when(bitcoinWalletService.getTransactions(address)).thenReturn(expectedResponse);

        ResponseEntity<?> result = bitcoinWalletController.getTransactions(address);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(expectedResponse, result.getBody());
    }

    @Test
    public void getBalance_ShouldReturnBalanceResponse() throws Exception {
        String address = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";
        BalanceResponse expectedResponse = new BalanceResponse(address, new BigDecimal("1000.00")); // Initialize as needed

        when(bitcoinWalletService.getBalance(address)).thenReturn(expectedResponse);

        ResponseEntity<?> result = bitcoinWalletController.getBalance(address);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(expectedResponse, result.getBody());
    }

    @Test
    public void synchronizeTransactions_ShouldReturnSuccessMessage() throws Exception {
        ResponseEntity<?> result = bitcoinWalletController.synchronizeTransactions();

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals("Transactions have been synchronized.", result.getBody());
        verify(bitcoinWalletService, times(1)).synchronizeTransactions();
    }

}