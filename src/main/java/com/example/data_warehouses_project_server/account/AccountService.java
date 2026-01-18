package com.example.data_warehouses_project_server.account;

import com.example.data_warehouses_project_server.domain.oltp.constant.Role;
import com.example.data_warehouses_project_server.domain.oltp.entity.AccountEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.AddressEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.CustomerEntity;
import com.example.data_warehouses_project_server.domain.oltp.repository.AccountRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.AddressRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.CustomerRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.TokenRepository;
import com.example.data_warehouses_project_server.exception.BadRequestException;
import com.example.data_warehouses_project_server.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
class AccountService {

    private final AccountRepository accountRepository;
    private final TokenRepository tokenRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    AccountService(
            AccountRepository accountRepository,
            TokenRepository tokenRepository,
            CustomerRepository customerRepository,
            AddressRepository addressRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.accountRepository = accountRepository;
        this.tokenRepository = tokenRepository;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    List<AccountResponse> getAllAccounts() {
        return this.accountRepository.findAllWithCustomer().stream().map(AccountResponse::fromEntity).toList();
    }

    AccountResponse getAccountById(Long id) {
        return this.accountRepository.findById(id)
                .map(AccountResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    @Transactional
    Long createAccount(CreateAccountForm form) {
        if (this.accountRepository.existsByUsernameOrEmail(form.getUsername(), form.getEmail())) {
            throw new BadRequestException("Username or email already in use");
        }

        CustomerEntity customer = null;

        if (form.getRole() == Role.USER) {
            if (form.getFirstName() == null || form.getLastName() == null) {
                throw new BadRequestException("Firstname and lastname must not be null if the role is USER");
            }

            customer = this.customerRepository.save(new CustomerEntity(
                    form.getFirstName(),
                    form.getLastName(),
                    form.getDateOfBirth(),
                    form.getPhoneNumber(),
                    OffsetDateTime.now()
            ));
        }

        return this.accountRepository.save(new AccountEntity(
                form.getUsername(),
                form.getEmail(),
                this.passwordEncoder.encode(form.getPassword()),
                form.getRole(),
                customer
        )).getId();
    }

    @Transactional
    void deleteAccount(Long id) { // TODO: Should this be able to also delete customers + addresses?
        this.tokenRepository.deleteAllByAccountId(id);
        this.accountRepository.deleteById(id);
    }

    List<AddressResponse> getAllAddresses() {
        return this.addressRepository.findAllWithCustomerAndAccount().stream().map(AddressResponse::fromEntity).toList();
    }

    List<AddressResponse> getAllAddressesByAccount(Long id) {
        return this.addressRepository.findAllByAccountIdWithCustomerAndAccount(id).stream()
                .map(AddressResponse::fromEntity).toList();
    }

    AddressResponse getAddressById(Long id) {
        return this.addressRepository.findById(id)
                .map(AddressResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("Address not found"));
    }

    @Transactional
    Long createAddress(Long id, CreateAddressForm form) {
        Optional<AccountEntity> account = this.accountRepository.findById(id);

        if (account.isEmpty() || account.get().getCustomer() == null) {
            throw new NotFoundException("Customer account not found");
        }

        return this.addressRepository.save(new AddressEntity(
                form.getStreet(),
                form.getCity(),
                form.getRegion(),
                form.getCountry(),
                form.getPostalCode(),
                account.get().getCustomer()
        )).getId();
    }

    @Transactional
    void deleteAddress(Long id) { // TODO: It should create an error if the address is used for a delivery
        this.addressRepository.deleteById(id);
    }
}
