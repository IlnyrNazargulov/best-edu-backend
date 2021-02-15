package ru.ilnyrdiplom.bestedu.dal.model.users;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ilnyrdiplom.bestedu.facade.model.enums.AccountType;
import ru.ilnyrdiplom.bestedu.facade.model.enums.Role;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue(AccountType.TEACHER_VALUE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountTeacher extends Account {
    @Override
    public Role getRole() {
        return Role.ROLE_TEACHER;
    }
}
