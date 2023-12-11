package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;

	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	//Test if getName is ok.
	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	//Test if getCurrency is ok.
	@Test
	public void testGetCurrency() {
		assertSame(SEK, SweBank.getCurrency());
		assertSame(SEK, Nordea.getCurrency());
		assertSame(DKK, DanskeBank.getCurrency());
	}

	//Test if openCurrency is ok.
	//Failed, did not throw exception when account existed.
	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		assertThrows(AccountExistsException.class, () -> SweBank.openAccount("Bob"));
	}

	//Test if deposit is ok.
	//Failed, NullPointerException
	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		assertThrows(AccountDoesNotExistException.class, () ->
				SweBank.deposit("Jacek", new Money(1000, SEK)));

		SweBank.deposit("Bob", new Money(43200, SEK));
		assertEquals(43200, SweBank.getBalance("Bob"), 0);
	}

	//Test if withdraw is ok.
	//Failed, AccountDoesNotExistsException
	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		assertThrows(AccountDoesNotExistException.class, ()
				-> SweBank.withdraw("Jacek", new Money(1000, SEK)));

		SweBank.withdraw("Bob", new Money(1000, SEK));
		assertEquals(-1000, SweBank.getBalance("Bob"), 0);
	}

	//Test if getBalance is ok.
	//Failed, AccountDoesNotExistsException
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertThrows(AccountDoesNotExistException.class, ()
				-> SweBank.getBalance("Jacek"));

		assertEquals(0, SweBank.getBalance("Bob"), 0);
	}

	//Test if transfer is ok.
	//Failed, AccountDoesNotExistsException
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		assertThrows(AccountDoesNotExistException.class, ()
				-> SweBank.transfer("Bob", SweBank,"Jacek", new Money(1000, SEK)));
		assertThrows(AccountDoesNotExistException.class, ()
				-> SweBank.transfer("Bob", "Jacek", new Money(1000, SEK)));

		SweBank.transfer("Bob", "Ulrika", new Money(1000, SEK));
		assertEquals(1000, SweBank.getBalance("Ulrika"), 0);
		SweBank.transfer("Bob", Nordea,"Bob", new Money(1000, SEK));
		assertEquals(1000, Nordea.getBalance("Bob"), 0);
	}

	//Test is timedPayment is ok.
	//Failed, NullPointerException
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.addTimedPayment("Ulrika", "2", 1, 1, new Money(10000, SEK),
				Nordea, "Bob");
		SweBank.tick();
		SweBank.tick();
		assertEquals(10000, Nordea.getBalance("Bob"), 0);
		SweBank.removeTimedPayment("Ulrika", "2");
		SweBank.tick();
		assertEquals(10000, Nordea.getBalance("Bob"), 0);
	}
}
