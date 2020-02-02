package pl.utp.kradowski.hospitaldb.security;

import com.vaadin.flow.server.ServletHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.utp.kradowski.hospitaldb.view.LoginView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class SecurityUtils {

    public static  boolean isAccessGranted(Class<?> classToAccess){
        if(LoginView.class.equals(classToAccess))
            return true;
        if(!isUserLoggedIn())
            return false;
        Secured secured = AnnotationUtils.findAnnotation(classToAccess, Secured.class);
        if(secured==null) {
            return true;
        } else {
            List<String> allowedUserGroups = Arrays.asList(secured.value());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .anyMatch(allowedUserGroups::contains);
        }
    }

    static boolean isUserLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth !=null
                && !(auth instanceof AnonymousAuthenticationToken)
                && auth.isAuthenticated();
    }

    public static boolean isFrameworkInternalRequest(HttpServletRequest request) {
        final String paramValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return paramValue !=null
                && Stream.of(ServletHelper.RequestType.values()).anyMatch(r -> r.getIdentifier().equals(paramValue));
    }
}
