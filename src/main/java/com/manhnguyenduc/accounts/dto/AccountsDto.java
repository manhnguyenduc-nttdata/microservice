package com.manhnguyenduc.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account Information"
)
public class AccountsDto {

    @Schema(
            description = "Account number of the account",
            example = "1234567890"
    )
    @NotEmpty(message = "Account number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Account type of the account",
            example = "Savings"
    )
    @NotEmpty(message = "Account type can not be a null or empty")
    private String accountType;

    @Schema(
            description = "Branch address of the account",
            example = "123 Main Street"
    )
    @NotEmpty(message = "Branch address can not be a null or empty")
    private String branchAddress;
}
