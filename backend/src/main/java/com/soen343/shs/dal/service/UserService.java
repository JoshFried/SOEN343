package com.soen343.shs.dal.service;

import com.soen343.shs.dal.model.HouseMember;
import com.soen343.shs.dal.model.RealUser;
import com.soen343.shs.dal.model.User;
import com.soen343.shs.dal.repository.UserRepository;
import com.soen343.shs.dal.repository.mapping.RealUserMapper;
import com.soen343.shs.dal.service.exceptions.state.SHSNotFoundException;
import com.soen343.shs.dto.RealUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ConversionService mvcConversionService;
    private final UserRepository userRepository;
    private final RealUserMapper mapper;

    /**
     * @param user user object to save to our db
     * @return userDTO representing the object saved to db
     */
    public RealUserDTO createUser(final RealUser user) {
        return mvcConversionService.convert(userRepository.save(user), RealUserDTO.class); // return the dto object to our user
    }

    /**
     * @param dto data transfer object used to map the desired state of user to the record in the db
     * @return dto representing the change of state to our user
     */
    public RealUserDTO updateUser(final RealUserDTO dto) {
        return mvcConversionService
                .convert(userRepository
                                // Convert after mapping is taken care of
                                .save(mapper
                                        .mapRealUserDTOToRealUser(dto, fetchById(dto.getId()))),
                        RealUserDTO.class);
    }

    /**
     * @param id    id of user to fetch from db
     * @param dto   instance of data transfer object to convert to
     * @param <DTO> class of dto to convert to
     * @return DTO object who has the given id
     */
    public <DTO> DTO getById(final long id, final Class<DTO> dto) {
        return mvcConversionService.convert(fetchById(id), dto);
    }

    /**
     * @param username of the user we wish to make leave the house
     * @return updated user who is now not located inside of the house
     */
    public RealUserDTO leaveHouse(final String username) {
        final RealUserDTO dto = getUserByUsername(username, RealUserDTO.class);
        dto.setLocation(null);
        dto.setOutside(true);
        return updateUser(dto);
    }

    /**
     * @param username String value used to fetch from repository by username
     * @return UserDTO corresponding to the unique given username, or throw UsernameNotFoundException
     */
    public <DTO> DTO getUserByUsername(final String username, final Class<DTO> dtoClass) {
        return mvcConversionService.convert(
                userRepository.findByUsername(User.class, username)
                        .orElseThrow(() -> new SHSNotFoundException(getNotFoundExceptionMessage("username", username)))
                , dtoClass);
    }

    /**
     * @param email to check if it exists
     * @return boolean value representing if theres a user in our db who this email already belongs to
     */
    public boolean emailExists(final String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * @param username to check if it exists
     * @return boolean value representing if theres a user in our db who this name already belongs to
     */
    public boolean usernameExists(final String username) {
        return userRepository.findByUsername(RealUser.class, username).isPresent()
                || userRepository.findByUsername(HouseMember.class, username).isPresent();
    }

    private static String getNotFoundExceptionMessage(final String username, final String parameter) {
        return String.format("%s: %s doesn't exist", username, parameter);
    }

    private RealUser fetchById(final long id) {
        return userRepository.findById(RealUser.class, id)
                .orElseThrow(() -> new SHSNotFoundException(getNotFoundExceptionMessage("id", String.valueOf(id))));
    }
}