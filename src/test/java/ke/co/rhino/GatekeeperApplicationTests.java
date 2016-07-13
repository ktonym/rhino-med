package ke.co.rhino;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RhinoMedApplication.class)
@WebAppConfiguration
public class GatekeeperApplicationTests {

	String famSize = "M+2";
	String famSizeZero = "M";

	@Test
	public void contextLoads() {
	}

	@Test
	public void testFamSize(){
		assert famSize.matches("M+(\\+\\d+)?");
	}

	@Test
	public void testFamSizeZero(){
		assert famSizeZero.matches("M+(\\+\\d+)?");
	}

}
