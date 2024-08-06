package com.mms.user.service.services;

import com.mms.user.service.dtos.PortfolioRequestDTO;
import com.mms.user.service.helper.*;
import com.mms.user.service.model.Portfolio;
import com.mms.user.service.model.User;
import com.mms.user.service.repositories.PortfolioRepository;
import com.mms.user.service.repositories.UserRepository;
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
public class PortfolioService {
    private static final Logger logger = LoggerFactory.getLogger(PortfolioService.class);
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    public IApiResponse<?> processGetAllPortfolios(int page, int size){
        try {
            logger.info("Get all Portfolios");
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
            var portfolios = portfolioRepository.findAll(pageable);
            List<Portfolio> portfolioResponse = portfolios.stream().toList();
            logger.info("All portfolios {}", portfolios);
            var pagedRes = new PageResponse<>(
                    portfolioResponse,
                    portfolios.getNumber(),
                    portfolios.getSize(),
                    portfolios.getTotalElements(),
                    portfolios.getTotalPages(),
                    portfolios.isFirst(),
                    portfolios.isLast());
            logger.info("registration process response");
            return ApiResponseUtil.toOkApiResponse(pagedRes, "Successful");
        }catch(Exception exception){
            logger.error("error while retrieving users", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public IApiResponse<?> processGetOnePortfolioById(int portfolioId){
        try {
            Portfolio portfolio = checkPortfolio(portfolioId);

            return ApiResponseUtil.toOkApiResponse(portfolio, "Successful");
        }catch(Exception exception){
            logger.error("error : {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public IApiResponse<?> processGetPortfoliosByUserId(int userId){
        try {
            logger.info("Get one portfolio by Id");
            var portfolio = portfolioRepository.findAllByOwnerId(userId);

            logger.info("Get one portfolio by Id {}", portfolio);

            if(portfolio.isEmpty()){
                logger.debug("Could not find portfolio");
                return ApiResponseUtil.toNotFoundApiResponse(null, "Portfolio not found");
            }

            return ApiResponseUtil.toOkApiResponse(portfolio, "Successful");
        }catch(Exception exception){
            logger.error("error : {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public IApiResponse<?> processPortfolioCreation(PortfolioRequestDTO requestDto, Authentication connectedUser){
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
            Portfolio portfolio = new Portfolio();
            portfolio.setPortfolioName(requestDto.getPortfolioName());
            portfolio.setPortfolioType(requestDto.getPortfolioType());
            portfolio.setStatus(Portfolio.Status.ACTIVE);
            portfolio.setOwner(user);
            var response = portfolioRepository.save(portfolio).getId();
            return ApiResponseUtil.toOkApiResponse(response, "Portfolio created successfully");
        }catch(Exception exception){
            logger.error("error while processing portfolio creation: {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public IApiResponse<?> processUpdatePortfolio(int id, PortfolioRequestDTO requestDto, Authentication connectedUser){
        try {
            Portfolio portfolio = checkPortfolio(id);
            portfolio.setPortfolioName(requestDto.getPortfolioName());
            portfolio.setPortfolioType(requestDto.getPortfolioType());
            portfolio.setStatus(requestDto.getStatus());
            var response = portfolioRepository.save(portfolio).getId();
            return ApiResponseUtil.toOkApiResponse(response, "Updated user successfully");
        }catch(Exception exception){
            logger.error("error while processing login: {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    public IApiResponse<?> processDeletePortfolio(int portoflioId){
        try {
            Portfolio portfolio = checkPortfolio(portoflioId);
            portfolio.setStatus(Portfolio.Status.DISABLED);

            var response = portfolioRepository.saveAndFlush(portfolio).getId();
            return ApiResponseUtil.toOkApiResponse(response, "Portfolio account disabled successfully");
        }catch(Exception exception){
            logger.error("error attempting to disable account: {}", exception);
            ArrayList<ErrorDetails> error = new ArrayList<>();
            error.add(new ErrorDetails(exception.getMessage(), exception.toString()));
            return ApiResponseUtil.toBadRequestApiResponse("Error", error);
        }
    }

    private Portfolio checkPortfolio(int portoflioId){
        logger.info("Processing login authentication");
        Optional<Portfolio> portfolioOptional = portfolioRepository.findById(portoflioId);

        logger.info("Get one portfolio by Id {}", portfolioOptional);

        if(portfolioOptional.isEmpty()){
            logger.debug("Could not find user");
            throw new EntityNotFoundException("User not found");
            //return ApiResponseUtil.toNotFoundApiResponse(null, "User not found");
        }

        return portfolioOptional.get();
    }
}
