package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V__insert_example_todo extends BaseJavaMigration {

    @Override
    public void migrate(final Context context) {
//        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(),true))
//        .execute("insert into tasks (description, done) VALUE ('Test fly',true)");
    }
}
