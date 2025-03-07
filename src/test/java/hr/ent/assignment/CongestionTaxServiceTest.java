package hr.ent.assignment;

import hr.ent.assignment.service.CongestionTaxService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CongestionTaxServiceTest {

	@Autowired
	private CongestionTaxService taxService;

	@Test
	void testExemptVehicle() {
		List<LocalDateTime> passages = List.of(
				LocalDateTime.of(2013, 2, 8, 6, 0),
				LocalDateTime.of(2013, 2, 8, 7, 0),
				LocalDateTime.of(2013, 2, 8, 8, 0)
		);
		assertEquals(0, taxService.getTax("Bus", passages));
	}

	@Test
	void testSinglePassage() {
		List<LocalDateTime> passages = List.of(
				LocalDateTime.of(2013, 2, 8, 6, 50) // 13
		);
		assertEquals(13, taxService.getTax("Car", passages));
	}

	@Test
	void testMultiplePassagesSameDay() {
		List<LocalDateTime> passages = List.of(
				LocalDateTime.of(2013, 2, 8, 6, 10), // 8
				LocalDateTime.of(2013, 2, 8, 7, 15) // 18
		);
		assertEquals(26, taxService.getTax("Car", passages));
	}

	@Test
	void testMultiplePassagesDifferentDays() {
		List<LocalDateTime> passages = List.of(
				LocalDateTime.of(2013, 2, 7, 6, 10), // 8
				LocalDateTime.of(2013, 2, 8, 7, 10) // 18
		);
		assertEquals(26, taxService.getTax("Car", passages));
	}

	@Test
	void testMultiplePassagesDifferentDaysWithWeekend() {
		List<LocalDateTime> passages = List.of(
				LocalDateTime.of(2013, 2, 8, 6, 10), // 8
				LocalDateTime.of(2013, 2, 9, 7, 10) // 0
		);
		assertEquals(8, taxService.getTax("Car", passages));
	}

	@Test
	void testSingleChargeRule() {
		List<LocalDateTime> passages = List.of(
				LocalDateTime.of(2013, 2, 8, 6, 10), // 8
				LocalDateTime.of(2013, 2, 8, 6, 20), // 8
				LocalDateTime.of(2013, 2, 8, 6, 30), // 13
				LocalDateTime.of(2013, 2, 8, 7, 5) // 18
		);
		assertEquals(18, taxService.getTax("Car", passages));
	}

	@Test
	void testMaxAmountPerDay() {
		List<LocalDateTime> passages = List.of(
				LocalDateTime.of(2013, 2, 8, 6, 10), // 8
				LocalDateTime.of(2013, 2, 8, 7, 10), // 18
				LocalDateTime.of(2013, 2, 8, 8, 10), // 13
				LocalDateTime.of(2013, 2, 8, 9, 10), // 8
				LocalDateTime.of(2013, 2, 8, 15, 10),// 13
				LocalDateTime.of(2013, 2, 8, 16, 10),// 18
				LocalDateTime.of(2013, 2, 8, 17, 10),// 13
				LocalDateTime.of(2013, 2, 8, 18, 10),// 8
				LocalDateTime.of(2013, 2, 8, 18, 40) // 0
		);
		assertEquals(60, taxService.getTax("Car", passages));
	}

	@Test
	void testJuly() {
		List<LocalDateTime> passages = List.of(
				LocalDateTime.of(2013, 7, 8, 6, 27), // 8
				LocalDateTime.of(2013, 7, 8, 6, 20) // 8
		);
		assertEquals(0, taxService.getTax("Car", passages));
	}

	@Test
	void testWeekend() {
		List<LocalDateTime> passages = List.of(
				LocalDateTime.of(2013, 2, 9, 6, 27), // Saturday
				LocalDateTime.of(2013, 2, 10, 6, 20) // Sunday
		);
		assertEquals(0, taxService.getTax("Car", passages));
	}

	@Test
	void testEmptyPassages() {
		List<LocalDateTime> passages = List.of();
		assertEquals(0, taxService.getTax("Car", passages));
	}

}
