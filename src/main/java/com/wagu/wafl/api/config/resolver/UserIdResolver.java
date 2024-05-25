package com.wagu.wafl.api.config.resolver;

import com.wagu.wafl.api.common.exception.AuthException;
import com.wagu.wafl.api.common.message.ExceptionMessage;
import com.wagu.wafl.api.config.jwt.JwtTokenManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class UserIdResolver implements HandlerMethodArgumentResolver {
    private final JwtTokenManager jwtTokenManager;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class) && Long.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer mavContainer, @NotNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final String token = request.getHeader("Authorization"); //이 부분도 위에 상수처리를 통해 매직넘버 지양하는게 맞는듯
                                                                        // 예시 ) private final AUTH_HEADER="Authoriziation"

        jwtTokenManager.verifyToken(token);

        final String tokenContents = jwtTokenManager.getJwtContents(token);
        try{
            return Long.parseLong(tokenContents);
        } catch (NumberFormatException e) { //에러 메시지 ExceptionMessage에 남기는게 나을 것 같음.
            throw new AuthException(ExceptionMessage.NOT_PARSED_TO_ID.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
