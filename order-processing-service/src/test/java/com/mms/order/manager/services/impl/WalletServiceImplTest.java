//package com.mms.order.manager.services.impl;
//
//import com.mms.order.manager.models.User;
//import com.mms.order.manager.models.Wallet;
//import com.mms.order.manager.repositories.UserRepository;
//import com.mms.order.manager.repositories.WalletRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.internal.verification.Times;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoSettings;
//import org.mockito.quality.Strictness;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
//@RunWith(SpringRunner.class)
//class WalletServiceImplTest {
//    @Mock
//    WalletRepository walletRepository;
//
//    @Mock
//    UserRepository userRepository;
//
//    @InjectMocks
//    private WalletServiceImpl walletService;
//
//
//    @Test
//    void shouldCreateWalletWhenUserIsFound() {
//        // GIVEN
//        var mockedUser = mock(User.class);
//
//        // WHEN
//        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockedUser));
//
//        var IsWalletCreated = walletService.createWallet(1L, new BigDecimal(100));
//
//        // THEN
//        verify(walletRepository, new Times(1)).save(any(Wallet.class));
//        assertTrue(IsWalletCreated);
//    }
//
//    @Test
//    void shouldNotCreateWalletWhenUserIsNotFound() {
//        // WHEN
//        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        var IsWalletCreated = walletService.createWallet(1L, new BigDecimal(100));
//
//        // THEN
//        assertFalse(IsWalletCreated);
//    }
//
//    @Test
//    void shouldGetBalanceByWalletIdWhenUserIsFoundAndWalletIsActive() {
//        // GIVEN
//        var EXPECTED_BALANCE = new BigDecimal(100);
//
//        var wallet = Wallet.builder()
//                .balance(EXPECTED_BALANCE)
//                .isActive(true)
//                .user(mock(User.class))
//                .build();
//
//        // WHEN
//        when(walletRepository.findById(anyLong())).thenReturn(Optional.of(wallet));
//
//        var optionalBalance = walletService.getBalanceByWalletId(1L);
//
//        // THEN
//        assertTrue(optionalBalance.isPresent(), "The balance should be present");
//
//        var balance = optionalBalance.get();
//        assertNotNull(balance, "The balance should not be null");
//        assertEquals(EXPECTED_BALANCE, balance);
//    }
//
//    @Test
//    void shouldNotGetBalanceByWalletIdWhenUserIsFoundAndWalletIsInActive() {
//        // GIVEN
//        var wallet = Wallet.builder()
//                .balance(new BigDecimal(100))
//                .isActive(false)
//                .user(mock(User.class))
//                .build();
//
//        // WHEN
//        when(walletRepository.findById(anyLong())).thenReturn(Optional.of(wallet));
//        var optionalBalance = walletService.getBalanceByWalletId(1L);
//
//        // THEN
//        assertTrue(optionalBalance.isEmpty(), "The balance should be empty");
//    }
//
//    @Test
//    void shouldNotGetBalanceByWalletIdWhenUserIsFound() {
//        // WHEN
//        when(walletRepository.findById(anyLong())).thenReturn(Optional.empty());
//        var optionalBalance = walletService.getBalanceByWalletId(1L);
//
//        // THEN
//        assertTrue(optionalBalance.isEmpty(), "The balance should be empty");
//    }
//
//    @Test
//    void shouldGetBalanceByUserIdWhenUserIsFoundAndWalletIsActive() {
//        // GIVEN
//        var EXPECTED_BALANCE = new BigDecimal(100);
//
//        var wallet = Wallet.builder()
//                .balance(EXPECTED_BALANCE)
//                .isActive(true)
//                .user(mock(User.class))
//                .build();
//
//        // WHEN
//        when(walletRepository.findByUserId(anyLong())).thenReturn(Optional.of(wallet));
//        var optionalBalance = walletService.getBalanceByUserId(1L);
//
//        // THEN
//        assertTrue(optionalBalance.isPresent(), "The balance should be present");
//
//        var balance = optionalBalance.get();
//        assertNotNull(balance, "The balance should not be null");
//        assertEquals(EXPECTED_BALANCE, balance);
//    }
//
//    @Test
//    void shouldNotGetBalanceByUserIdWhenUserIsNotFound() {
//        // WHEN
//        when(walletRepository.findByUserId(anyLong())).thenReturn(Optional.empty());
//
//        var optionalBalance = walletService.getBalanceByUserId(1L);
//
//        // THEN
//        assertTrue(optionalBalance.isEmpty(), "The balance should be empty");
//    }
//
//    @Test
//    void shouldNotGetBalanceByUserIdWhenUserIsFoundAndWalletIsInactive() {
//        // GIVEN
//        var wallet = Wallet.builder()
//                .balance(new BigDecimal(100))
//                .isActive(false)
//                .user(mock(User.class))
//                .build();
//
//        // WHEN
//        when(walletRepository.findByUserId(anyLong())).thenReturn(Optional.of(wallet));
//        var optionalBalance = walletService.getBalanceByUserId(1L);
//
//        // THEN
//        assertTrue(optionalBalance.isEmpty(), "The balance should be empty");
//    }
//
//    @Test
//    void shouldCreditWalletWhenWalletIsFoundAndActive() {
//        // GIVEN
//        var INITIAL_BALANCE = new BigDecimal(100);
//        var CREDIT_AMOUNT = new BigDecimal(50);
//        var EXPECTED_BALANCE = INITIAL_BALANCE.add(CREDIT_AMOUNT);
//
//        var wallet = Wallet.builder()
//                .balance(INITIAL_BALANCE)
//                .isActive(true)
//                .user(mock(User.class))
//                .build();
//
//        // WHEN
//        when(walletRepository.findById(anyLong())).thenReturn(Optional.of(wallet));
//        var result = walletService.creditWallet(1L, CREDIT_AMOUNT);
//
//        // THEN
//        assertTrue(result);
//        verify(walletRepository, new Times(1)).save(any(Wallet.class));
//        assertEquals(EXPECTED_BALANCE, wallet.getBalance());
//    }
//
//    @Test
//    void shouldReturnFalseWhenWalletNotFoundForCredit() {
//        // GIVEN
//        when(walletRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        // WHEN
//        var result = walletService.creditWallet(1L, new BigDecimal(100));
//
//        // THEN
//        assertFalse(result);
//    }
//
//    @Test
//    void shouldReturnFalseWhenWalletIsInactiveForCredit() {
//        // GIVEN
//        Wallet wallet = Wallet.builder()
//                .balance(new BigDecimal(100))
//                .isActive(false)
//                .user(mock(User.class))
//                .build();
//
//        when(walletRepository.findById(anyLong())).thenReturn(Optional.of(wallet));
//
//        // WHEN
//        boolean result = walletService.creditWallet(1L, new BigDecimal(100));
//
//        // THEN
//        assertFalse(result, "Should return false when wallet is inactive");
//    }
//
//    @Test
//    void shouldDebitWalletWhenWalletIsFoundAndActive() {
//        // GIVEN
//        var INITIAL_BALANCE = new BigDecimal(100);
//        var DEBIT_AMOUNT = new BigDecimal(50);
//        var EXPECTED_BALANCE = INITIAL_BALANCE.subtract(DEBIT_AMOUNT);
//
//        var wallet = Wallet.builder()
//                .balance(INITIAL_BALANCE)
//                .isActive(true)
//                .user(mock(User.class))
//                .build();
//
//        // WHEN
//        when(walletRepository.findById(anyLong())).thenReturn(Optional.of(wallet));
//        var result = walletService.debitWallet(1L, DEBIT_AMOUNT);
//
//        // THEN
//        assertTrue(result);
//        verify(walletRepository, new Times(1)).save(any(Wallet.class));
//        assertEquals(EXPECTED_BALANCE, wallet.getBalance());
//    }
//
//
//    @Test
//    void shouldReturnFalseWhenWalletNotFoundForDebit() {
//        // GIVEN
//        when(walletRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        // WHEN
//        var result = walletService.debitWallet(1L, new BigDecimal(100));
//
//        // THEN
//        assertFalse(result);
//    }
//
//    @Test
//    void shouldReturnFalseWhenWalletIsInactiveForDebit() {
//        // GIVEN
//        Wallet wallet = Wallet.builder()
//                .balance(new BigDecimal(100))
//                .isActive(false)
//                .user(mock(User.class))
//                .build();
//
//        when(walletRepository.findById(anyLong())).thenReturn(Optional.of(wallet));
//
//        // WHEN
//        boolean result = walletService.debitWallet(1L, new BigDecimal(100));
//
//        // THEN
//        assertFalse(result, "Should return false when wallet is inactive");
//    }
//
//    @Test
//    void shouldEnableWalletWhenWalletIsFoundAndInActive() {
//        // GIVEN
//        Wallet wallet = Wallet.builder()
//                .balance(new BigDecimal(100))
//                .isActive(false)
//                .user(mock(User.class))
//                .build();
//
//        when(walletRepository.findById(anyLong())).thenReturn(Optional.of(wallet));
//        boolean result = walletService.enableWallet(1L);
//
//        // WHEN
//        assertTrue(result);
//        assertTrue(wallet.isActive());
//    }
//
//    @Test
//    void shouldReturnFalseWhenWalletIsNotFoundToBeEnabled() {
//        // WHEN
//        when(walletRepository.findById(anyLong())).thenReturn(Optional.empty());
//        boolean result = walletService.enableWallet(1L);
//
//        // THEN
//        assertFalse(result);
//    }
//
//    @Test
//    void shouldDisableWalletWhenWalletIsFoundAndActive() {
//        // GIVEN
//        Wallet wallet = Wallet.builder()
//                .balance(new BigDecimal(100))
//                .isActive(true)
//                .user(mock(User.class))
//                .build();
//
//        when(walletRepository.findById(anyLong())).thenReturn(Optional.of(wallet));
//        boolean result = walletService.disableWallet(1L);
//
//        // WHEN
//        assertTrue(result);
//        assertFalse(wallet.isActive());
//    }
//
//    @Test
//    void shouldReturnFalseWhenWalletIsNotFoundToBeDisabled() {
//        // WHEN
//        when(walletRepository.findById(anyLong())).thenReturn(Optional.empty());
//        boolean result = walletService.disableWallet(1L);
//
//        // THEN
//        assertFalse(result);
//    }
//}