package com.sim.app.sim_app.config;

import com.sim.app.sim_app.core.exception.MyException;
import com.sim.app.sim_app.core.response.ResultCode;
import com.sim.app.sim_app.core.response.ResultUtil;
import com.sim.app.sim_app.core.vo.ResultMessage;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MyException.class)
    public ResponseEntity<ResultMessage<?>> handleCustomException(MyException ex) { 
        HttpStatus httpStatus = ex.getResultCode().getHttpStatus();
        
        String finalMessage = (ex.getMyMessage() != null && !ex.getMyMessage().trim().isEmpty()) 
                                 ? ex.getMyMessage() 
                                 : ex.getResultCode().message();
                                     
        ResultMessage<?> errorMessage = ResultUtil.error(ex.getResultCode(), finalMessage);
        
        return new ResponseEntity<>(errorMessage, Objects.requireNonNull(httpStatus));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultMessage<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ResultMessage<?> errorMessage = ResultUtil.error(
                ResultCode.PARAMS_ERROR,
                ResultCode.PARAMS_ERROR.message() + ": " + errors
        );
        
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<ResultMessage<?>> handleRateLimitException(RequestNotPermitted ex) {
        ResultMessage<?> errorMessage = ResultUtil.error(
            ResultCode.RATE_LIMIT_ERROR
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<ResultMessage<?>> handleCircuitBreakerOpen(CallNotPermittedException ex) {
        ResultMessage<?> errorMessage = ResultUtil.error(
            ResultCode.CIRCUIT_BREAKER_IS_OPEN
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.SERVICE_UNAVAILABLE);
    } 
}
