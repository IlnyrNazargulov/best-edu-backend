package ru.ilnyrdiplom.bestedu.dal.model.users;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ilnyrdiplom.bestedu.facade.model.AccountTeacherFacade;
import ru.ilnyrdiplom.bestedu.facade.model.enums.AccountType;
import ru.ilnyrdiplom.bestedu.facade.model.enums.Role;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.Instant;

@Getter
@Entity
@DiscriminatorValue(AccountType.TEACHER_VALUE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountTeacher extends Account implements AccountTeacherFacade {
    @Override
    public Role getRole() {
        return Role.ROLE_TEACHER;
    }

    public AccountTeacher(Instant createdAt, String login, String passwordHash, String secondName, String firstName, String patronymic) {
        super(createdAt, login, passwordHash, secondName, firstName, patronymic);
    }
}
