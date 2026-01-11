package com.example.data_warehouses_project_server.account;

import com.example.data_warehouses_project_server.domain.oltp.constant.Role;
import com.example.data_warehouses_project_server.domain.oltp.entity.AccountEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.AddressEntity;
import com.example.data_warehouses_project_server.domain.oltp.entity.CustomerEntity;
import com.example.data_warehouses_project_server.domain.oltp.repository.AccountRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.AddressRepository;
import com.example.data_warehouses_project_server.domain.oltp.repository.CustomerRepository;
import com.example.data_warehouses_project_server.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    AccountService(
            AccountRepository accountRepository,
            CustomerRepository customerRepository,
            AddressRepository addressRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    List<AccountResponse> getAllAccounts() {
        return this.accountRepository.findAll().stream().map(AccountResponse::fromEntity).toList();
    }

    AccountResponse getAccountById(Long id) {
        return this.accountRepository.findById(id)
                .map(AccountResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    @Transactional
    Long createAccount(CreateAccountForm form) {
        if (this.accountRepository.findByUsername(form.getUsername()).isPresent()) {
            throw new UsernameNotFoundException("Username taken");
        }

        if (this.accountRepository.findByEmail(form.getEmail()).isPresent()) {
            throw new UsernameNotFoundException("Email taken");
        }

        CustomerEntity customer = null;

        if (form.getRole() == Role.USER && form.getFirstName() != null && form.getLastName() != null) {
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
    void deleteAccount(Long id) {
        this.accountRepository.deleteById(id);
    }

    List<AddressResponse> getAllAddresses() {
        return this.addressRepository.findAll().stream().map(AddressResponse::fromEntity).toList();
    }

    List<AddressResponse> getAllAddressesByAccount(Long id) {
        Optional<AccountEntity> account = this.accountRepository.findById(id);

        if (account.isEmpty() || account.get().getCustomer() == null) {
            throw new NotFoundException("Customer account not found");
        }

        // TODO: Test this, because it might cause N + 1 problem, if so try to query from AddressRepository instead
        return account.get().getCustomer().getAddresses().stream().map(AddressResponse::fromEntity).toList();
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
    void deleteAddress(Long id) {
        this.addressRepository.deleteById(id);
    }
}
