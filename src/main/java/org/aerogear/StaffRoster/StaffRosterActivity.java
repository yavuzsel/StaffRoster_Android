package org.aerogear.StaffRoster;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.jboss.aerogear.android.Pipeline;
import org.jboss.aerogear.android.ReadFilter;
import org.jboss.aerogear.android.impl.pipeline.PipeConfig;
import org.jboss.aerogear.android.pipeline.LoaderPipe;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class StaffRosterActivity extends FragmentActivity {
	private Pipeline pipeline;
	private LoaderPipe<Employee> pipe;
	private EmployeeArrayAdapter listAdapter;
	private EditText inputSearch;
	/**
	 * Called when the activity is first created.
	 * @param savedInstanceState If the activity is being re-initialized after 
	 * previously being shut down then this Bundle contains the data it most 
	 * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.staffsearchlist);

		final ListView listview = (ListView) findViewById(R.id.listview);
		listAdapter = new EmployeeArrayAdapter(this, android.R.layout.simple_list_item_1);
		listview.setAdapter(listAdapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Employee employee = (Employee) listview.getAdapter().getItem(arg2);
				Intent i = new Intent(getApplicationContext(), EmployeeDetailActivity.class);
				i.putExtra("employee", employee);
				startActivity(i);
			}
		});

		inputSearch = (EditText) findViewById(R.id.inputSearch);

		inputSearch.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if(count+start == 0) {
					clearListView();
					return;
				}
				searchEmployee(s.toString());
			}

		});

		try {
			URL baseURL = new URL(Constants.BASE_URL);
			pipeline = new Pipeline(baseURL);
			PipeConfig pipeConfigEmployee = new PipeConfig(baseURL, Employee.class);
			pipeConfigEmployee.setName("employee");
			pipeConfigEmployee.setEndpoint("get_data_simple.php");
			pipeConfigEmployee.setTimeout(1000*10);
			pipeline.pipe(Employee.class, pipeConfigEmployee);

			pipe = pipeline.get("employee", this);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void searchEmployee(String query) {
		pipe.reset();
		ReadFilter readFilter = new ReadFilter();
		HashMap<String, String> employeeData = new HashMap<String, String>();
		employeeData.put("query", query);
		readFilter.setWhere(new JSONObject(employeeData));
		pipe.readWithFilter(readFilter, new EmployeeReadCallback<Employee>());
	}

	private void clearListView(){
		listAdapter.clear();
		listAdapter.notifyDataSetChanged();
	}

	public void addEmployee(List<Employee> employeesToAdd) {
		listAdapter.clear();
		listAdapter.addAll(employeesToAdd);
		listAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(org.aerogear.StaffRoster.R.menu.main, menu);
		return true;
	}

	private class EmployeeArrayAdapter extends ArrayAdapter<Employee> {

		ArrayList<Employee> items = new ArrayList<Employee>();
		Context context;

		public EmployeeArrayAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
			this.context = context;
		}

		@Override
		public void addAll(Collection<? extends Employee> collection) {
			clear();
			items.addAll(collection);
		}

		@Override
		public void clear() {
			items.clear();
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Employee getItem(int position) {
			return items.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(android.R.layout.simple_list_item_1, null);
			}
			Employee item = items.get(position);
			if (item!= null) {
				// My layout has only one TextView
				TextView itemView = (TextView) view.findViewById(android.R.id.text1);
				if (itemView != null) {
					// do whatever you want with your string and long
					itemView.setText(item.getCn());
				}
			}
			return view;
		}
	}
}

