package com.wuxp.security.authenticate.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 会话信息失效策略
 */
@Slf4j
public class WuxpSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {


    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {

    }
}
