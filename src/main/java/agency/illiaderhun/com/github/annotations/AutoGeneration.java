package agency.illiaderhun.com.github.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AutoGeneration annotation allows a date value
 * to be generated automatically when a new record is inserted into a table.
 *
 * @author Illia Derhun
 * @version 1.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoGeneration {

    TypeOfGeneration value();

}

