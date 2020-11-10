package com.soen343.shs.dal.service.validators;

import com.soen343.shs.dal.service.exceptions.IllegalStateException;
import com.soen343.shs.dal.service.exceptions.state.SHSSameStateException;

public class StateValidator {

    /**
     * @param desiredState desired state of object
     * @param currentState current state of object
     * @param id           id of object
     * @param classType    class type of object
     * @param <Entity>     generic class
     */
    public static <Entity> void validateState(final boolean desiredState, final boolean currentState, final long id, final Class<Entity> classType) {
        if (desiredState && currentState || (!desiredState && !currentState)) {
            throw new SHSSameStateException(getSameStateExceptionErrorMessage(classType, id));
        }
    }

    /**
     * @param classType    class of object
     * @param id           id of object
     * @param stateToCheck state to check
     * @param <Entity>     generic class
     */
    public static <Entity> void checkForIllegalStateException(final Class<Entity> classType, final long id, final boolean stateToCheck) {
        final String ERROR_MSG = String.format("Error: object: %s with id: %d cannot be opened since it is blocked.", classType.getName(), id);
        if (stateToCheck) {
            throw new IllegalStateException(String.format(ERROR_MSG, id));
        }
    }

    /**
     * @param id        id of the object
     * @param classType class type of object
     * @param <Entity>  class of object
     * @return new string formatted to return our error message
     */
    static <Entity> String getSameStateExceptionErrorMessage(final Class<Entity> classType, final long id) {
        return String.format("Error: object: %s with id: %d is already in the expected state", classType.getName(), id);
    }


}
