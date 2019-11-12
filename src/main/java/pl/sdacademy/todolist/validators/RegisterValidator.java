package pl.sdacademy.todolist.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.sdacademy.todolist.dto.UserDto;
import pl.sdacademy.todolist.entity.User;
import pl.sdacademy.todolist.utils.AppUtils;
import pl.sdacademy.todolist.utils.Consts;

import java.util.Optional;

@Service
public class RegisterValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDto userDto = (UserDto) o;

        if (StringUtils.isNotBlank(userDto.getPhoneNumber())) {
            boolean matchPhoneNumber = AppUtils.validateData(Consts.CELLPHONE_NUMBER, userDto.getPhoneNumber());
            if (!matchPhoneNumber) {
                errors.rejectValue("phoneNumber", "error.userPhoneIsNotMatch");
            }
        } else {
            errors.rejectValue("phoneNumber", "error.phoneNumber.empty");
        }

        if (StringUtils.isNotBlank(userDto.getEmail())) {
            boolean matchEmail = AppUtils.validateData(Consts.EMAIL_PATTERN, userDto.getEmail());
            if (!matchEmail) {
                errors.rejectValue("email", "error.userEmailIsNotMatch");
            }
        } else {
            errors.rejectValue("email", "error.email.empty");
        }

        if (StringUtils.isNotBlank(userDto.getPassword())) {
            boolean matchPassword = AppUtils.validateData(Consts.PASSWORD_PATTERN, userDto.getPassword());
            if (!matchPassword) {
                errors.rejectValue("password", "error.userPasswordIsNotMatch");
                errors.rejectValue("password", "error.userPasswordIsNotMatch2");
            }
        } else {
            errors.rejectValue("password", "error.password.empty");
        }

    }
    public void validatePhoneExist(Optional<User> user, Errors errors) {
        if (user.isPresent()) {
            errors.rejectValue("phoneNumber", "error.userPhoneExist");
        }
    }
}
