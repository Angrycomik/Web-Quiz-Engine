package engine.service;

import engine.adapter.AppUserAdapter;
import engine.entity.AppUser;
import engine.exception.UserAlreadyExistsException;
import engine.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(AppUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = repository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));

        return new AppUserAdapter(user);
    }

    public void saveUser(AppUser user){
        if(repository.existsByEmail(user.getEmail())){throw new UserAlreadyExistsException("Username already exists");}
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }
}
