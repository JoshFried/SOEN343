package com.soen343.shs.dal.service;

import com.soen343.shs.dal.model.User;
import com.soen343.shs.dal.repository.UserRepository;
import com.soen343.shs.dal.service.Login.LoginRequest;
import com.soen343.shs.dal.service.Login.LoginResponse;
import com.soen343.shs.dal.service.exceptions.user.SHSUserAlreadyExistsException;
import com.soen343.shs.dto.RegistrationDTO;
import com.soen343.shs.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

import static com.soen343.shs.dal.model.UserRole.PARENT;
import static com.soen343.shs.dal.service.helpers.UserTestHelper.EMAIL;
import static com.soen343.shs.dal.service.helpers.UserTestHelper.FIRST_NAME;
import static com.soen343.shs.dal.service.helpers.UserTestHelper.LAST_NAME;
import static com.soen343.shs.dal.service.helpers.UserTestHelper.PASSWORD;
import static com.soen343.shs.dal.service.helpers.UserTestHelper.USERNAME;
import static com.soen343.shs.dal.service.helpers.UserTestHelper.createUser;
import static com.soen343.shs.dal.service.helpers.UserTestHelper.createUserDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private ConversionService mvcConversionService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationProvider authProvider;

    @InjectMocks
    private UserService classUnderTest;

    private static final RegistrationDTO REGISTRATION_DTO = buildRegistrationDTO();
    private static final String ENCODED_PASSWORD = "encodedString";
    private static final String REGISTRATION_ERR_MSG = "Username/email already exists!";

    @Test
    void testCreateUser_givenUserDoesntAlreadyExist() {
        when(mvcConversionService.convert(REGISTRATION_DTO, User.class)).thenReturn(createUser());
        when(passwordEncoder.encode(REGISTRATION_DTO.getPassword())).thenReturn(ENCODED_PASSWORD);
        when(mvcConversionService.convert(REGISTRATION_DTO, UserDTO.class)).thenReturn(UserDTO.builder().build());

        final UserDTO dto = classUnderTest.createUser(REGISTRATION_DTO);
        Assertions.assertNotNull(dto);
    }

    @Test
    void testCreateUser_givenUserAlreadyExists() {
        when(userRepository.findByUsername(REGISTRATION_DTO.getUsername())).thenReturn(Optional.of(createUser()));

        final SHSUserAlreadyExistsException exception = Assertions.assertThrows(SHSUserAlreadyExistsException.class, () -> {
                    classUnderTest.createUser(REGISTRATION_DTO);
                }
        );

        Assertions.assertEquals(REGISTRATION_ERR_MSG, exception.getMessage());
    }

    @Test
    void testLogin_givenValidCredentials() {
        final LoginRequest loginRequest = buildLoginRequest();
        final MockHttpServletRequest request = new MockHttpServletRequest();

        final User u = createUser();
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.ofNullable(u));
        when(mvcConversionService.convert(u, UserDTO.class)).thenReturn(createUserDTO());

        final HttpSession session = request.getSession(true);
        final LoginResponse response = classUnderTest.login(request, loginRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(createUserDTO(), response.getUser());
        Assertions.assertEquals(Objects.requireNonNull(session).getId(), response.getToken());
    }

    @Test
    void testLogin_givenInvalidCredentials() {
        final LoginRequest loginRequest = buildLoginRequest();
        final MockHttpServletRequest request = new MockHttpServletRequest();

        when(authProvider.authenticate(any())).thenThrow(BadCredentialsException.class);

        Assertions.assertThrows(BadCredentialsException.class, () -> {
                    classUnderTest.login(request, loginRequest);
                }
        );
    }

    private static RegistrationDTO buildRegistrationDTO() {
        return RegistrationDTO.builder()
                .username(USERNAME)
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .password(PASSWORD)
                .matchingPassword(PASSWORD)
                .role(PARENT.name())
                .build();
    }

    private static LoginRequest buildLoginRequest() {
        return LoginRequest.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }
}