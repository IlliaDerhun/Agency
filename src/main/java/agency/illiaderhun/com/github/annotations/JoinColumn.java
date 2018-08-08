package agency.illiaderhun.com.github.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * The JoinColumn annotation
 * shows which columns join in two {@link Table}s
 *
 * @author Illia Derhun
 * @version 1.0
 * */

@Retention(RetentionPolicy.SOURCE)
@Target({FIELD, LOCAL_VARIABLE, PARAMETER})
public @interface JoinColumn {

    /**
     * Name of column for joining
     * */
    String name();
}