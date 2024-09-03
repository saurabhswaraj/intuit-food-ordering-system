package com.intuit.foodorderingsystem.filter;

import com.intuit.foodorderingsystem.constant.AppConstant;
import com.intuit.foodorderingsystem.enums.Role;
import com.intuit.foodorderingsystem.exception.UnauthorisedException;
import com.intuit.foodorderingsystem.model.JWTDecodeModel;
import com.intuit.foodorderingsystem.repository.RestaurantAuthenticationRepository;
import com.intuit.foodorderingsystem.repository.RestaurantRefreshTokenRepository;
import com.intuit.foodorderingsystem.repository.UserAuthenticationRepository;
import com.intuit.foodorderingsystem.repository.UserRefreshTokenRepository;
import com.intuit.foodorderingsystem.service.JWTTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JWTTokenService jwtTokenService;

    private final RestaurantRefreshTokenRepository restaurantRefreshTokenRepository;

    private final UserRefreshTokenRepository userRefreshTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader(AppConstant.AUTHORIZATION);
        if(authorization == null){
            throw new UnauthorisedException("Authorization header is null");
        }

        String jwtToken = authorization.substring(7);
        JWTDecodeModel jwtDecodeModel = jwtTokenService.decodeJWTToken(jwtToken);
        Role role = jwtDecodeModel.getRole();
        String pathInfo = request.getPathInfo();
        request.getMethod();
        Long id = jwtDecodeModel.getId();

        if(Role.RESTAURANT == role) {
            if(pathInfo != null && !pathInfo.startsWith(AppConstant.RESTAURANT_PATH_PREFIX)) {
                throw new UnauthorisedException("This role can't access this url");
            }
            restaurantRefreshTokenRepository.findById(id).ifPresentOrElse(restaurantRefreshTokenEntity -> {
                if(!restaurantRefreshTokenEntity.getIsActive()) {
                    throw new UnauthorisedException("Token expired, Please login again");
                }
            }, () -> {throw new UnauthorisedException("Restaurant is not present");});
        }

        if(Role.USER == role) {
            if (pathInfo != null && !pathInfo.startsWith(AppConstant.USER_PATH_PREFIX) ) {
                throw new UnauthorisedException("This role can't access this url");
            }

            userRefreshTokenRepository.findById(id).ifPresentOrElse(userRefreshTokenEntity -> {
                if(!userRefreshTokenEntity.getIsActive()) {
                    throw new UnauthorisedException("Token expired, Please login again");
                }
            }, () -> {throw new UnauthorisedException("User is not present");});
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return false;
    }
}
