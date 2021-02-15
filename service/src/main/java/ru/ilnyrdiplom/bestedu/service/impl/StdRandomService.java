package ru.ilnyrdiplom.bestedu.service.impl;

import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.stereotype.Component;
import ru.ilnyrdiplom.bestedu.service.service.RandomService;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.UUID;

@Component
public class StdRandomService implements RandomService {

    private SecureRandom secureRandom = new SecureRandom();

    @Override
    public UUID generateUUID() {
        return UUID.randomUUID();
    }

    @Override
    public String generatePassword(int length) {
        BigInteger integer = new BigInteger(5 * length, secureRandom);
        // бывает случай, когда первые биты образуют целочисленный 0, и в таком случае он теряется
        String password = integer.toString(32);
        // поэтому дополняем нулями
        String paddedPassword = leftPadWithZero(password, length);
        return paddedPassword;
    }

    @Override
    public String generateCode(int length) {
        BigInteger integer = new BigInteger(4 * length, secureRandom);
        //noinspection ConstantConditions
        String code = integer.toString(10);
        // int might be 0, or too big, so padding before and cut
        String paddedCode = leftPadWithZero(code, length);
        return paddedCode;
    }

    private String leftPadWithZero(String source, int requiredLength) {
        String padded = StringUtils.leftPad(source, requiredLength, '0').substring(0, requiredLength);
        return padded;
    }
}
