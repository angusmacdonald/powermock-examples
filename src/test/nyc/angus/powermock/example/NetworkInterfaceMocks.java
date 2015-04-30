package nyc.angus.powermock.example;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.net.NetworkInterface;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Tests highlighting the difficulty of mocking java standard library classes and final classes.
 * <p>
 * {@link NetworkInterface} is a final class in the java standard library. It must be prepared with the
 * <code>@PrepareForTest</code> annotation to be mocked.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ NetworkInterface.class })
public class NetworkInterfaceMocks {

	/**
	 * Mocking {@link NetworkInterface} requires it to be prepared for test (above the class definition) and mocked with
	 * PowerMockito rather than vanilla Mockito.
	 * <p>
	 * This test shows that the mock is successful if called within the test class.
	 */
	@Test
	public void sameClassSuccess() throws Exception {
		final NetworkInterface mockInterface = PowerMockito.mock(NetworkInterface.class);

		when(mockInterface.isUp()).thenReturn(true);
		assertTrue(mockInterface.isUp());
	}

	/**
	 * This test fails!
	 * <p>
	 * It is the same as the previous one, but rather than call the {@link NetworkInterface#isUp()} mocked method from
	 * within the test class, it is called from within another class -- named {@link OtherClass}!.
	 * <p>
	 * This fails because PowerMock cannot mock java system classes, so instead of preparing the
	 * {@link NetworkInterface} class for test, you have to prepare the class calling {@link NetworkInterface}.
	 * <p>
	 * This is explained <a href="https://code.google.com/p/powermock/wiki/MockSystem">here</a>.
	 */
	@Test
	public void differentClassFailure() throws Exception {
		final NetworkInterface mockInterface = PowerMockito.mock(NetworkInterface.class);

		when(mockInterface.isUp()).thenReturn(true);
		assertTrue(new OtherClass().isUp(mockInterface));
	}

	/**
	 * This test takes into account the lesson of the previous test by preparing the calling class for test.
	 * <p>
	 * One issue with this is that code coverage is disabled when a class is prepared for a mock, so if the prepared
	 * class is also your class under test this is problematic.
	 */
	@Test
	@PrepareForTest(OtherClass.class)
	public void differentClassSuccess() throws Exception {
		final NetworkInterface mockInterface = PowerMockito.mock(NetworkInterface.class);

		when(mockInterface.isUp()).thenReturn(true);
		assertTrue(new OtherClass().isUp(mockInterface));
	}
}