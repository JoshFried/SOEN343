package com.soen343.shs.dal.service.validators.helper;

public class ErrorMessageGenerator {
    /**
     * @param id        id of the object
     * @param classType class type of object
     * @param <Entity>  class of object
     * @return new string formatted to return our error message
     */
    public static <Entity> String getSHSNotFoundErrorMessage(final long id, final Class<Entity> classType) {
        return String.format("Error: %s with ID: %d does not exist. Please enter a valid %s id.", classType.getName(), id, classType.getName());
    }

}
