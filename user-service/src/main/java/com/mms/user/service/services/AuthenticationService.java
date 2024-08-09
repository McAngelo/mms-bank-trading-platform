package com.mms.user.service.services;

import com.mms.user.service.dtos.*;
import com.mms.user.service.helper.ApiResponseUtil;
import com.mms.user.service.helper.EmailTemplateName;
import com.mms.user.service.helper.IApiResponse;
import com.mms.user.service.model.*;
import com.mms.user.service.model.mappers.UserMapper;
import com.mms.user.service.repositories.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.Port;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;
    private final OrderMicroServiceHttpClient orderMicroServiceHttpClient;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public IApiResponse<?> register(RegistrationRequestDto request) throws MessagingException {

        Role userRole = findRole("TRADER");

        User user = buildUser(request, userRole);
        User savedUser = saveUser(user);

        WalletRequestDTO createWalletRequest = new WalletRequestDTO();
        createWalletRequest.setUserId(savedUser.getId());
        createWalletRequest.setBalance(BigDecimal.ZERO);
        createWallet(createWalletRequest);

        PortfolioRequestDTO createPortofolioRequest = new PortfolioRequestDTO();
        createPortofolioRequest.setName("Basic Portfolio");
        createPortofolioRequest.setUserId(savedUser.getId());
        createPortfolio(createPortofolioRequest);


        return ApiResponseUtil.toOkApiResponse(savedUser, "Successful");
    }

    public IApiResponse<?> authenticate(AuthenticationRequestDto request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.getFullName());

        RegistrationResponseDto authUser = new RegistrationResponseDto();
        authUser.userId = user.getId();
        authUser.fullName = user.getFullName();
        authUser.email = user.getEmail();
        authUser.accountLocked = user.isAccountLocked();
        authUser.enabled = user.isEnabled();
        authUser.authorities = user.getAuthorities().toString();
        authUser.roles = user.getRoles();
        authUser.createdDate = user.getCreatedDate();
        authUser.lastModifiedDate = user.getLastModifiedDate();



        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
        var response = AuthenticationResponseDto.builder()
                .token(jwtToken)
                .user(authUser)
                .build();
        return ApiResponseUtil.toOkApiResponse(response, "Successful");
    }

    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been send to the same email address");
        }

        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    private String generateAndSaveActivationToken(User user) {
        // Generate a token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken;
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }

    private Role findRole(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalStateException("ROLE " + roleName + " was not initiated"));
    }

    private boolean createWallet(WalletRequestDTO request) {
        orderMicroServiceHttpClient.createWallet(request);
        return true;
    }

    private boolean createPortfolio(PortfolioRequestDTO request) {
        orderMicroServiceHttpClient.createPortfolio(request);
        return true;
    }

    private User buildUser(RegistrationRequestDto request, Role userRole) {
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(true)
                .createdDate(LocalDateTime.now())
                .roles(List.of(userRole))
                .build();
        return user;
    }

    private User saveUser(User user) {
        return userRepository.save(user);
    }

}
