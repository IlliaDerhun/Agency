package agency.illiaderhun.com.github.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * The JoinTable annotation
 * shows which {@link Table}s are joining
 *
 * @author Illia Derhun
 * @version 1.0
 * */

@Retention(RetentionPolicy.SOURCE)
@Target({FIELD, LOCAL_VARIABLE, PARAMETER})
public @interface JoinTable {

    /**
     * Name of table for joining
     * */
    String name();
    JoinColumn joinsColums();
    JoinColumn inverseJoinColumn();
}