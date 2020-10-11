import React, {useState, useEffect} from "react";
import FormInput from "./form_components/FormInput.js";
import {Button} from "./form_components/Button.js";
import {register} from "../../modules/login_registration/authenticationService.js"
import {Redirect} from "react-router-dom";
import validateRegistration from "./validators/validateRegistration.js";
import useFormValidation from "./validators/useFormValidation.js";

const INITIAL_STATE = {
    username: "",
    password: ""
};

export const RegistrationForm = () => {
    const [registered, setRegistered] = useState(false);

    const {
        username,
        password,
        matchingPassword,
        firstName,
        lastName,
        email,
        role
    } = values;

    const {
        handleSubmit,
        handleChange,
        handleBlur,
        values,
        errors,
        isSubmitting,
    } = useFormValidation(INITIAL_STATE, validateRegistration, register(values));

    if (registered) {
        return <Redirect to="/login"/>;
    }

    return (
        <form
            onSubmit={handleSubmit}
            className="form-signin"
        >
            {errors.username && (
                <p className="error-text" class="alert alert-danger">
                    {errors.username}
                </p>
            )};

            <FormInput
                label="Email"
                name="email"
                type="text"
                className={errors.email && "error-input"}
                className="form-control"
                value={values.email}
                onChange={handleChange}
                placeholder="Email address"
            />

            <br/>

            {errors.password && (
                <p className="error-text" class="alert alert-danger">
                    {errors.password}
                </p>
            )};

            <FormInput
                type="password"
                onChange={handleChange}
                onBlur={handleBlur}
                name="password"
                className={errors.password && "error-input"}
                className="form-control"
                value={values.password}
                placeholder="Password"
            />

            <br/>

            <FormInput
                type="password"
                onChange={handleChange}
                onBlur={handleBlur}
                name="matchingPassword"
                className={errors.matchingPassword && "error-input"}
                className="form-control"
                value={values.matchingPassword}
                placeholder="Matching password"
            />

            <br/>

            <FormInput
                label="Username"
                name="username"
                type="text"
                className={errors.username && "error-input"}
                className="form-control"
                value={values.username}
                onChange={handleChange}
                placeholder="Username"
            />

            <br/>

            <FormInput
                label="FirstName"
                name="firstName"
                type="text"
                className={errors.firstName && "error-input"}
                className="form-control"
                value={values.firstName}
                onChange={handleChange}
                placeholder="First Name"
            />

            <br/>

            <FormInput
                label="LastName"
                name="firstName"
                type="text"
                className={errors.firstName && "error-input"}
                className="form-control"
                value={values.firstName}
                onChange={handleChange}
                placeholder="First Name"
            />

            <br/>

            <select>
                <option value="Parent">Parent</option>
                <option value="Child">Child</option>
                <option value="Guest">Guest</option>
            </select>

            <Button
                type="submit"
                label="Submit"
                className="button"
                disabled={isSubmitting}
            />


        </form>
    );
}

export default RegistrationForm;