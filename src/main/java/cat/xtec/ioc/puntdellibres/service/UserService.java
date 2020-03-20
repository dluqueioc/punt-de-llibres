package cat.xtec.ioc.puntdellibres.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cat.xtec.ioc.puntdellibres.model.User;
import cat.xtec.ioc.puntdellibres.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(final String username) {
    final User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException(username);
    }

    List<GrantedAuthority> roles = new ArrayList<>();
    roles.add(new SimpleGrantedAuthority("ROLE_USER"));

    return new org.springframework.security.core.userdetails.User(username, user.getPassword(), roles);
  }

}