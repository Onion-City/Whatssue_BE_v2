package GDG.whatssue.domain.schedule.repository;

import static GDG.whatssue.domain.schedule.entity.QSchedule.*;

import GDG.whatssue.domain.schedule.dto.SchedulesResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Repository
@Transactional
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SchedulesResponse> findAllSchedule(Long clubId, String searchQuery, String sDate, String eDate, Pageable pageable) {

        JPAQuery<SchedulesResponse> query = queryFactory
            .select(Projections.constructor(
                SchedulesResponse.class,
                schedule.id,
                schedule.scheduleName,
                schedule.scheduleDate,
                schedule.scheduleTime))
            .from(schedule)
            .where(
                filterClub(clubId),
                filterQuery(searchQuery),
                filterDate(sDate, eDate))
            .orderBy(schedule.scheduleDate.asc(), schedule.scheduleTime.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize());

//        for (Sort.Order o : pageable.getSort()) {
//            PathBuilder pathBuilder = new PathBuilder(schedule.getType(), schedule.getMetadata());
//            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
//                pathBuilder.get(o.getProperty())));
//        }

        List<SchedulesResponse> results = query.fetch();

        long total = query.select(schedule)
            .from(schedule)
            .where(
                filterClub(clubId),
                filterQuery(searchQuery),
                filterDate(sDate, eDate))
            .stream()
            .count();

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression filterClub(Long clubId) {
        return schedule.club.id.eq(clubId);
    }

    private BooleanExpression filterQuery(String searchQuery) {
        if (StringUtils.hasText(searchQuery)) {
            return schedule.scheduleName.like("%" + searchQuery + "%");
        }

        return null;
    }

    private BooleanExpression filterDate(String sDate, String eDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(sDate, formatter);
        LocalDate endDate = LocalDate.parse(eDate, formatter);

        return schedule.scheduleDate.between(startDate, endDate);
    }
}