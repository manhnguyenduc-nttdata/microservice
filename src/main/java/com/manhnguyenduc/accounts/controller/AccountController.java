package com.manhnguyenduc.accounts.controller;

import com.manhnguyenduc.accounts.constants.AccountsConstants;
import com.manhnguyenduc.accounts.dto.CustomerDto;
import com.manhnguyenduc.accounts.dto.ErrorResponseDto;
import com.manhnguyenduc.accounts.dto.ResponseDto;
import com.manhnguyenduc.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Accounts in GoldenBank",
        description = "CRUD REST APIs for Accounts in GoldenBank to CREATE, UPDATE, FETCH and DELETE account details"
)
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class AccountController {

    private IAccountsService iAccountsService;

    @Operation(
            summary = "Create a new account",
            description = "Create a new account in GoldenBank"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Account created successfully"
    )
    @ApiResponse(
            responseCode = "500",
            description = "An error occurred. Please try again or contact Dev team",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        iAccountsService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch account details",
            description = "Fetch account details in GoldenBank"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Request processed successfully"
    )
    @ApiResponse(
            responseCode = "500",
            description = "An error occurred. Please try again or contact Dev team",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                           @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be 10 digits")
                                                           String mobileNumber) {

        CustomerDto customerDto = iAccountsService.fetchAccountDetails(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(
            summary = "Update account details",
            description = "Update account details in GoldenBank"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Request processed successfully"
    )
    @ApiResponse(
            responseCode = "417",
            description = "Expected update operation failed."
    )
    @ApiResponse(
            responseCode = "500",
            description = "An error occurred. Please try again or contact Dev team",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {

        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete account details",
            description = "Delete account details in GoldenBank"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Request processed successfully"
    )
    @ApiResponse(
            responseCode = "417",
            description = "Expected delete operation failed."
    )
    @ApiResponse(
            responseCode = "500",
            description = "An error occurred. Please try again or contact Dev team",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be 10 digits")
                                                            String mobileNumber) {

        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }
}
