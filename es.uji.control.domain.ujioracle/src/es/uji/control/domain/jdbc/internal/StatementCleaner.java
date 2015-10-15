package es.uji.control.domain.jdbc.internal;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class StatementCleaner {

	private LinkedList<Statement> statements;
	
	public StatementCleaner() {
		statements = new LinkedList<Statement>();
	}
	
	public void add(Statement statement) {
		statements.push(statement);
	}
	
	public void clear() {
		while(!statements.isEmpty()) {
			try {
				statements.poll().close();
			} catch (SQLException e) {
			}
		}
	}
	
}
