package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	//Test if add/removeTimedPayment is ok.
	//Failed, NullPointerException
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("1", 3, 1,new Money(10000, SEK),
				SweBank, "Alice");

		assertTrue("true", testAccount.timedPaymentExists("1"));
		assertFalse("False", testAccount.timedPaymentExists("3"));

		testAccount.removeTimedPayment("1");
		assertFalse("False", testAccount.timedPaymentExists("1"));
	}

	//Test if timedPayment is ok.
	//Failed, NullPointerException
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("1", 1, 1,new Money(10000, SEK),
				SweBank, "Alice");

		testAccount.tick();
		testAccount.tick();

		assertEquals(9990000, testAccount.getBalance().getAmount(), 0);
	}

	//Test if addWithdraw is ok.
	//Failed, NullPointerException
	@Test
	public void testAddWithdraw() {
		testAccount.withdraw(new Money(10000, SEK));
		assertEquals(9990000, testAccount.getBalance().getAmount(), 0);
	}

	//Test if getBalance is ok.
	//Failed, NullPointerException
	@Test
	public void testGetBalance() {
		assertEquals(10000000, testAccount.getBalance().getAmount(), 0);
	}
}
