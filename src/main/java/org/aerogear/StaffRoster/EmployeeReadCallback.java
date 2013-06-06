package org.aerogear.StaffRoster;

import java.util.List;

import org.jboss.aerogear.android.pipeline.support.AbstractFragmentActivityCallback;

import android.util.Log;

public class EmployeeReadCallback<T> extends AbstractFragmentActivityCallback<List<Employee>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1303523318896194004L;

	public void onSuccess(List<Employee> data) {
		((StaffRosterActivity) getFragmentActivity()).addEmployee(data);
	}

	public void onFailure(Exception e) {
		Log.d("EmployeeReadCallback", e.getMessage());
	}

}
