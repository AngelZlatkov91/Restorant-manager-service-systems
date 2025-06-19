package User.management.user.management.Web;


import User.management.user.management.Exception.ErrorResponse;
import User.management.user.management.Exception.UserDontExistExp;
import User.management.user.management.Exception.UserIsAlreadyExistExp;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                "Unauthorized",
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler({BadCredentialsException.class,UsernameNotFoundException.class,EntityNotFoundException.class, UserDontExistExp.class, UserIsAlreadyExistExp.class})
    public ResponseEntity<ErrorResponse> handleEntityNotFound(
            EntityNotFoundException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                "Not Found",
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        ErrorResponse error = new ErrorResponse(
                "Validation Error",
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
