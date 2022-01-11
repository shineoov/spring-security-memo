package shineoov.springsecuritymemo.domain;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public Account save(AccountDto accountDto) {
        return accountRepository.save(modelMapper.map(accountDto, Account.class));
    }

}
