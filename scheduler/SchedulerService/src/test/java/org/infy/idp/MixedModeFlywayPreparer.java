package org.infy.idp;

import com.opentable.db.postgres.embedded.DatabasePreparer;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MixedModeFlywayPreparer implements DatabasePreparer {
    private final Flyway flyway;
    private final List<String> locations;

    public static MixedModeFlywayPreparer forClasspathLocation(String... locations) {
        Flyway f = new Flyway();
        f.setMixed(true);
        f.setLocations(locations);
        return new MixedModeFlywayPreparer(f, Arrays.asList(locations));
    }

    private MixedModeFlywayPreparer(Flyway flyway, List<String> locations) {
        this.flyway = flyway;
        this.locations = locations;
    }

    @Override
    public void prepare(DataSource ds) throws SQLException {
        flyway.setDataSource(ds);
        flyway.migrate();
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof MixedModeFlywayPreparer)) {
            return false;
        }
        return Objects.equals(locations, ((MixedModeFlywayPreparer) obj).locations);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(locations);
    }
}
