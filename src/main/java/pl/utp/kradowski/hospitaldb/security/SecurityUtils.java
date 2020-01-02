package pl.utp.kradowski.hospitaldb.security;

import com.vaadin.flow.server.ServletHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

public class SecurityUtils {



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
