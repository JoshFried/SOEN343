package com.soen343.shs.dal.service;

import com.soen343.shs.dal.model.User;
import com.soen343.shs.dal.repository.UserRepository;
import com.soen343.shs.dal.service.exceptions.InvalidFieldException;
import com.soen343.shs.dal.service.exceptions.SHSUserAlreadyExistsException;
import com.soen343.shs.dal.service.validators.FieldValidator;
import com.soen343.shs.dto.RegistrationDTO;
import com.soen343.shs.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
public class UserService {
    private final ConversionService mvcConversionService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FieldValidator fieldValidator;
    private final AuthenticationProvider authProvider;

    @Autowired
    public UserService(final ConversionService mvcConversionService,
                       final UserRepository userRepository,
                       final PasswordEncoder passwordEncoder,
                       @Qualifier("SHSFieldValidator") final FieldValidator fieldValidator,
                       final AuthenticationProvider authProvider) {
        this.mvcConversionService = mvcConversionService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fieldValidator = fieldValidator;
        this.authProvider = authProvider;
    }

    @Transactional
    public UserDTO createUser(final RegistrationDTO details) throws SHSUserAlreadyExistsException, InvalidFieldException {

        // check to see if username/email exists already, if so throw exception
        if (userRepository.findByEmail(details.getEmail()) != null || userRepository.findByUsername(details.getUsername()) == null) {
            throw new SHSUserAlreadyExistsException("Username/email already exists!");
        }

        fieldValidator.validateRegistration(details); // Validate the rest of the fields;

        final User user = mvcConversionService.convert(details, User.class); // Convert to our model
        assert user != null;
        user.setPassword(passwordEncoder.encode(details.getPassword())); // encode password

        userRepository.save(user); // save to DB

        return mvcConversionService.convert(details, UserDTO.class); // return the dto object to our user
    }

    public UserDTO login(final HttpServletRequest request, final String username, final String password) {
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, passwordEncoder.encode(password));
        authProvider.authenticate(authenticationToken);

        final SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authenticationToken);

        final HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);

        return mvcConversionService.convert(userRepository.findByUsername(username), UserDTO.class);
    }

    public UserDTO updateUser(final UserDTO userDTO) {
        userRepository.save(mvcConversionService.convert(userDTO, User.class));
        return userDTO;
    }


    public UserDTO getUserByUsername(final String username) {
        final User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username doesn't exist");
        } else {
            return mvcConversionService.convert(user, UserDTO.class);
        }
    }

    public UserDTO getUserById(final Long id) {
        final User user = userRepository.findById(id).get();
        return mvcConversionService.convert(user, UserDTO.class);
    }
}