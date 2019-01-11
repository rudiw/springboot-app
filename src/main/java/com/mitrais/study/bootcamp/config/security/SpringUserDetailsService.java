package com.mitrais.study.bootcamp.config.security;

import com.google.common.collect.ImmutableList;
import com.mitrais.study.bootcamp.dao.PersonDao;
import com.mitrais.study.bootcamp.dao.RoleDao;
import com.mitrais.study.bootcamp.model.jpa.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author rudi
 */
@Service("springUserDetailsService")
@Transactional(readOnly = true)
public class SpringUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(SpringUserDetailsService.class);

    private static final String PREFIX_ROLE = "ROLE_";
    private static final String GUEST = "GUEST";

    @Inject
    private PersonDao personDao;
    @Inject
    private RoleDao roleDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        @Nullable final Person person = personDao.findOneByUsername(username);

        if (person != null) {
//			if ( ImmutableList.of(PersonStatus.ACTIVE, PersonStatus.VERIFIED).contains(person.getStatus()) ) {
            if (true) {
                return new User(String.valueOf(person.getId()), person.getPassword(), getAuthorities(person));
            } else {
//				log.warn(String.format("User '%s' is disabled with status '%s'", username, person.getStatus()));
                throw new DisabledException(String.format("User '%s' is disabled.", username));
            }
        } else {
            log.warn(String.format("User not found for '%s'", username));
            throw new UsernameNotFoundException(String.format("User not found for '%s'", username));
        }
    }

    private Set<GrantedAuthority> getAuthorities(final Person person) {
        final Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        if (person.getRoleIds() != null && !person.getRoleIds().isEmpty()) {
            final Map<Long, String> roleMap = roleDao.findNames(ImmutableList.copyOf(person.getRoleIds()));

            person.getRoleIds().forEach(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) {
                    final GrantedAuthority grantedAuthority = new
                            SimpleGrantedAuthority(PREFIX_ROLE + roleMap.get(aLong));
                    authorities.add(grantedAuthority);
                }
            });
        } else {
            final GrantedAuthority grantedAuthority = new
                    SimpleGrantedAuthority(PREFIX_ROLE + GUEST);
            authorities.add(grantedAuthority);
        }

        return authorities;
    }

}
