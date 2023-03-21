package hello.plusapi.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.plusapi.Entity.EmailAuth;
import hello.plusapi.Entity.QEmailAuth;
import java.sql.Timestamp;
import java.util.Optional;
import javax.persistence.EntityManager;

public class EmailAuthCustomRepositoryImpl implements EmailAuthRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public EmailAuthCustomRepositoryImpl(EntityManager em) {
        jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<EmailAuth> findValidAuthByEmail(String authToken, Timestamp currentTime) {
        EmailAuth emailAuth = jpaQueryFactory.selectFrom(QEmailAuth.emailAuth)
            .where(QEmailAuth.emailAuth.authToken.eq(authToken),
                QEmailAuth.emailAuth.expireDate.goe(currentTime),
                QEmailAuth.emailAuth.expired.eq(false))
            .fetchFirst();
        return Optional.ofNullable(emailAuth);
    }
}
