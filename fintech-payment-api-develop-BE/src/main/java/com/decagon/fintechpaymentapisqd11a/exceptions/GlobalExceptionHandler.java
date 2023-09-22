package com.decagon.fintechpaymentapisqd11a.exceptions;

import com.decagon.fintechpaymentapisqd11a.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistsException(final UserAlreadyExistException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setDebugMessage("User already exists");

        return ResponseEntity.of(Optional.of(errorResponseDto));
    }


    @ExceptionHandler(EmailAlreadyTakenException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public ResponseEntity<ErrorResponseDto> handleEmailAlreadyTakenException(final EmailAlreadyTakenException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setDebugMessage("Email Already Taken");

        return ResponseEntity.of(Optional.of(errorResponseDto));
    }

    @ExceptionHandler(EmailNotValidException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public ResponseEntity<ErrorResponseDto> handleEmailNotValidException(final EmailNotValidException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setDebugMessage("Email Not Valid");

        return ResponseEntity.of(Optional.of(errorResponseDto));
    }


    @ExceptionHandler(TokenNotFoundException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public ResponseEntity<ErrorResponseDto> handleTokenNotFoundException(final TokenNotFoundException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setDebugMessage("Token Not Found");

        return ResponseEntity.of(Optional.of(errorResponseDto));
    }

    @ExceptionHandler(ConfirmationTokenException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public ResponseEntity<ErrorResponseDto> handleConfirmationTokenException(final ConfirmationTokenException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setDebugMessage("Email Already Confirmed");

        return ResponseEntity.of(Optional.of(errorResponseDto));
    }


    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public ResponseEntity<ErrorResponseDto> handleTokenExpiredException(final TokenExpiredException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setDebugMessage("Token Expired");

        return ResponseEntity.of(Optional.of(errorResponseDto));
    }


    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(final UserNotFoundException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setDebugMessage("User Not Found");

        return ResponseEntity.of(Optional.of(errorResponseDto));
    }

    @ExceptionHandler(IncorrectDetailsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> handleIncorrectDetailsException(final IncorrectDetailsException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setDebugMessage("Incorrect Details");

        return ResponseEntity.of(Optional.of(errorResponseDto));
    }

    @ExceptionHandler(ErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> handleErrorExceptionException(final ErrorException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setDebugMessage("An error occured");

        return ResponseEntity.of(Optional.of(errorResponseDto));
    }

    @ExceptionHandler(InvalidAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> handleInvalidAmountException(final InvalidAmountException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setDebugMessage("Invalid Amount!");

        return ResponseEntity.of(Optional.of(errorResponseDto));
    }

    @ExceptionHandler(IncorrectTransactionPinException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> handleInsufficientBalanceException(final IncorrectTransactionPinException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setDebugMessage("Incorrect Transaction PIN!");

        return ResponseEntity.of(Optional.of(errorResponseDto));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> handleInsufficientBalanceException(final InsufficientBalanceException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setDebugMessage("Insufficient balance!");

        return ResponseEntity.of(Optional.of(errorResponseDto));
    }

    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> handleWalletNotFoundException(final WalletNotFoundException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setDebugMessage("Invalid Account Number or Bank name provided!");

        return ResponseEntity.of(Optional.of(errorResponseDto));
    }
}
