package service;

import model.Bank;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BankRepositoryTest {

    private BankRepository bankRepository;

    private static final String BANK1_NAME = "bank1";
    private static final String BANK2_NAME = "bank2";
    private static final String BANK1_RANGE = "30, 70";
    private static final String BANK2_RANGE = "70, 72";

    @Before
    public void setUp() throws IOException {
        Properties properties = new Properties();
        InputStream is = ClassLoader.getSystemResourceAsStream("test.properties");

        properties.load(is);
        bankRepository = new BankRepository();
        bankRepository.setMyProperties(properties);
        bankRepository.loadBankSet();
    }

    @Test
    public void verifyLoadedRepository() {
        assertEquals(2, bankRepository.getBankSet().size());
        assertTrue(bankRepository.getBankSet().contains(buildBank(BANK1_NAME, BANK1_RANGE)));
        assertTrue(bankRepository.getBankSet().contains(buildBank(BANK2_NAME, BANK2_RANGE)));
    }

    @Test
    public void test() {
        Set<Bank> set = new HashSet<>();
        Bank bank = buildBank(BANK1_NAME, BANK1_RANGE);
        set.add(bank);
        assertTrue(set.contains(bank));
    }
    private Bank buildBank(String name, String range) {
        return new Bank(name, range);
    }

}