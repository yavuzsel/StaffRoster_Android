package org.aerogear.StaffRoster;

import android.os.Parcel;
import android.os.Parcelable;

public class Employee implements Parcelable {
	
	private String cn;
	private String rhatlocation;
	private String mail;
	
	public Employee(){
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getRhatlocation() {
		return rhatlocation;
	}

	public void setRhatlocation(String rhatlocation) {
		this.rhatlocation = rhatlocation;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	@Override
	public String toString() {
		return cn + " " + rhatlocation + " " + mail;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || o.getClass() != getClass())
			return false;
		Employee other = (Employee) o;
		if (toString().length() == 2)
			return other.toString().length() == 2;
		return toString().equals(other.toString());
	}

	@Override
	public int hashCode() {
		return (toString().length() == 2) ? 0 : toString().hashCode();
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(cn);
		dest.writeString(rhatlocation);
		dest.writeString(mail);
	}
	
	public static final Creator<Employee> CREATOR = new Creator<Employee>() {

		public Employee createFromParcel(Parcel source) {
			Employee employee = new Employee();
			employee.cn = source.readString();
			employee.rhatlocation = source.readString();
			employee.mail = source.readString();
			return employee;
		}

		public Employee[] newArray(int size) {
			return new Employee[size];
		}
		
	};

}
