package com.mms.order.manager.helpers;

import com.mms.order.manager.models.*;
import com.mms.order.manager.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class Seeder implements ApplicationRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final WalletRepository walletRepository;
    private final PortfolioRepository portfolioRepository;
    private final ExchangeRepository exchangeRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var role = Role.builder()
                .name("user")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        var user = User.builder()
                .fullName("John Doe")
                .dob(LocalDate.now())
                .email("steven@mail.com")
                .password("password")
                .role(role)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();

        var wallet = Wallet.builder()
                .user(user)
                .isActive(true)
                .balance(new BigDecimal(1000))
                .build();

        var portfolio = Portfolio.builder()
                .user(user)
                .name("Default Portfolio")
                .isDefault(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        var exchange = Exchange.builder()
                .name("mallon exchange 1")
                .slug("EXCHANGE1")
                .baseUrl("https://exchange.matraining.com")
                .privateKey("e58a81a8-637d-4c36-90a9-72275548f92f")
                .isActive(true)
                .build();

        var exchange2 = Exchange.builder()
                .name("mallon exchange 2")
                .slug("EXCHANGE2")
                .baseUrl("https://exchange2.matraining.com")
                .privateKey("2f458160-39ab-439f-bf77-ad7858e9c290")
                .isActive(true)
                .build();

//        roleRepository.save(role);
//        userRepository.save(user);
//        walletRepository.save(wallet);
//        portfolioRepository.save(portfolio);
//        exchangeRepository.save(exchange2);
    }
}
