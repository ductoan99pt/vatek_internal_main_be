package asia.vatek.project.security.service;

import asia.vatek.project.entity.UserEntity;
import asia.vatek.project.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    final
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserPrinciple loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found with -> username or email : " + username)
                );

        return UserPrinciple.build(userEntity);
    }
}
