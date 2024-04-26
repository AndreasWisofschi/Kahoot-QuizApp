package wahoot.client.ui.fxml;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;




@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface FXMLMethod {
    String node() default "";
    FXMLEvent event() default FXMLEvent.NONE;



}
