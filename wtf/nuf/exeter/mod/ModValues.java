package wtf.nuf.exeter.mod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModValues {
    String label();
    String[] aliases();
    String description() default "Default mod description";
    int color() default 0xFFFFFFFF;
    boolean visible() default true;
    boolean enabled() default false;
    ModType modType();
}
