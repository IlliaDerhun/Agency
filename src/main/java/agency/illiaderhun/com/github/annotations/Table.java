package agency.illiaderhun.com.github.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Table annotation is used to mark source code
 * that has been uploaded/downloaded into/from table in Data Base.
 * In other words, table name in DB.
 *
 * @author Illia Derhun
 * @version 1.0
 * */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
    String name();
}
