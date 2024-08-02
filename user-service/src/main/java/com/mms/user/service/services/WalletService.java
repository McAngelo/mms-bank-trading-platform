package com.mms.user.service.services;

import com.mms.user.service.dtos.PortfolioRequestDTO;
import com.mms.user.service.dtos.WalletRequestDTO;
import com.mms.user.service.helper.ApiResponseUtil;
import com.mms.user.service.helper.ErrorDetails;
import com.mms.user.service.helper.IApiResponse;
import com.mms.user.service.helper.PageResponse;
import com.mms.user.service.model.Portfolio;
import com.mms.user.service.model.User;
import com.mms.user.service.model.Wallet;
import com.mms.user.service.repositories.PortfolioRepository;
import com.mms.user.service.repositories.UserRepository;
import com.mms.user.service.repositories.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WalletService {
    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public IApiResponse<?> processGetAllWallets(int page, int size){
        try {
            logger.info("Get all wallets");
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
            var wallets = walletRepository.findAll(pageable);
            List<Wallet> walletResponse = wallets.stream().toList();
            logger.info("All wallets {}", wallets);
            var pagedRes = new PageResponse<>(
                    walletResponse,
                    wallets.getNumber(),
                    wallets.getSize(),
                    wallets.getTotalElements(),
                    wallets.getTotalPages(),
                    wallets.isFirst(),
                    wallets.isLast());
            logger.info("wallets process response");
            return ApiResponseUtil.toOkApiResponse(pagedRes, "Successful");
        }catch(Exception exception){
            logger.error("error while retrieving wallets", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public IApiResponse<?> processGetOneWalletById(int walletId){
        try {
            Wallet wallet = checkPortfolio(walletId);

            return ApiResponseUtil.toOkApiResponse(wallet, "Successful");
        }catch(Exception exception){
            logger.error("error : {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public IApiResponse<?> processGetWalletsByUserId(int userId){
        try {
            logger.info("Get one wallet by Id");
            var wallet = walletRepository.findAllByOwnerId(userId);

            logger.info("Get one wallet by Id {}", wallet);

            if(wallet.isEmpty()){
                logger.debug("Could not find wallet");
                return ApiResponseUtil.toNotFoundApiResponse(null, "Wallet not found");
            }

            return ApiResponseUtil.toOkApiResponse(wallet, "Successful");
        }catch(Exception exception){
            logger.error("error : {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public IApiResponse<?> processWalletCreation(WalletRequestDTO requestDto, Authentication connectedUser){
        try {
            logger.info("Creating a Portfolio");
            Optional<User> userOptional = userRepository.findById(requestDto.getUserId());

            logger.info("Get one user by Id {}", userOptional.toString());

            if(userOptional.isEmpty()){
                logger.debug("Could not find user");
                return ApiResponseUtil.toNotFoundApiResponse(null, "User not found");
            }

            User user = userOptional.get();
            logger.debug("find the user {}", user.toString());
            Wallet wallet = new Wallet();
            wallet.setBalance(requestDto.getBalance());
            wallet.setStatus(Wallet.Status.ACTIVE);
            wallet.setOwner(user);
            var response = walletRepository.save(wallet).getId();
            return ApiResponseUtil.toOkApiResponse(response, "Wallet created successfully");
        }catch(Exception exception){
            logger.error("error while processing portfolio creation: {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public IApiResponse<?> processUpdateWallet(int id, WalletRequestDTO requestDto, Authentication connectedUser){
        try {
            Wallet wallet = checkPortfolio(id);
            wallet.setBalance(requestDto.getBalance());
            wallet.setStatus(requestDto.getStatus());
            var response = walletRepository.save(wallet).getId();
            return ApiResponseUtil.toOkApiResponse(response, "Updated wallet successfully");
        }catch(Exception exception){
            logger.error("error while processing wallet: {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public IApiResponse<?> processDeleteWallet(int walletId){
        try {
            Wallet wallet = checkPortfolio(walletId);
            wallet.setStatus(Wallet.Status.DISABLED);

            var response = walletRepository.saveAndFlush(wallet).getId();
            return ApiResponseUtil.toOkApiResponse(response, "Wallet has been disabled successfully");
        }catch(Exception exception){
            logger.error("error attempting to disable account: {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    private Wallet checkPortfolio(int walletId){
        logger.info("Process find wallet by id");
        Optional<Wallet> walletOptional = walletRepository.findById(walletId);

        logger.info("Get one wallet by Id {}", walletOptional);

        if(walletOptional.isEmpty()){
            logger.debug("Could not find optional");
            throw new EntityNotFoundException("Wallet not found");
        }
        return walletOptional.get();
    }
}
