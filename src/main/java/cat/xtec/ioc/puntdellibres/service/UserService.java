package cat.xtec.ioc.puntdellibres.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class UserService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) {
    return null;
  }

}