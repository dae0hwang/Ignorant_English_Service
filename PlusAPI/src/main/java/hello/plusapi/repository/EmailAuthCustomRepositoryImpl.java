package hello.plusapi.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.plusapi.entity.EmailAuth;
import hello.plusapi.entity.QEmailAuth;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailAuthCustomRepositoryImpl implements EmailAuthCustomRepository{

    JPAQueryFactory jpaQueryFactory;

    public EmailAuthCustomRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    public Optional<EmailAuth> findValidAuthByEmail(String authToken) {
        EmailAuth emailAuth = jpaQueryFactory
            .selectFrom(QEmailAuth.emailAuth)
            .where(QEmailAuth.emailAuth.authToken.eq(authToken),
                QEmailAuth.emailAuth.expireDate.goe(Timestamp.valueOf(LocalDateTime.now())),
                QEmailAuth.emailAuth.expired.eq(false))
            .fetchFirst();

        return Optional.ofNullable(emailAuth);
    }

    @Override
    public Optional<EmailAuth> findExpiredAuth(String authToken) {
        EmailAuth emailAuth = jpaQueryFactory
            .selectFrom(QEmailAuth.emailAuth)
            .where(QEmailAuth.emailAuth.authToken.eq(authToken),
                QEmailAuth.emailAuth.expireDate.loe(Timestamp.valueOf(LocalDateTime.now())),
                QEmailAuth.emailAuth.expired.eq(false))
            .fetchFirst();
        return Optional.ofNullable(emailAuth);
    }
}
