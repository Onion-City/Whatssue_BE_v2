package GDG.whatssue.global.interceptor;

import static GDG.whatssue.global.error.CommonErrorCode.*;

import GDG.whatssue.domain.schedule.exception.ScheduleErrorCode;
import GDG.whatssue.domain.schedule.service.ScheduleService;
import GDG.whatssue.global.error.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

@Component
public class ScheduleCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private ScheduleService scheduleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        Long clubId = getClubId(request);
        Long scheduleId = getScheduleId(request);

        //스케줄 존재 및 클럽 소유여부 체크
        if (!scheduleService.isClubSchedule(clubId, scheduleId)) {
            throw new CommonException(ScheduleErrorCode.EX4100);
        }

        //인터셉터 통과
        return true;
    }

    private Long getClubId(HttpServletRequest request) {
        String clubId = extractPathVariableFromRequest(request, "clubId");

        return Long.parseLong(clubId);
    }

    private Long getScheduleId(HttpServletRequest request) {
        return Long.parseLong(extractPathVariableFromRequest(request, "scheduleId"));
    }

    private String extractPathVariableFromRequest(HttpServletRequest request, String pathVariable) {
        Map<String, String> pathVariables = (Map<String, String>) request
            .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        return pathVariables.get(pathVariable);
    }
}
