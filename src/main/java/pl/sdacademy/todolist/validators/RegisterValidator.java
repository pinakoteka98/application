package pl.sdacademy.todolist.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.sdacademy.todolist.dto.UserDto;
import pl.sdacademy.todolist.utils.AppUtils;
import pl.sdacademy.todolist.utils.Consts;

@Service
public class RegisterValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDto userDto = (UserDto) o;

//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "error.phoneNumber.empty");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.email.empty");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password.empty");

        if (StringUtils.isNotBlank(userDto.getPhoneNumber())) {
            boolean matchPhoneNumber = AppUtils.validateEmailOrPassword(Consts.CELLPHONE_NUMBER, userDto.getPhoneNumber());
            if (!matchPhoneNumber) {
                errors.rejectValue("phoneNumber", "error.userPhoneIsNotMatch");
            }
        } else {
            errors.rejectValue("phoneNumber", "error.phoneNumber.empty");
        }

        if (StringUtils.isNotBlank(userDto.getEmail())) {
            boolean matchEmail = AppUtils.validateEmailOrPassword(Consts.EMAIL_PATTERN, userDto.getEmail());
            if (!matchEmail) {
                errors.rejectValue("email", "error.userEmailIsNotMatch");
            }
        } else {
            errors.rejectValue("email", "error.email.empty");
        }

        if (StringUtils.isNotBlank(userDto.getPassword())) {
            boolean matchPassword = AppUtils.validateEmailOrPassword(Consts.PASSWORD_PATTERN, userDto.getPassword());
            if (!matchPassword) {
                errors.rejectValue("password", "error.userPasswordIsNotMatch");
            }
        } else {
            errors.rejectValue("password", "error.password.empty");
        }


    }
}
