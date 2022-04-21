package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import Models.sqlTranscoder;

public class TranscoderTest {
	ArrayList<Integer> decoded1 = new ArrayList<Integer>();
	ArrayList<Integer> decoded2 = new ArrayList<Integer>();
	ArrayList<Integer> decoded3 = new ArrayList<Integer>();
	
	@Before
	public void setUp() {
		decoded1.add(1);
		decoded1.add(9);
		decoded1.add(6254);
		decoded1.add(463);
		decoded1.add(1);
		decoded1.add(9);
		decoded1.add(47);

		decoded2.add(97);
		decoded2.add(232);
		decoded2.add(32);
		decoded2.add(95);
		decoded2.add(15);
		decoded2.add(21);
		decoded2.add(21);
	}
	
	@Test
	public void TestTranscode1() {
		String encoded1 = sqlTranscoder.encode(decoded1);
		ArrayList<Integer> decoded1again = sqlTranscoder.decode(encoded1);
		assertEquals(decoded1again,decoded1);
	}
	
	@Test
	public void TestTranscode2() {
		String encoded2 = sqlTranscoder.encode(decoded2);
		ArrayList<Integer> decoded2again = sqlTranscoder.decode(encoded2);
		assertEquals(decoded2again,decoded2);
	}
	
	@Test
	public void TestTranscode3() {
		String encoded3 = sqlTranscoder.encode(decoded3);
		ArrayList<Integer> decoded3again = sqlTranscoder.decode(encoded3);
		assertEquals(decoded3again,decoded3);
	}
	
}