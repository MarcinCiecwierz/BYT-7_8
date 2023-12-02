package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(10000, SEK100.getAmount(), 0);
		assertEquals(1000, EUR10.getAmount(), 0);
		assertEquals(-10000, SEKn100.getAmount(), 0);
	}

	@Test
	public void testGetCurrency() {
		assertEquals(0.15, SEK100.getCurrency().getRate(), 0);
		assertEquals(1.5, EUR0.getCurrency().getRate(), 0);
		assertEquals(0.15, SEKn100.getCurrency().getRate(), 0);
	}

	@Test
	public void testToString() {
		assertEquals("20.0 EUR", EUR20.toString());
		assertEquals("0.0 EUR", EUR0.toString());
		assertEquals("-100.0 SEK", SEKn100.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals(1500, SEK100.universalValue(), 0);
		assertEquals(3000, EUR20.universalValue(), 0);
		assertEquals(-1500, SEKn100.universalValue(), 0);
	}

	@Test
	public void testEqualsMoney() {
		assertTrue("true", EUR0.equals(SEK0));
		assertFalse("false", EUR10.equals(SEKn100));
	}

	@Test
	public void testAdd() {
		assertEquals(20000, SEK100.add(EUR10).getAmount(), 0);
		assertEquals(3000, EUR20.add(EUR10).getAmount(), 0);
		assertEquals(10000, SEKn100.add(EUR20).getAmount(), 0);
	}

	@Test
	public void testSub() {
		assertEquals(0, SEK100.sub(EUR10).getAmount(), 0);
		assertEquals(1000, EUR20.sub(EUR10).getAmount(), 0);
		assertEquals(-30000, SEKn100.sub(EUR20).getAmount(), 0);
	}

	@Test
	public void testIsZero() {
		assertTrue("True", EUR0.isZero());
		assertFalse("False", EUR10.isZero());
	}

	@Test
	public void testNegate() {
		assertEquals(-1000, EUR10.negate().getAmount(), 0);
		assertEquals(-10000, SEK100.negate().getAmount(), 0);
	}

	@Test
	public void testCompareTo() {
		assertEquals(0, EUR0.compareTo(SEK0));
		assertEquals(-1, SEK100.compareTo(EUR20));
		assertEquals(1, EUR20.compareTo(SEK100));
	}
}
