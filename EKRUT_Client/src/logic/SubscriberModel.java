package logic;

import javafx.beans.property.SimpleStringProperty;

//This class represents the subscriber data
public class SubscriberModel {

	private SimpleStringProperty firstName;
	private SimpleStringProperty lastName;
	private SimpleStringProperty id;
	private SimpleStringProperty phone;
	private SimpleStringProperty email;
	private SimpleStringProperty credit;
	private SimpleStringProperty subscriber;

	public SubscriberModel(String firstName, String lastName, String id, String phone, String email, String credit,
			String subscriber) {
		super();
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.id = new SimpleStringProperty(id);
		this.phone = new SimpleStringProperty(phone);
		this.email = new SimpleStringProperty(email);
		this.credit = new SimpleStringProperty(credit);
		this.subscriber = new SimpleStringProperty(subscriber);
	}

	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public String getId() {
		return id.get();
	}

	public void setId(String id) {
		this.id.set(id);
	}

	public String getPhone() {
		return phone.get();
	}

	public void setPhone(String phone) {
		this.phone.set(phone);
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public String getCredit() {
		return credit.get();
	}

	public void setCredit(String credit) {
		this.credit.set(credit);
	}

	public String getSubscriber() {
		return subscriber.get();
	}

	public void setSubscriber(String subscriber) {
		this.subscriber.set(subscriber);
	}
}