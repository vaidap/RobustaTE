package robustaTE;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RobustaMainTest {
	
	@Test
	void RobustaInit() {
		RobustaMain instance = new RobustaMain();
		assertNotNull(instance);
		assertEquals(instance.getAppName(),"RobustaTE Editor - ");
		assertEquals(instance.getFrame().getTitle(), "RobustaTE Editor - untitled file");
	}

}
