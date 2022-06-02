package asia.vatek.project.validator.anotation;

import asia.vatek.project.constant.DateConstant;
import asia.vatek.project.validator.DateFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateFormatValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormatConstraint {

    String message() default "Invalid date";

    String datePattern() default DateConstant.DATE_PATTERN_DD_MM_YYYY;

    boolean required() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
