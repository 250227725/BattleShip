import org.junit.jupiter.api.Test;
import org.hamcrest.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PrimaryTest {

    @Test
    public void mainTest() {
        assertThat(true, not(false));
    }
}
