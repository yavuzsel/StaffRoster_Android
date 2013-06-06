package org.aerogear.StaffRoster;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EmployeeDetailActivity extends Activity {
	
	private Employee employee;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.employeedetail);
		
		employee = (Employee)getIntent().getExtras().get("employee");
		
		final TextView nameLabel = (TextView)findViewById(R.id.name_label);
		nameLabel.setText(employee.getCn());
		
		final TextView locationLabel = (TextView)findViewById(R.id.location_label);
		locationLabel.setText(employee.getRhatlocation());
		
		final TextView emailLabel = (TextView)findViewById(R.id.email_label);
		emailLabel.setText(employee.getMail());
	}

}
